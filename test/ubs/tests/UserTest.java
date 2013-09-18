package ubs.tests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ubs.core.ReservationSystem;
import ubs.core.User;
import ubs.core.Wallet;


public class UserTest {

	@Test
	public void userTest() {
		ReservationSystem bs = ReservationSystem.getInstance();
		bs.addUser(new User("Test", "test@user.com", "heslo", new Wallet(100)));
		
		Iterable<User> users = bs.getUsersByName("Test");
		for (User user : users) {
			if (user.getEmail() == "test@user.com")
				assertEquals(user.getPassword(), "heslo");
		}
	}

}
