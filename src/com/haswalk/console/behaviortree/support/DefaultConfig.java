package com.haswalk.console.behaviortree.support;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.haswalk.console.annotation.Action;
import com.haswalk.console.annotation.Injection;
import com.haswalk.console.behaviortree.Config;
import com.haswalk.console.behaviortree.Node;
import com.haswalk.console.util.JsonUtil;

public class DefaultConfig implements Config{
	private String[] paths;
	private HashMap<String, Node> nodes = new HashMap<>();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public Config setConfigPaths(String... paths) {
		this.paths = paths;
		return this;
	}
	
	private JsonObject readAndMergeJson() {
		List<JsonObject> jsonObjects = new ArrayList<>();
		for(String path: paths) {
			try {
				jsonObjects.add(gson.fromJson(new String(Files.readAllBytes(Paths.get(path))), JsonObject.class));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return JsonUtil.merge(jsonObjects);
		
	}
	
	private JsonObject nickNameReplace(JsonObject json) {
		JsonElement nickNameJson = json.get("nickname");
		JsonElement configJson = json.get("config");
		if(nickNameJson == null) {
			return configJson.getAsJsonObject();
		}
		HashMap<String, String> nickName = new HashMap<>();
		nickNameJson.getAsJsonObject().entrySet().forEach(e -> {
			nickName.put(e.getKey(), e.getValue().getAsString());
		});
		String configStr = configJson.toString();
		
		for(Entry<String, String> e: nickName.entrySet()) {
			configStr = configStr.replaceAll(e.getKey(), e.getValue());
		}
			
		return gson.fromJson(configStr, JsonObject.class);
	}
	
	@Override
	public Config parse() {
		JsonObject json = readAndMergeJson();
		JsonObject configJson = nickNameReplace(json);
		configJson.entrySet().forEach(e -> {
			String name = e.getKey();
			JsonObject properties = e.getValue().getAsJsonObject();
			String type = properties.get("class").getAsString();
			try {
				nodes.put(name, (Node) Class.forName(type).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		
		configJson.entrySet().forEach(e -> {
			String name = e.getKey();
			JsonObject properties = e.getValue().getAsJsonObject();
			JsonElement transitions = properties.get("transitions");
			if(transitions != null) {
				transitions.getAsJsonObject().entrySet().forEach(t -> {
					String trigger = t.getKey();
					String targetName = t.getValue().getAsString();
					System.out.println(name + ": " + trigger + "-> " + targetName);
					nodes.get(name).addTransition(trigger, nodes.get(targetName));
				});
			}
		});
		
		return this;
	}
	
	@Override 
	public Config DI(List<Object> components) {
		nodes.forEach((name, node) -> {
			Class<?> clazz ;
			try {
				clazz = Class.forName(node.getClass().getName());
				if(clazz.isAnnotationPresent(Action.class)){
					Method[] methods = clazz.getMethods();
					for(Method method: methods) {
						if(method.isAnnotationPresent(Injection.class)) {
							Class<?> paramClazz = method.getParameterTypes()[0];
							for(Object comp:components) {
								if(paramClazz.isAssignableFrom(comp.getClass())) {
									try {
										method.invoke(node, comp);
									} catch (IllegalAccessException | IllegalArgumentException
											| InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		return this;
	}
	
	@Override
	public Node getStart() {
		return nodes.get("Start");
	}

}
