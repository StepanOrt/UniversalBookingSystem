package ubs.core;

import java.util.Collection;

import ubs.core.actions.Action;

public interface Condition {

	public boolean isSatisfiedFor(ReservationSystem system, Reservation reservation, Collection<Action> defaultActions);

}
