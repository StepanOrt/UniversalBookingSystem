package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Role;

public interface RoleDao extends Dao<Role> {

	Role findByName(String name);
}
