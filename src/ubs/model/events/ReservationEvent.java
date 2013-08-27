package ubs.model.events;

import ubs.model.Reservation;

public class ReservationEvent extends Event {
	private Reservation modified;
	private Reservation reservation;
	
	public ReservationEvent(EventType type, Reservation reservation)  throws InvalidEventTypeException{
		super(type);
		if (type == EventType.MODIFY) throw new IllegalArgumentException("Modified reservation must be provided for event type MODIFY!");
		this.reservation = reservation;
	}
	
	public ReservationEvent(EventType type, Reservation reservation, Reservation modified) throws InvalidEventTypeException{
		super(type);
		this.reservation = reservation;
		this.modified = modified;
	}

	public Reservation getModified() {
		return modified;
	}

	public Reservation getReservation() {
		return reservation;
	}

	@Override
	protected EventType[] validEventTypes() {
		return new EventType[] { 
				
				EventType.CREATE,
				EventType.REMOVE,
				EventType.MODIFY,
				EventType.STATUS_CHANGE
				
			};
	}
}
