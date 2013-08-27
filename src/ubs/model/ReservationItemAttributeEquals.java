package ubs.model;

public class ReservationItemAttributeEquals implements Condition {

	private ReservationAttribute attribute;
	private Object value;
	
	public ReservationItemAttributeEquals(ReservationAttribute attribute, Object value) throws NotComparableAttributeException {
		this.attribute = attribute;
		if (attribute.getType() == AttributeType.PICTURE ||
		    attribute.getType() == AttributeType.TEXT ||
		    attribute.getType() == AttributeType.URL) throw new NotComparableAttributeException(attribute);
		this.value = value;
	}

	@Override
	public boolean isSatisfiedFor(ReservationSystem system, Reservation reservation) {
		ReservationAttributeValue attributeValue = reservation.getItem().getAttribute(attribute);
		if (attributeValue.equals(value)) return true;
		return false;
	}

}
