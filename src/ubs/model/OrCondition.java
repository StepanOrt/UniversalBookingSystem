package ubs.model;

import java.util.ArrayList;
import java.util.List;

public class OrCondition implements Condition {

	List<Condition> subConditions;
	
	public OrCondition(List<Condition> subConditions) {
		super();
		this.subConditions = new ArrayList<Condition>();
	}

	@Override
	public boolean isSatisfiedFor(BookingSystem system, Reservation reservation) {
		for (Condition condition : subConditions) {
			if (condition.isSatisfiedFor(system, reservation)) return true;
		}
		return false;
	}

}
