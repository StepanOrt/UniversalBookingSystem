package cz.cvut.fit.ortstepa.universalbookingsystem.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cz.cvut.fit.ortstepa.universalbookingsystem.service.ReservationService;

@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);
}
