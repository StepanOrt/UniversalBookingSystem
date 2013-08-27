package ubs.model.events;

import ubs.model.events.Event.EventType;

@SuppressWarnings("serial")
public class InvalidEventTypeException extends Exception {

	private String eventClassName;
	private EventType eventType;

	public InvalidEventTypeException(String eventClassName, EventType type) {
		this.eventClassName = eventClassName;
		this.eventType = type;
	}
	
	public String getMessage() {
		return "Type: " + eventType.toString() + "is invalid for event" + eventClassName + "!";
	}
	
}
