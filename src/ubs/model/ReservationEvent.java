package ubs.model;


public class ReservationEvent extends Event {
	
	public void triger(BookingSystem system, Reservation reservation) {
		if (condition.isSatisfiedFor(system, reservation)) {
			for (Action action : super.actions) {
				action.execute(system, reservation);
			}				
		}
	}
}
