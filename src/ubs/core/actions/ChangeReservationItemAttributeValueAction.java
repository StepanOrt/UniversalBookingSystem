package ubs.core.actions;

import ubs.core.ReservationItem;
import ubs.core.ReservationItemAttribute;

public class ChangeReservationItemAttributeValueAction implements Action {

	private ReservationItemAttribute attribute;
	private Object value;
	private ReservationItem reservationItem;

	public ChangeReservationItemAttributeValueAction(ReservationItemAttribute attribute) {
		this.attribute = attribute;
	}
	
	
	public void setValue(Object value) {
		this.value = value;
	}

	public void setReservationItem(ReservationItem reservationItem) {
		this.reservationItem = reservationItem;
	}


	@Override
	public void execute() throws Exception {
		if (reservationItem == null || value == null) throw new Exception("Item or value not set!");
		reservationItem.setAttributeValue(attribute, value);
	}

}
