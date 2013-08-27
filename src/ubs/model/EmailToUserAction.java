package ubs.model;

public class EmailToUserAction implements Action {

	private String message;
	
	public EmailToUserAction(String message) {
		this.message = message;
	}

	@Override
	public void execute(ReservationSystem system, Reservation reservation) {
		User user = reservation.getUser();
		if (user.isEmailNotifications()) {
			system.sendEmail(user, message);			
		}
	}

}
