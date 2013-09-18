package ubs.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ubs.core.actions.Action;
import ubs.core.actions.ActionPlace;
import ubs.core.events.Event;
import ubs.core.events.ReservationEvent;
import ubs.core.events.Event.EventType;

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
	
	private Collection<ReservationCategoryTree> reservationCategoryTrees;
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


	public void sendEmail(User user, String message) {
		// TODO Auto-generated method stub
	}
	
	public void addRule(Class<? extends Event> event, EventType type, Condition condition, Collection<Action> actions, boolean cancels) {
		rules.add(new Rule(event, type, condition, actions, cancels));
	}
	
	public Collection<Rule> getRules(Class<? extends Event> event, EventType type) {
		List<Rule> selected = new ArrayList<Rule>();
		for (Rule rule : rules) {
			if (rule.getEvent().equals(event) && rule.getEventType().equals(type)) {
				selected.add(rule);
			}
		}
		if (selected.size() == 0) selected = null;
		return selected;
	}
	
	public void handleEvent(ReservationEvent event) throws Exception {
		Collection<Rule> r = getRules(event.getClass(), event.getEventType());
		Reservation reservation = event.getReservation();
		Collection<Action> defaultActions = getDefaultActions(event.getClass(), event.getEventType());
		Collection<Action> actionsBefore = new ArrayList<Action>();
		Collection<Action> actionsAfter = new ArrayList<Action>();
		for (Rule rule : r) {
			if (rule.getCondition().isSatisfiedFor(this, reservation, defaultActions)) {
				for (Action a : rule.getActions()) {
					if (a.getActionPlace() == ActionPlace.BEFORE_DEFAULT) {
						actionsBefore.add(a);
					}
					else if (a.getActionPlace() == ActionPlace.AFTER_DEFAULT) {
						actionsAfter.add(a);
					}
				}
			}
			for (Action action : actionsBefore) {
				action.execute();
			}
			if (!rule.cancelDefaultAction()) {
				for (Action action : defaultActions) {
					action.execute();					
				}
			}
			for (Action action : actionsAfter) {
				action.execute();
			}
		}
	}

	private Collection<Action> getDefaultActions(
			Class<? extends ReservationEvent> class1, EventType eventType) {
		// TODO Auto-generated method stub
		return null;
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
	
	public void createReservation(ReservationItem reservationItem, User user) {
		reservations.add(new Reservation(user, reservationItem, defaultReservationStatus));
		
	}

	public Collection<Reservation> getAllReservations() {
		return reservations;
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

	public void createReservationItem(ReservationItem reservationItem) {
		reservationItems.add(reservationItem);
	}
	
	public Collection<ReservationItem> getReservationAllItems() {
		return reservationItems;
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
			if (ri.getCategories().equals(node)) 
				selected.add(ri);
		}
		if (selected.size() == 0) return null;
		return selected;
	}
}
