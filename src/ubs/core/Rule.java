package ubs.core;

import java.util.Collection;

import ubs.core.actions.Action;
import ubs.core.events.Event;
import ubs.core.events.Event.EventType;

public class Rule {
	
	private Class<? extends Event> event;
	private EventType eventType;
	private Condition condition;
	private Collection<Action> actions;
	private boolean cancels;
	
	public Rule(Class<? extends Event> event, EventType eventType, Condition condition,
			Collection<Action> actions, boolean cancels) {
		this.event = event;
		this.eventType = eventType;
		this.condition = condition;
		this.actions = actions;
		this.cancels = cancels;
	}

	public Class<? extends Event> getEvent() {
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

	public boolean cancelDefaultAction() {
		return cancels;
	}
}
