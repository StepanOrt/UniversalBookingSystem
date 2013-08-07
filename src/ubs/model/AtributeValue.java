package ubs.model;

public class AtributeValue {
	private String name;
	private AttributeType attributType;
	private Object value;
	
	public AtributeValue(String name, AttributeType attributType, Object value) {
		super();
		this.name = name;
		this.attributType = attributType;
		this.value = value;
	}

	public Object getValue() {
		return value;
		/*
		if (attributType == AttributeType.TEXT || attributType == AttributeType.URL || attributType == AttributeType.PICTURE)
			return (String)value;
		else if (attributType == AttributeType.INTEGER) 
			return (Integer)value;
		else if (attributType == AttributeType.FLOAT)
			return (Float)value;
		*/
	}

	public void setValue(Object value) throws InvalidAttributeException {
		if (attributType == AttributeType.FLOAT && value instanceof Float) {
			this.value = value;			
		} else if (attributType == AttributeType.INTEGER && value instanceof Integer) {
			this.value = value;
		} else if ((attributType == AttributeType.TEXT || attributType == AttributeType.URL || attributType == AttributeType.PICTURE) && value instanceof String) {
			this.value = value;			
		} else {
			throw new InvalidAttributeException(value, attributType);
		}
	}
}
