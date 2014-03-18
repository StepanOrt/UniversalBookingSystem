package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ReservationDao;

@Service
@Transactional(readOnly = true)
public class ReservationService {

	@Autowired
	private ReservationDao reservationDao;
	
	
}
