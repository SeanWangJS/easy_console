package com.haswalk.console.behaviortree.support;

import java.util.regex.Pattern;

import com.haswalk.console.behaviortree.Node;

public class DblNode extends DefaultNode{

	private Pattern digit = Pattern.compile("^[0-9]*\\.?[0-9]+$");
	
	public Node next(String word) {
		if(digit.matcher(word).find()) {
			return super.next("d");
		}
		return super.next(word);
	}
}
