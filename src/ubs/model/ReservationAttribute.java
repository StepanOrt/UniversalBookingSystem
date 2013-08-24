package ubs.model;

public class ReservationAttribute {
	private String name;
	private AttributeType type;

	public ReservationAttribute(String name, AttributeType type) {
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
