package ubs.core;


public class ReservationAttributeValue extends AttributeValue {
	
	private ReservationItemAttribute attribute;
	public ReservationAttributeValue(ReservationItemAttribute attribute, Object value) {
		super(value);
		this.setAttribute(attribute);
		super.attributeType = attribute.getType();
	}
	public ReservationItemAttribute getAttribute() {
		return attribute;
	}
	public void setAttribute(ReservationItemAttribute attribute) {
		this.attribute = attribute;
	}
	public boolean isSameValue(Object value) {
		Class<?> clazz = ReservationSystem.ATTRIBUTE_TYPE_CLASSES.get(attributeType);
		try {
			if (clazz.cast(value).equals(clazz.cast(getValue()))) {
				return true;
			}			
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
