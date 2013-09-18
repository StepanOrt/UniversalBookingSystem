package ubs.core.events;

import ubs.core.ReservationItem;

public class ReservationItemEvent extends Event {
	private ReservationItem modified;
	private ReservationItem reservationItem;
	
	public ReservationItemEvent(EventType type, ReservationItem reservationItem) throws InvalidEventTypeException {
		super(type);
		this.reservationItem = reservationItem;
	}
	
	public ReservationItemEvent(EventType type, ReservationItem reservationItem, ReservationItem modified) throws InvalidEventTypeException {
		super(type);
		this.reservationItem = reservationItem;
		this.modified = modified;
	}

	public ReservationItem getModified() {
		return modified;
	}

	public ReservationItem getReservationItem() {
		return reservationItem;
	}
	
	@Override
	protected EventType[] validEventTypes() {
		return new EventType[] { 
				
				EventType.CREATE,
				EventType.REMOVE,
				EventType.MODIFY
				
			};
	}
}
