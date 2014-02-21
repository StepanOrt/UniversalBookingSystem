package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

public interface UserDetailsDao {
	String findPasswordByUsername(String username);
}
