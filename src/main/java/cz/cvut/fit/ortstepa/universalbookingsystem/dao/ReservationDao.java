package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;

public interface ReservationDao extends Dao<Reservation> {

	Reservation find(Account account, Schedule schedule);

}
