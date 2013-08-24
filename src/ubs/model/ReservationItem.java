package ubs.model;

import java.util.HashSet;
import java.util.Set;

public class ReservationItem {
	private Set<ReservationAttributeValue> attributeValues;
	private ReservationCategoryNode category;
	
	public ReservationItem(ReservationCategoryNode category) {
		attributeValues = new HashSet<ReservationAttributeValue>();
		this.category = category;
	}

	public Set<ReservationAttributeValue> getAttributeValues() {
		return attributeValues;
	}

	public void addAttributeValue(ReservationAttribute attribute, Object value) {
		ReservationAttributeValue attributeValue = new ReservationAttributeValue(attribute, value);
		attributeValues.add(attributeValue);
	}

	public ReservationCategoryNode getCategory() {
		return category;
	}

	public void setCategory(ReservationCategoryNode category) {
		this.category = category;
	}
}
