package com.haswalk.console.behaviortree;

public interface Node {

	public Node next(String word);
	
	public void addTransition(String trigger, Node target);
	
	public void run();
	
	public void setCommand(String command);
}
