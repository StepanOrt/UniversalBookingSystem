package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ResourcePropertyDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;

@Repository
public class HbnResourcePropertyDao extends AbstractHbnDao<ResourceProperty> implements
		ResourcePropertyDao {

	@Override
	public ResourceProperty getByName(String name) {
		Criteria crt = getSession().createCriteria(ResourceProperty.class);
		crt.add(Restrictions.eq("name", name));
		crt.setFirstResult(0);
		crt.setMaxResults(1);
		return ((List<ResourceProperty>)crt.list()).get(0);
	}
}
