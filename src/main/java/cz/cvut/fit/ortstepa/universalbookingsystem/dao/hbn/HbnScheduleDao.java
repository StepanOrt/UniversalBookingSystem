package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ScheduleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;

@Repository
public class HbnScheduleDao extends AbstractHbnDao<Schedule> implements ScheduleDao {
	
}
