package com.haswalk.console.test;

import com.haswalk.console.annotation.Action;
import com.haswalk.console.annotation.Injection;
import com.haswalk.console.behaviortree.support.IntNode;

@Action
public class ActionNode1 extends IntNode{
	private Comp1 comp;
	
	@Override
	public void run() {
		super.run();
		comp.action();
	}
	
	@Injection
	public void setComp(Comp1 comp){
		this.comp = comp;
	}
}
