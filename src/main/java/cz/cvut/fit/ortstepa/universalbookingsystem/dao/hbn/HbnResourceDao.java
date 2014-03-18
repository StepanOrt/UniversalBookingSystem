package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ResourceDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Resource;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourcePropertyValue;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.DumpToString;

@Repository
public class HbnResourceDao extends AbstractHbnDao<Resource> implements ResourceDao {

	private static final Logger log = LoggerFactory.getLogger(HbnResourceDao.class);
	
	@Override
	public void create(Resource t) {
		super.create(t);
		updatePropertyValues(t);
	}


	@Override
	public void update(Resource t) {
		updatePropertyValues(t);
		super.update(t);
	}
	
	
	private void updatePropertyValues(Resource t) {
		log.debug("\n"+DumpToString.dump(t));
		for (ResourcePropertyValue resourcePropertyValue : t.getPropertyValues()) {
			if(resourcePropertyValue.getId() == null) {
				getSession().save(resourcePropertyValue);
				resourcePropertyValue = (ResourcePropertyValue) getSession().get(ResourcePropertyValue.class, resourcePropertyValue.getId());
			}
		}
	}
	
}
