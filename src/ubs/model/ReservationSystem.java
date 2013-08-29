package ubs.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ReservationSystem {
	
	@SuppressWarnings("serial")
	public static final Map<AttributeType, Class<?>> ATTRIBUTE_TYPE_CLASSES = new HashMap<AttributeType, Class<?>>() 
	{
		{ 
			put(AttributeType.DATETIME, Date.class);
			put(AttributeType.DECIMAL, Float.class);
			put(AttributeType.NUMERIC, Long.class);
			put(AttributeType.TEXT, String.class);
			put(AttributeType.PICTURE, String.class);
			put(AttributeType.URL, String.class);
		}
	};
	
	
	private Collection<ReservationSystemAttributeValue> atributeValues;
	
	private ReservationCategoryTree reservationCategoryTrees;
	private Collection<ReservationTag> reservationTags;
	private Collection<ReservationItemAttribute> 	reservationAtributes;
	private Collection<ReservationStatus> reservationStatuses;
	private ReservationStatus defaultReservationStatus;
	
	private Collection<User> users;
	
	private Collection<Rule> rules;
	
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

	public Collection<Reservation> getReservations(User user) {
		List<Reservation> selected = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getUser().equals(user)) {
				selected.add(reservation);				
			}
		}
		if (selected.size() == 0) return null;
		return selected;
	}
	
	public Collection<Reservation> getReservations(ReservationItem item) {
		List<Reservation> selected = new ArrayList<Reservation>();
		for (Reservation reservation : reservations) {
			if (reservation.getItem().equals(item)) {
				selected.add(reservation);
			}
		}
		if (selected.size() == 0) return null;
		return selected;
	}
	
	public Reservation getReservation(ReservationItem item, User user) {
		Reservation selected = null;
		for (Reservation reservation : reservations) {
			if (reservation.getItem().equals(item) && reservation.getUser().equals(user)) {
				selected = reservation;
			}
		}
		return selected;
	}
	
	public Collection<ReservationItem> getReservationItems(ReservationItemAttribute attribute, Object value) {
		List<ReservationItem> selected = new ArrayList<ReservationItem>();
		for (ReservationItem ri : reservationItems) {
			if (ri.getAttribute(attribute).isSameValue(value)) {
				selected.add(ri);
			}
		}
		if (selected.size() == 0) return null;
		return selected;
	}

	public Collection<ReservationItem> getReservationItems(ReservationTag[] tags) {
		List<ReservationItem> selected = new ArrayList<ReservationItem>();
		for (ReservationItem ri : reservationItems) {
			boolean containsAll = true;
			for (ReservationTag tag : tags) {			
				if (!ri.getTags().contains(tag)) {
					containsAll = false;
					break;
				}
			}
			if (containsAll) selected.add(ri);
		}
		if (selected.size() == 0) return null;
		return selected;
	}
	
	public Collection<ReservationItem> getReservationItems(ReservationCategoryNode node) {
		List<ReservationItem> selected = new ArrayList<ReservationItem>();
		for (ReservationItem ri : reservationItems) {
			if (ri.getCategory().equals(node)) 
				selected.add(ri);
		}
		if (selected.size() == 0) return null;
		return selected;
	}
}
