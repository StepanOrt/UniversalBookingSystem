package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.tools.Dump;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ResourceDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.dao.ResourcePropertyDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Resource;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourcePropertyValue;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.DumpToString;

@Service
@Transactional(readOnly = true)
public class ResourceService {

	private static final Logger log = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private ResourcePropertyDao resourcePropertyDao;
	
	
	@Transactional(readOnly = false)
	public void add(Resource resource) {
		updatePropertyValues(resource);
		log.debug("adding Resource:\n" + DumpToString.dump(resource));
		resourceDao.create(resource);
		log.debug("added Resource:\n" + DumpToString.dump(resource));
	}

	
	public Resource get(Long id) {
		log.debug("getting resource with id: " + id);
		Resource resource = resourceDao.get(id);
		createPropertyValueMap(resource);
		log.debug("got Resource:\n" + DumpToString.dump(resource));
		return resource;
	}
	
	public Resource getEager(Long id) {
		Resource resource = get(id);
		Hibernate.initialize(resource.getSchedules());
		return resource;
	}

	
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		try {
			log.debug("deleting resource with id: " + id);
			resourceDao.deleteById(id);
			log.debug("deleted resource with id: " + id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	@Transactional(readOnly = false)
	public void update(Resource resource, Errors errors) {
		if (resource.getId() != null) {
			Resource inDb = resourceDao.get(resource.getId());
			inDb.setCapacity(resource.getCapacity());
			inDb.setDuration(resource.getDuration());
			inDb.setPrice(resource.getPrice());
			inDb.setPropertyValuesMap(resource.getPropertyValuesMap());
			inDb.setVisible(resource.isVisible());
			resource = inDb;
		}
		log.debug("updating resource:\n" + DumpToString.dump(resource));
		updatePropertyValues(resource);
		resourceDao.update(resource);
		log.debug("updated resource:\n" + DumpToString.dump(resource));
	}

	
	public Resource createEmptyResource() {
		Resource resource = new Resource();
		createPropertyValueMap(resource);
		return resource;
	}

	
	public List<Resource> getAll() {
		log.debug("getting all resources");
		List<Resource> resources = resourceDao.getAll();
		for (Resource resource : resources) {
			createPropertyValueMap(resource);
			log.debug("got Resource:\n" + DumpToString.dump(resource));
		}
		return resources;
	}

	private void addDefaultProperties(Resource resource) {
		if (resource.getPropertyValues() == null) resource.setPropertyValues(new HashSet<ResourcePropertyValue>());
		List<ResourceProperty> properties = resourcePropertyDao.getAll();
		for (ResourceProperty property : properties) {
			boolean found = false;
			for (ResourcePropertyValue propertyValue : resource.getPropertyValues()) {
				if (propertyValue.getProperty().equals(property)) {
					found = true;
					break;
				}
			}
			if (!found) {
				ResourcePropertyValue newResourcePropertyValue = new ResourcePropertyValue();
				newResourcePropertyValue.setProperty(property);
				newResourcePropertyValue.setResource(resource);
				newResourcePropertyValue.setValue(property.getDefaultValue());
				resource.getPropertyValues().add(newResourcePropertyValue);
			}
		}
	}
	
	private void createPropertyValueMap(Resource resource) {
		addDefaultProperties(resource);
		Map<String, String> valuesMap = new HashMap<String, String>();
		if (resource.getPropertyValues() != null) {
	    	for (ResourcePropertyValue resourcePropertyValue : resource.getPropertyValues()) {
	    		String name = resourcePropertyValue.getProperty().getName();
	    		String value = resourcePropertyValue.getValue();
	    		valuesMap.put(name, value);
	    	}
		}
    	resource.setPropertyValuesMap(valuesMap);
	}
	
	private void updatePropertyValues(Resource resource) {
		addDefaultProperties(resource);
		for (ResourcePropertyValue resourcePropertyValue : resource.getPropertyValues()) {
			for (Entry<String, String> entry : resource.getPropertyValuesMap().entrySet()) {
				if (resourcePropertyValue.getProperty().getName().equals(entry.getKey())) {
					resourcePropertyValue.setValue(entry.getValue());
				}				
			}
		}
	}
}
