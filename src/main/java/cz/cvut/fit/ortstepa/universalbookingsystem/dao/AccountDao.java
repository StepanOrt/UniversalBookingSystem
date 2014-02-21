package cz.cvut.fit.ortstepa.universalbookingsystem.dao;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

public interface AccountDao extends Dao<Account> {

	void create(Account account, String password);
	
	void updatePassword(String username, String password);

	Account findByUsername(String username);
}