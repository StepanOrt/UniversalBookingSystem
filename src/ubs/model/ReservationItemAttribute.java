package ubs.model;

public class ReservationItemAttribute {
	private String name;
	private AttributeType type;

	public ReservationItemAttribute(String name, AttributeType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public AttributeType getType() {
		return type;
	}
}
