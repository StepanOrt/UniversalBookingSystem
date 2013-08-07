package ubs.model;

public class InvalidAttributeException extends Exception {

	public InvalidAttributeException(Object value, AttributeType attributType) {
		super("Value " + value + " is invalid attribute value for type " + attributType);
	}
	
}
