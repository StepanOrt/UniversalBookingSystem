package ubs.core.events;

import ubs.core.User;

public class UserEvent extends Event {
	private User modified;
	private User user;
	
	public UserEvent(EventType type, User user) throws InvalidEventTypeException {
		super(type);
		this.user = user;
	}
	
	public UserEvent(EventType type, User user, User modified) throws InvalidEventTypeException {
		super(type);
		this.user = user;
		this.modified = modified;
	}

	public User getModified() {
		return modified;
	}

	public User getUser() {
		return user;
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
