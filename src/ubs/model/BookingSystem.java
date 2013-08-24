package ubs.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	private Set<ReservationItem> reservationItems;
	private Set<Reservation> reservations;
	
	public BookingSystem() {
		this.atributeValues = atributeValues;
		this.reservationCategoryTrees = reservationCategoryTrees;
		this.reservationTags = reservationTags;
		this.reservationAtributes = reservationAtributes;
		this.reservationStatuses = reservationStatuses;
		this.users = new HashSet<User>();
		this.reservationItems = new HashSet<ReservationItem>();
		this.reservations = reservations;
	}

	public void createReservation(ReservationItem reservationItem, User user) {
		//TODO
	}

	public Iterable<Reservation> getReservations() {
		return reservations;
	}

	public void sendEmail(User user, String message) {
		// TODO Auto-generated method stub
	}

	public void addUser(User user) {
		users.add(user);
	}

	public Iterable<User> getUsersByName(String string) {
		List<User> selectedUsers = new ArrayList<User>();
		for (User user : users) {
			if (user.getName().contains(string)) selectedUsers.add(user);
		}
		return selectedUsers;
	}

	public void createReservationItem(ReservationItem reservationItem) {
		reservationItems.add(reservationItem);
	}

	public Iterable<ReservationItem> getReservationItems() {
		return reservationItems;
	}
}
