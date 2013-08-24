package ubs.model;

import java.util.ArrayList;
import java.util.List;

public class AndCondition implements Condition {

	List<Condition> subConditions;
	
	public AndCondition(List<Condition> subConditions) {
		super();
		this.subConditions = new ArrayList<Condition>();
	}

	@Override
	public boolean isSatisfiedFor(BookingSystem system, Reservation reservation) {
		for (Condition condition : subConditions) {
			if (!condition.isSatisfiedFor(system, reservation)) return false;
		}
		return true;
	}

}
