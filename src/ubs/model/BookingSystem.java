package ubs.model;

import java.util.List;

public class BookingSystem {
	private List<AtributeValue> atributeValues;
	private List<ReservationCategoryTree> reservationCategoryTrees;
	private List<ReservationTag> reservationTags;
	private List<ReservationAtributes> 	reservationAtributes;
	private List<ReservationStatus> reservationStatuses;
	
	private List<User> users;
	
	private boolean creditPayment;
	private boolean prepay;
	private boolean cashPayment;
	
	private List<ReservationItem> reservationItem;
	private List<Reservation> reservations;
	
	public void createReservation(ReservationItem reservationItem, User user) {
		//TODO
	}
	
	
}
