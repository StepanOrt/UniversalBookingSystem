package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

public interface AccountDao extends Dao<Account> {

	void create(Account account, String password);
	
	void updatePassword(String email, String password);

	Account findByEmail(String email);
}