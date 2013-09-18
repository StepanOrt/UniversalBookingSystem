package ubs.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReservationItem {
	private Map<String, ReservationAttributeValue> attributeValues;
	private ReservationCategoryNode category;
	private Set<ReservationTag> tags;
	
	public ReservationItem(ReservationCategoryNode category) {
		attributeValues = new HashMap<String, ReservationAttributeValue>();
		tags = new HashSet<ReservationTag>();
		this.category = category;
	}

	public Collection<ReservationAttributeValue> getAttributeValues() {
		return attributeValues.values();
	}

	public void setAttributeValue(ReservationItemAttribute attribute, Object value) {
		ReservationAttributeValue attributeValue = new ReservationAttributeValue(attribute, value);
		attributeValues.put(attribute.getName(), attributeValue);
	}
	
	public void addTag(ReservationTag tag) {
		tags.add(tag);
	}
	
	public void removeTag(ReservationTag tag) {
		tags.remove(tag);
	}

	public Collection<ReservationTag> getTags() {
		return tags;
	}

	public ReservationCategoryNode getCategory() {
		return category;
	}

	public void setCategory(ReservationCategoryNode category) {
		this.category = category;
	}

	public ReservationAttributeValue getAttribute(ReservationItemAttribute attribute) {
		return attributeValues.get(attribute.getName());
	}
}
