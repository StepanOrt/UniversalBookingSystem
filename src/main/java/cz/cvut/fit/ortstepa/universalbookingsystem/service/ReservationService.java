package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Action;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Status;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.GoogleCalendarHelper;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.GooglePlusHelper;
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
	
	@Autowired
	private GooglePlusHelper googlePlusHelper;
	@Autowired
	private GoogleCalendarHelper googleCalendarHelper;
	
	
	@Transactional(readOnly = false)
	@PreAuthorize("hasRole('PERM_RESERVE')")
	public void reserve(Long scheduleId) {
		Schedule schedule = scheduleDao.get(scheduleId);
		if (schedule.getCapacityAvailable() == 0)
			throw new ScheduleFullException();
		Reservation reservation = get(schedule);
		Account account = getLoggedAccount();
		if (reservation == null) {
			reservation = new Reservation();
			if (priceEngine.canReserve(account, schedule)) {
				reservation.setAccount(account);
				reservation.setSchedule(schedule);
				reservation.setStatus(Status.RESERVED);
				if (account.isCalendarOk())
					reservation = googleCalendarHelper.createReservationEvent(reservation);
				if (account.isGooglePlusOk())
					reservation = googlePlusHelper.createMoment(reservation);
				reservationDao.create(reservation);
				updateCredit(account, getPrice(schedule, Action.CREATE));
				return;
			}
			throw new NotEnoughCreditException();
		}
		if (reservation.getStatus().equals(Status.CANCELED)) {
			reservation.setStatus(Status.RESERVED);
			if(account.isCalendarOk())
				reservation = googleCalendarHelper.createReservationEvent(reservation);
			if (account.isGooglePlusOk())
				reservation = googlePlusHelper.createMoment(reservation);
			reservationDao.update(reservation);
			updateCredit(account, getPrice(schedule, Action.CREATE));
			return;
		}
		throw new ReservationAlreadyExistsException();
	}

	private double getPrice(Schedule schedule, Action action) {
		return priceEngine.calculatePrice(schedule, action);
	}

	private void updateCredit(Account account, double price) {
		double credit = account.getCredit();
		credit -= price;
		account.setCredit(credit);
		accountDao.update(account);
	}

	@Transactional(readOnly = false)
	@PreAuthorize("hasRole('PERM_RESERVE')")
	public void cancel(Long scheduleId) {
		Schedule schedule = scheduleDao.get(scheduleId);
		Account account = getLoggedAccount();
		Reservation reservation = reservationDao.find(account, schedule);
		if (reservation == null) throw new ReservationNotExistException();
		reservation.setStatus(Status.CANCELED);
		reservation = googleCalendarHelper.removeReservationEvent(reservation);
		reservation = googlePlusHelper.removeMoment(reservation);
		reservationDao.update(reservation);
		updateCredit(account, getPrice(schedule, Action.CANCEL));
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
		String email = ((UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
		Account account = accountDao.findByEmail(email);
		return account;
	}
	
	public class ReservationNotExistException extends RuntimeException {}
	public class NotEnoughCreditException extends RuntimeException {}
	public class ReservationAlreadyExistsException extends RuntimeException {}
	public class ScheduleFullException extends RuntimeException {}
}
