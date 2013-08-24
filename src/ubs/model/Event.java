package ubs.model;

import java.util.List;

public abstract class Event {
	protected Condition condition;
	protected List<Action> actions;
	
	protected void executeAllActions(BookingSystem system, Reservation reservation) {
		for (Action action : actions) {
			action.execute(system, reservation);
		}
	}
}
