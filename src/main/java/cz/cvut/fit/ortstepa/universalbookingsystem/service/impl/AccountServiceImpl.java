package cz.cvut.fit.ortstepa.universalbookingsystem.service.impl;

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
public class AccountServiceImpl implements AccountService {
	
	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired 
	private AccountDao accountDao;
	@Autowired 
	private RoleDao roleDao;

	@Override
	@Transactional(readOnly = false)
	public boolean registerAccount(Account account, String password,
			Errors errors) {
		validateUsername(account.getUsername(), errors);
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
	
	private void validateUsername(String username, Long id, Errors errors) {
		Account account = accountDao.findByUsername(username);
		if (account != null)  {
			if (id == null || !account.getId().equals(id)) {
				log.debug("Validation failed: duplicate username");
				errors.rejectValue("username", "error.duplicate", new String[] { username }, null);
			}
		}
	}
	
	private void validateUsername(String username, Errors errors) {
		validateUsername(username, null, errors);
	}

	@Override
	public Account getAccountByUsername(String username) {
		Account account = accountDao.findByUsername(username);
		if (account != null) { Hibernate.initialize(account.getRoles()); }
		return account;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean changePassword(String username, String password,
			Errors errors) {
		boolean valid = !errors.hasErrors();
		if (valid) {
			accountDao.updatePassword(username, password);
		}
		return valid;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateAccount(String username, AccountForm form, Errors errors) {
		Account account = getAccountByUsername(username);
		form.fill(account);
		validateUsername(account.getUsername(), account.getId(), errors);
		boolean valid = !errors.hasErrors();

		if (valid) {
			accountDao.update(account);
		}
		return valid;
	}

}
