package ubs.model;

import java.util.HashSet;
import java.util.Set;

public class ReservationItem {
	private Set<ReservationAttributeValue> attributeValues;
	private ReservationCategoryNode category;
	private Set<ReservationTag> tags;
	
	public ReservationItem(ReservationCategoryNode category) {
		attributeValues = new HashSet<ReservationAttributeValue>();
		tags = new HashSet<ReservationTag>();
		this.category = category;
	}

	public Iterable<ReservationAttributeValue> getAttributeValues() {
		return attributeValues;
	}

	public void addAttributeValue(ReservationAttribute attribute, Object value) {
		ReservationAttributeValue attributeValue = new ReservationAttributeValue(attribute, value);
		attributeValues.add(attributeValue);
	}
	
	public void addTag(ReservationTag tag) {
		tags.add(tag);
	}
	
	public void removeTag(ReservationTag tag) {
		tags.remove(tag);
	}

	public Iterable<ReservationTag> getTags() {
		return tags;
	}

	public ReservationCategoryNode getCategory() {
		return category;
	}

	public void setCategory(ReservationCategoryNode category) {
		this.category = category;
	}
}
