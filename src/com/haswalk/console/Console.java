package com.haswalk.console;

import java.util.List;
import java.util.Scanner;

import com.haswalk.console.behaviortree.BehaviorTree;
import com.haswalk.console.behaviortree.Config;
import com.haswalk.console.behaviortree.support.DefaultBehaviorTree;
import com.haswalk.console.behaviortree.support.DefaultConfig;

public class Console {
	private BehaviorTree btree;
	private Config config;
	
	public Console() {
		config = new DefaultConfig();
	}
	
	public void config(String... paths) {
		config.setConfigPaths(paths);
		config.parse();
		btree = new DefaultBehaviorTree(config.getStart());
	}
	public void setComponent(List<Object> components) {
		config.DI(components);
	}
	public void run(String command) {
		btree.excute(command);
	}

	public void waitFor() {
		System.out.print("console:");
		String command = "";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
			command = scanner.nextLine();
			run(command);
			System.out.print("console:");
		}
	}
}
