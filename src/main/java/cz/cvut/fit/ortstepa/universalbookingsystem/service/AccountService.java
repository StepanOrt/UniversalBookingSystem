package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import org.springframework.validation.Errors;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.AccountForm;

public interface AccountService {
	boolean registerAccount(Account account, String password, Errors errors);
	Account getAccountByUsername(String username);
	boolean changePassword(String username, String password, Errors errors);
	boolean updateAccount(String username, AccountForm form, Errors errors);
}
