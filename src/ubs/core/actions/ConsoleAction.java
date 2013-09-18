package ubs.core.actions;


public class ConsoleAction extends Action {

	private String message;
	
	public ConsoleAction(String message) {
		this.message = message;
	}
	
	@Override
	public void execute() {
		System.out.println(message);
	}

}
