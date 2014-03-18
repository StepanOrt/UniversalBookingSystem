package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.AccountDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.dao.RoleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Role;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.AccountForm;

@Service
@Transactional(readOnly = true)
public class AccountService {
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired 
	private AccountDao accountDao;
	@Autowired 
	private RoleDao roleDao;

	
	@Transactional(readOnly = false)
	public boolean registerAccount(Account account, String password,
			Errors errors) {
		validateEmailUnique(account.getEmail(), errors);
		boolean valid = !errors.hasErrors();

		if (valid) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleDao.findByName("ROLE_USER"));
			roles.add(roleDao.findByName("ROLE_REGISTERED"));
			account.setRoles(roles);
			accountDao.create(account, password);
		}

		return valid;
	}
	
	private void validateEmailUnique(String email, Long id, Errors errors) {
		Account account = accountDao.findByEmail(email);
		if (account != null)  {
			if (id == null || !account.getId().equals(id)) {
				log.debug("Validation failed: duplicate email");
				errors.rejectValue("email", "error.duplicate", new String[] { email }, null);
			}
		}
	}
	
	private void validateEmailUnique(String email, Errors errors) {
		validateEmailUnique(email, null, errors);
	}

	
	public Account getAccountByEmail(String email) {
		Account account = accountDao.findByEmail(email);
		if (account != null) { Hibernate.initialize(account.getRoles()); }
		return account;
	}

	
	@Transactional(readOnly = false)
	public boolean changePassword(String email, String password,
			Errors errors) {
		boolean valid = !errors.hasErrors();
		if (valid) {
			accountDao.updatePassword(email, password);
		}
		return valid;
	}

	
	@Transactional(readOnly = false)
	public boolean updateAccount(String email, AccountForm form, Errors errors) {
		Account account = getAccountByEmail(email);
		form.fill(account);
		validateEmailUnique(account.getEmail(), account.getId(), errors);
		boolean valid = !errors.hasErrors();

		if (valid) {
			accountDao.update(account);
		}
		return valid;
	}

}
