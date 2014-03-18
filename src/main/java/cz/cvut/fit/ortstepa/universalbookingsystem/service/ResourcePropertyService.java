package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ResourcePropertyDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.PropertyType;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.ResourcePropertyService;

@Service
@Transactional(readOnly = true)
public class ResourcePropertyService {

	@Autowired
	private ResourcePropertyDao resourcePropertyDao;
	
	
	@Transactional(readOnly = false)
	public void add(ResourceProperty resourceProperty, Errors errors) {
		if (resourceProperty.getId() != null) 
			errors.reject("id");
		else
			resourcePropertyDao.create(resourceProperty);
			
	}

	
	public ResourceProperty getById(Long id) {
		return resourcePropertyDao.get(id);
	}

	
	public ResourceProperty getByName(String name) {
		return resourcePropertyDao.getByName(name);
	}

	
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		try {
			resourcePropertyDao.deleteById(id);			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	public List<ResourceProperty> getAll() {
		return resourcePropertyDao.getAll();
	}

	
	@Transactional(readOnly = false)
	public void update(ResourceProperty resourceProperty, Errors errors) {
		if (resourceProperty.getId() == null  || !resourcePropertyDao.exists(resourceProperty.getId())) 
			errors.reject("id");
		else
			resourcePropertyDao.update(resourceProperty);
	}

	
	public List<ResourceProperty> list(PropertyType propertyType) {
		List<ResourceProperty> selection = new ArrayList<ResourceProperty>();
		for (ResourceProperty resourceProperty : resourcePropertyDao.getAll()) {
			if (resourceProperty.getType().equals(propertyType)) {
				selection.add(resourceProperty);
			}
		}
		return selection;
	}


	public Map<String, String> getMap() {
		List<ResourceProperty> list = getAll();
		Map<String, String> map = new HashMap<String, String>();
		for (ResourceProperty resourceProperty : list) {
			map.put(resourceProperty.getName(), resourceProperty.getType().toString());
		}
		return map;
	}
}
