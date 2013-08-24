package ubs.tests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ubs.model.BookingSystem;
import ubs.model.CreditWallet;
import ubs.model.User;


public class UserTest {

	@Test
	public void userTest() {
		BookingSystem bs = new BookingSystem();
		bs.addUser(new User("Test", "test@user.com", "heslo", new CreditWallet(100)));
		
		Iterable<User> users = bs.getUsersByName("Test");
		for (User user : users) {
			if (user.getEmail() == "test@user.com")
				assertEquals(user.getPassword(), "heslo");
		}
	}

}
