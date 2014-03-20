package cz.cvut.fit.ortstepa.universalbookingsystem.helper;

import org.springframework.stereotype.Component;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;

@Component
public class PriceEngine {

	public boolean canReserve(Account account, Schedule schedule) {
		return true;
	}

}
