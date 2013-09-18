package ubs.core;

import ubs.core.actions.Action;

public class ConsoleAction implements Action {

	private String message;
	
	public ConsoleAction(String message) {
		this.message = message;
	}
	
	@Override
	public void execute() {
		System.out.println(message);
	}

}
