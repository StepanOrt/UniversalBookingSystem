package ubs.model;

public class SystemEvent extends Event {
	
	public void trigerForAllReservations(BookingSystem system) {
		for (Reservation reservation : system.getReservations()) {
			if (condition.isSatisfiedFor(system, reservation)) {
				executeAllActions(system, reservation);
			}
		}
	}
	
	public void triger(BookingSystem system) {
		if (condition.isSatisfiedFor(system, null)) {
			executeAllActions(system, null);
		}
	}
}
