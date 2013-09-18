package ubs.core.actions;

import ubs.core.User;

public class EmailToUserAction extends Action {

	private String message;
	private User user;
	
	public EmailToUserAction(String message, User user) {
		this.message = message;
		this.user = user;
	}

	@Override
	public void execute() {
		if (user.isEmailNotifications()) {
			//TODO: sending emails			
		}
	}

}
