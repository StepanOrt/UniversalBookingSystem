package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;

public interface ResourcePropertyDao extends Dao<ResourceProperty> {

	public ResourceProperty getByName(String name);
}
