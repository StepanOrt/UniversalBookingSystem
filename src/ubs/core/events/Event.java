package ubs.core.events;

import java.util.Calendar;
import java.util.Date;

public abstract class Event {
	protected boolean consumed;
	protected Date when;
	protected EventType type;
	
	public Event(EventType type) throws InvalidEventTypeException {
		when = Calendar.getInstance().getTime();
		consumed = false;
		if (isValidEventType(type))
			this.type = type;			
		else
			throw new InvalidEventTypeException(this.getClass().getSimpleName(), type);
	}
	
	public void consume() {
		consumed = true;
	}
	
	public boolean isConsumed() {
		return consumed;
	}
	
	public Date getWhen() {
		return when;
	}
	
	public EventType getEventType() {
		return type;
	}
	
	public boolean isValidEventType(EventType type) throws InvalidEventTypeException {
		for (EventType t : validEventTypes()) {
			if (t ==  type) return true;
		}
		return false;
	}
	
	protected abstract EventType[] validEventTypes();
	
	public enum EventType {
		CREATE,
		REMOVE,
		MODIFY,
		STATUS_CHANGE,
		TIME_EVENT,
		TOPUP,
		CHARGE
	};
}
