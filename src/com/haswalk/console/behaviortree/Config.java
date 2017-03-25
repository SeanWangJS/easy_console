package com.haswalk.console.behaviortree;

import java.util.List;

public interface Config {

	Config setConfigPaths(String... paths);

	Config parse();

	Config DI(List<Object> components);
	
	Node getStart();

}
