package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ReservationDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;

@Repository
public class HbnReservationDao extends AbstractHbnDao<Reservation> implements
		ReservationDao {

	@Override
	public Reservation find(Account account, Schedule schedule) {
		Criteria crt = getSession().createCriteria(Reservation.class);
		crt.add(Restrictions.eq("account", account));
		crt.add(Restrictions.eq("schedule", schedule));
		List<Reservation> l = crt.list();
		if (l.isEmpty()) return null;
		return l.get(0);
	}

}
