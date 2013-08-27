package ubs.model.events;


public class SystemEvent extends Event {

	public SystemEvent(EventType type) throws InvalidEventTypeException {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected EventType[] validEventTypes() {
		return new EventType[] { 
				
				EventType.TIME_EVENT
				
			};
	}
}
