package com.haswalk.console.behaviortree.support;

import java.util.HashMap;

import com.haswalk.console.behaviortree.Node;

public class DefaultNode implements Node{
	protected String command;
	protected HashMap<String, Node> transitions = new HashMap<>();
	
	@Override
	public Node next(String word) {
		return transitions.get(word);
	}
	
	@Override
	public void addTransition(String trigger, Node target) {
		transitions.put(trigger, target);
	}
	
	@Override
	public void run() {
		System.out.println(this.getClass().getName() + ": running");
	}
	
	@Override
	public void setCommand(String command) {
		this.command = command;
	}
}
