package ubs.model.actions;

import ubs.model.User;

public class EmailToUserAction implements Action {

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
