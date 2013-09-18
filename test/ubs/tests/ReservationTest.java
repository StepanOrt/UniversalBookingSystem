package ubs.tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import ubs.core.AttributeType;
import ubs.core.CreditWallet;
import ubs.core.ReservationCategoryNode;
import ubs.core.ReservationItem;
import ubs.core.ReservationItemAttribute;
import ubs.core.ReservationSystem;
import ubs.core.ReservationTag;
import ubs.core.User;

public class ReservationTest {

	@Test
	public void reservationTest() {
		ReservationSystem bs = ReservationSystem.getInstance();
		User user = new User("Test", "test@user.com", "heslo", new CreditWallet(100));
		bs.addUser(user);
		ReservationItem ri = new ReservationItem(new ReservationCategoryNode("kategorie"));
		ri.addTag(new ReservationTag("Tag1"));
		ri.setAttributeValue(new ReservationItemAttribute("od", AttributeType.DATETIME), Calendar.getInstance().getTime());
		
		bs.createReservation(ri, user);
		
		assertTrue(bs.getReservations(user).size() > 0);		
	}

}
