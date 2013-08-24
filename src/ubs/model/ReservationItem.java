package ubs.model;

import java.util.HashSet;
import java.util.Set;

public class ReservationItem {
	private Set<ReservationAttributeValue> attributeValues;

	
	public ReservationItem() {
		attributeValues = new HashSet<ReservationAttributeValue>();
	}

	public Set<ReservationAttributeValue> getAttributeValues() {
		return attributeValues;
	}

	public void addAttributeValue(ReservationAttribute attribute, Object value) {
		ReservationAttributeValue attributeValue = new ReservationAttributeValue(attribute, value);
		attributeValues.add(attributeValue);
	}
}
