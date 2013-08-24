package ubs.model;

public class ReservationSystemAttributeValue extends AttributeValue {
	private String name;

	public ReservationSystemAttributeValue(String name,	AttributeType attributeType, Object value) {
		super(value);
		super.attributeType = attributeType;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void setValue(Object value) throws InvalidAttributeValueException {
		super.setValue(value);
	}
}
