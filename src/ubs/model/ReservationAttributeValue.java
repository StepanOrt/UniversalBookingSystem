package ubs.model;

public class ReservationAttributeValue extends AttributeValue {
	private ReservationAttribute attribute;
	public ReservationAttributeValue(ReservationAttribute attribute, Object value) {
		super(value);
		this.setAttribute(attribute);
		super.attributeType = attribute.getType();
	}
	public ReservationAttribute getAttribute() {
		return attribute;
	}
	public void setAttribute(ReservationAttribute attribute) {
		this.attribute = attribute;
	}

}
