package ubs.model;

import java.util.Set;

public class BookingSystem {
	private Set<ReservationSystemAttributeValue> atributeValues;
	
	private Set<ReservationCategoryTree> reservationCategoryTrees;
	private Set<ReservationTag> reservationTags;
	private Set<ReservationAttribute> 	reservationAtributes;
	private Set<ReservationStatus> reservationStatuses;
	
	private Set<User> users;
	
	private boolean creditPayment;
	private boolean prepay;
	private boolean cashPayment;
	
	private Set<ReservationItem> reservationItem;
	private Set<Reservation> reservations;
	
	public void createReservation(ReservationItem reservationItem, User user) {
		//TODO
	}

	public Iterable<Reservation> getReservations() {
		return reservations;
	}

	public void sendEmail(User user, String message) {
		// TODO Auto-generated method stub
	}
}
