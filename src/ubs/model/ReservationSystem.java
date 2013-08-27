package ubs.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import ubs.model.events.Event;

public class ReservationSystem {
	private Collection<ReservationSystemAttributeValue> atributeValues;
	
	private Collection<ReservationCategoryTree> reservationCategoryTrees;
	private Collection<ReservationTag> reservationTags;
	private Collection<ReservationAttribute> 	reservationAtributes;
	private Collection<ReservationStatus> reservationStatuses;
	private ReservationStatus defaultReservationStatus;
	
	private Collection<User> users;
	
	private Collection<Event> events;
	
	private boolean creditPayment;
	private boolean prepay;
	private boolean cashPayment;
	
	private Collection<ReservationItem> reservationItems;
	private Collection<Reservation> reservations;

	private static ReservationSystem INSTANCE;
	
	public static ReservationSystem getInstance() {
		if (INSTANCE == null) 
			INSTANCE = new ReservationSystem();
		return INSTANCE;
	}

	private ReservationSystem() {
		this.users = new HashSet<User>();
		this.reservationItems = new HashSet<ReservationItem>();
		this.reservations = new HashSet<Reservation>();
	}

	public void createReservation(ReservationItem reservationItem, User user) {
		reservations.add(new Reservation(user, reservationItem, defaultReservationStatus));
		
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void sendEmail(User user, String message) {
		// TODO Auto-generated method stub
	}

	public void addUser(User user) {
		users.add(user);
	}

	public Collection<User> getUsersByName(String string) {
		List<User> selectedUsers = new ArrayList<User>();
		for (User user : users) {
			if (user.getName().contains(string)) selectedUsers.add(user);
		}
		return selectedUsers;
	}

	public void createReservationItem(ReservationItem reservationItem) {
		reservationItems.add(reservationItem);
	}

	public Collection<ReservationItem> getReservationItems() {
		return reservationItems;
	}

	public Collection<Reservation> getReservationsBy(User user) {
		List<Reservation> selected = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getUser().equals(user)) {
				selected.add(reservation);				
			}
		}
		return selected;
	}
}
