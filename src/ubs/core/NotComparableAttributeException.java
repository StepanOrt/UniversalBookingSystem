package ubs.core;

@SuppressWarnings("serial")
public class NotComparableAttributeException extends Exception {
	
	public NotComparableAttributeException(ReservationItemAttribute attribute) {
		super("Attribute " + attribute + "is not comparable!");
	}
	
}
