package ubs.model;

@SuppressWarnings("serial")
public class NotComparableAttributeException extends Exception {
	
	public NotComparableAttributeException(ReservationAttribute attribute) {
		super("Attribute " + attribute + "is not comparable!");
	}
	
}
