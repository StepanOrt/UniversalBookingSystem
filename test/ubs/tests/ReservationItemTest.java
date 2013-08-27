package ubs.tests;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import ubs.model.AttributeType;
import ubs.model.ReservationSystem;
import ubs.model.ReservationAttribute;
import ubs.model.ReservationCategoryNode;
import ubs.model.ReservationItem;
import ubs.model.ReservationTag;


public class ReservationItemTest {

	@Test
	public void reservationItemTest() {
		ReservationSystem bs = ReservationSystem.getInstance();
		ReservationItem ri = new ReservationItem(new ReservationCategoryNode("kategorie"));
		ri.addTag(new ReservationTag("Tag1"));
		ri.addAttributeValue(new ReservationAttribute("od", AttributeType.DATETIME), Calendar.getInstance().getTime());
		bs.createReservationItem(ri);
		
		assertEquals(bs.getReservationItems().iterator().hasNext(), true);	
	}

}
