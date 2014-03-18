package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ScheduleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ScheduleService;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

	@Autowired
	private ScheduleDao scheduleDao;

	
	@Transactional(readOnly = false)
	public void add(Schedule schedule, Errors errors) {
		if (schedule.getId() != null)
			errors.reject("id");
		else {
			scheduleDao.create(schedule);
		}
	}

	public Schedule get(Long id) {
		return scheduleDao.get(id);
	}
	
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		try {
			scheduleDao.deleteById(id);			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	@Transactional(readOnly = false)
	public void update(Schedule schedule, Errors errors) {
		scheduleDao.update(schedule);
	}

	
	public Schedule createEmptySchedule() {
		return new Schedule();
	}


	public List<Schedule> getAll() {
		return scheduleDao.getAll();
	}

}
