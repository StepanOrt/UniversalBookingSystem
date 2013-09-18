package ubs.tests;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ubs.core.AttributeType;
import ubs.core.ReservationCategoryNode;
import ubs.core.ReservationItem;
import ubs.core.ReservationItemAttribute;
import ubs.core.ReservationSystem;
import ubs.core.ReservationTag;


public class ReservationItemTest {

	@Test
	public void reservationItemTest() {
		ReservationSystem bs = ReservationSystem.getInstance();
		Set<ReservationCategoryNode> cn = new HashSet<ReservationCategoryNode>();
		cn.add(new ReservationCategoryNode("kategorie"));
		ReservationItem ri = new ReservationItem(cn);
		ri.addTag(new ReservationTag("Tag1"));
		ri.setAttributeValue(new ReservationItemAttribute("od", AttributeType.DATETIME), Calendar.getInstance().getTime());
		bs.createReservationItem(ri);
		
		assertEquals(bs.getReservationAllItems().iterator().hasNext(), true);	
	}

}
