package ubs.core;

import java.util.Date;

public abstract class AttributeValue {
	private Object value;
	protected AttributeType attributeType;
	
	public AttributeValue(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) throws InvalidAttributeValueException {
		if (attributeType == AttributeType.DATETIME && value instanceof Date) {
			this.value = value;
		} else if (attributeType == AttributeType.DECIMAL && value instanceof Float) {
			this.value = value;			
		} else if (attributeType == AttributeType.NUMERIC && value instanceof Integer) {
			this.value = value;
		} else if ((attributeType == AttributeType.TEXT || attributeType == AttributeType.URL || attributeType == AttributeType.PICTURE) && value instanceof String) {
			this.value = value;			
		} else {
			throw new InvalidAttributeValueException(value, attributeType);
		}
	}
}
