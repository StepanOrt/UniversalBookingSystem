package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

public interface UserDetailsDao {
	String findPasswordByEmail(String email);
}
