package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ReservationDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;

@Repository
public class HbnReservationDao extends AbstractHbnDao<Reservation> implements
		ReservationDao {

}
