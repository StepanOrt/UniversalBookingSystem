package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends Object> {
	
	void create(T t);
	
	T get(Serializable t);
	
	T load(Serializable id);
	
	List<T> getAll();
		
	void update(T t);
	
	void delete(T t);
	
	void deleteById(Serializable t);
	
	void deleteAll();
	
	long count();
	
	boolean exists(Serializable id);
}
