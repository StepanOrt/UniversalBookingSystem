package ubs.model;

public interface Condition {

	public boolean isSatisfiedFor(BookingSystem system, Reservation reservation);

}
