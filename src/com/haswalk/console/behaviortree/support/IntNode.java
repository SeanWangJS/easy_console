package com.haswalk.console.behaviortree.support;

import java.util.regex.Pattern;

import com.haswalk.console.behaviortree.Node;

public class IntNode extends DefaultNode{

	private Pattern number = Pattern.compile("^[0-9]+$");
	
	public Node next(String word){
		if(number.matcher(word).find()) {
			return super.next("n");
		}
		return super.next(word);
	}
}
