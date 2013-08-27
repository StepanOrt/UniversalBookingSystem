package ubs.model;

public class ConsoleAction implements Action {

	private String message;
	
	public ConsoleAction(String message) {
		this.message = message;
	}
	
	@Override
	public void execute(ReservationSystem system, Reservation reservation) {
		System.out.println(message);
	}

}
