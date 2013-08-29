package ubs.model;

import java.util.Collection;

import ubs.model.actions.Action;
import ubs.model.events.Event;
import ubs.model.events.Event.EventType;

public class Rule {
	
	private Class<Event> event;
	private EventType eventType;
	private Condition condition;
	private Collection<Action> actions;
	
	public Rule(Class<Event> event, EventType eventType, Condition condition,
			Collection<Action> actions) {
		this.event = event;
		this.eventType = eventType;
		this.condition = condition;
		this.actions = actions;
	}

	public Class<Event> getEvent() {
		return event;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Condition getCondition() {
		return condition;
	}

	public Collection<Action> getActions() {
		return actions;
	}
}
