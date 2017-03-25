package com.haswalk.console.test;

import com.haswalk.console.annotation.Action;
import com.haswalk.console.annotation.Injection;
import com.haswalk.console.behaviortree.support.DefaultNode;

@Action
public class ActionNode2 extends DefaultNode{
	private Comp2 comp;
	
	@Override
	public void run() {
		super.run();
		comp.action();
	}
	
	@Injection
	public void setComp(Comp2 comp) {
		this.comp = comp;
	} 
}
