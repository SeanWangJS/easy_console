package com.haswalk.console.behaviortree.support;

import com.haswalk.console.behaviortree.BehaviorTree;
import com.haswalk.console.behaviortree.Node;

public class DefaultBehaviorTree implements BehaviorTree{

	private Node start;
	private Node current;
	private Node empty;
	
	public DefaultBehaviorTree(Node start) {
		this.start = start;
		current = start;
		empty = new DefaultNode(){
			@Override 
			public void run() {
				System.err.println("Error: can not handle the following command: "+ command);
			}
		};
	}

	@Override
	public void excute(String command) {
		String[] words = command.trim().split("\\s+");
		for(String word : words) {
			current = current.next(word);
			if(current == null) {
				current = empty;
				break;
			}
		}
		current.setCommand(command);
		current.run();
		reset();
	}

	@Override
	public void reset() {
		current = start;
	}
	
}
