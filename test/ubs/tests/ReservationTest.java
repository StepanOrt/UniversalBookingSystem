package ubs.tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import ubs.model.AttributeType;
import ubs.model.ReservationSystem;
import ubs.model.CreditWallet;
import ubs.model.ReservationAttribute;
import ubs.model.ReservationCategoryNode;
import ubs.model.ReservationItem;
import ubs.model.ReservationTag;
import ubs.model.User;

public class ReservationTest {

	@Test
	public void reservationTest() {
		ReservationSystem bs = ReservationSystem.getInstance();
		User user = new User("Test", "test@user.com", "heslo", new CreditWallet(100));
		bs.addUser(user);
		ReservationItem ri = new ReservationItem(new ReservationCategoryNode("kategorie"));
		ri.addTag(new ReservationTag("Tag1"));
		ri.addAttributeValue(new ReservationAttribute("od", AttributeType.DATETIME), Calendar.getInstance().getTime());
		
		bs.createReservation(ri, user);
		
		assertTrue(bs.getReservationsBy(user).size() > 0);		
	}

}
