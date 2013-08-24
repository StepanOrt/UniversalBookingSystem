package ubs.model;

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

	public void setValue(Object value) throws InvalidAttributeException {
		if (attributeType == AttributeType.FLOAT && value instanceof Float) {
			this.value = value;			
		} else if (attributeType == AttributeType.INTEGER && value instanceof Integer) {
			this.value = value;
		} else if ((attributeType == AttributeType.TEXT || attributeType == AttributeType.URL || attributeType == AttributeType.PICTURE) && value instanceof String) {
			this.value = value;			
		} else {
			throw new InvalidAttributeException(value, attributeType);
		}
	}
}
