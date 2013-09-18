package ubs.tests;

import static org.junit.Assert.*;

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
import ubs.core.User;
import ubs.core.Wallet;

public class ReservationTest {

	@Test
	public void reservationTest() {
		ReservationSystem bs = ReservationSystem.getInstance();
		User user = new User("Test", "test@user.com", "heslo", new Wallet(100));
		bs.addUser(user);
		Set<ReservationCategoryNode> cn = new HashSet<ReservationCategoryNode>();
		cn.add(new ReservationCategoryNode("kategorie"));
		ReservationItem ri = new ReservationItem(cn);
		ri.addTag(new ReservationTag("Tag1"));
		ri.setAttributeValue(new ReservationItemAttribute("od", AttributeType.DATETIME), Calendar.getInstance().getTime());
		
		bs.createReservation(ri, user);
		
		assertTrue(bs.getReservations(user).size() > 0);		
	}

}
