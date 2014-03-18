package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.Dao;

public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {

	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	private Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	private String getDomainClassName() {
		return getDomainClass().getName();
	}

	private void setTimestamp(T t, String name) {
		String validMethodName = WordUtils.capitalize(name).replace(" ", "");
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setDate" + validMethodName, new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, Calendar.getInstance().getTime());
			} catch (Exception e) { /* Ignore */ }
		}		
	}
	
	@Override
	public void create(T t) {
		setTimestamp(t, "created");
		getSession().save(t);
	}

	@Override
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}

	@Override
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}

	@Override
	public List<T> getAll() {
		return getSession().createQuery("from " + getDomainClassName()).list();
	}

	@Override
	public void update(T t) {
		setTimestamp(t, "updated");
		getSession().update(t);
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public void deleteById(Serializable id) {
		delete(load(id));
	}

	@Override
	public void deleteAll() {
		getSession().createQuery("delete " + getDomainClassName()).executeUpdate();
	}

	@Override
	public long count() {
		return (Long)getSession().createQuery("select count(*) from " + getDomainClassName()).uniqueResult();
	}

	@Override
	public boolean exists(Serializable id) {
		return (get(id) != null);
	}
}
