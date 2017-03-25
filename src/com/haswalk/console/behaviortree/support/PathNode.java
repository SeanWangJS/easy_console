package com.haswalk.console.behaviortree.support;

import java.util.regex.Pattern;

import com.haswalk.console.behaviortree.Node;

public class PathNode extends DefaultNode{

private Pattern path = Pattern.compile("([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?");
	
	public Node next(String word) {
		if(path.matcher(word).find()) {
			return super.next("p");
		}
		return super.next(word);
	}
	
}
