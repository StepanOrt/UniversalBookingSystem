package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.RoleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Role;

@Repository
public class HbnRoleDao extends AbstractHbnDao<Role> implements RoleDao {

	public Role findByName(String name) {
		Query q = getSession().getNamedQuery("findRoleByName");
		q.setParameter("name", name);
		return (Role) q.uniqueResult();
	}
}