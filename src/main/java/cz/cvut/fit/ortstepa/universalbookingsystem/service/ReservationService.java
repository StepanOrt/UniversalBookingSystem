package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.AccountDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ReservationDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ScheduleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Permission;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.UserDetailsAdapter;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Status;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.PriceEngine;

@Service
@Transactional(readOnly = true)
public class ReservationService {

	@Autowired
	private ReservationDao reservationDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private PriceEngine priceEngine;
	
	@Transactional(readOnly = false)
	@PreAuthorize("hasRole('PERM_RESERVE')")
	public void reserve(Long scheduleId) {
		Schedule schedule = scheduleDao.get(scheduleId);
		Reservation reservation = get(schedule);
		if (reservation == null) {
			reservation = new Reservation();
			String email = getLoggedAccount().getEmail();
			Account account = accountDao.findByEmail(email);
			if (priceEngine.canReserve(account, schedule)) {
				reservation.setAccount(account);
				reservation.setSchedule(schedule);
				reservation.setStatus(Status.RESERVED);
				reservationDao.create(reservation);
				return;
			}
			throw new NotEnoughCreditException();
		}
		if (reservation.getStatus().equals(Status.CANCELED)) {
			reservation.setStatus(Status.RESERVED);
			reservationDao.update(reservation);
			return;
		}
		throw new ReservationAlreadyExistsException();
	}

	@Transactional(readOnly = false)
	@PreAuthorize("hasRole('PERM_RESERVE')")
	public void cancel(Long scheduleId) {
		Schedule schedule = scheduleDao.get(scheduleId);
		Account account = getLoggedAccount();
		Reservation reservation = reservationDao.find(account, schedule);
		if (reservation == null) throw new ReservationNotExistException();
		reservation.setStatus(Status.CANCELED);
		reservationDao.update(reservation);
	}
	
	public Reservation get(Schedule schedule) {
		if (!canManageReservations()) return null;
		Account account = getLoggedAccount();
		return reservationDao.find(account, schedule);
	}
	
	private boolean canManageReservations() {
		Permission p = new Permission();
		p.setName("PERM_RESERVE");
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(p);
	}

	private Account getLoggedAccount() {
		Account account = ((UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
		return account;
	}
	
	public class ReservationNotExistException extends RuntimeException {}
	public class NotEnoughCreditException extends RuntimeException {}
	public class ReservationAlreadyExistsException extends RuntimeException {}
}
