package ubs.core;

@SuppressWarnings("serial")
public class InvalidAttributeValueException extends Exception {
	
	public InvalidAttributeValueException(Object value, AttributeType attributeType) {
		super("Value " + value + " is invalid attribute value for type " + attributeType);
	}
	
}
