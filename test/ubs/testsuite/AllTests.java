package ubs.testsuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ubs.core.Reservation;
import ubs.tests.ReservationItemTest;
import ubs.tests.ReservationTest;
import ubs.tests.UserTest;


@RunWith(Suite.class)
@SuiteClasses({ ReservationItemTest.class, 
				UserTest.class, 
				ReservationTest.class })
public class AllTests {

}
