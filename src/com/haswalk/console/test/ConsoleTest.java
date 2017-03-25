package com.haswalk.console.test;

import java.util.Arrays;

import org.junit.Test;

import com.haswalk.console.Console;

public class ConsoleTest {

	@Test
	public void test(){
		Console console = new Console();
		console.config("C:/Users/wangx/OneDrive/workspace/neon/consolex/src/com/haswalk/console/test/config.json");
		console.setComponent(Arrays.asList(new Object[]{new Comp1(), new Comp2()}));
		console.run("cmd3");
		console.run("cmd3 10");
		console.run("cmd3 10 10");
		
		console.run("cmd2");
		console.run("cmd2 10.2");
		console.run("cmd2 10.2 10.3");
		
		console.run("cmd4");
		console.run("cmd4 e:\\fvm");
	}
	
}
