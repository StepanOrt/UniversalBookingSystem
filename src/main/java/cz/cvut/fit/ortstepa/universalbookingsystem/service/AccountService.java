package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
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

	public List<Account> getAll() {
		return accountDao.getAll();
	}

	public Account get(Long id) {
		List<Account> list = accountDao.getAll();
		Account account = accountDao.get(id);
		return account;
	}

	@Transactional(readOnly = false)
	public void topup(Long id, Double topup) {
		Account account = get(id);
		account.setCredit(account.getCredit() + topup);
		accountDao.update(account);		
	}
	
	@Transactional(readOnly = false)
	public void makeAdmin(Long id) {
		makeAdmin(get(id));
	}
	
	@Transactional(readOnly = false)
	public void makeUser(Long id) {
		makeUser(get(id));
	}
	
	private void makeAdmin(Account account) {
		removeRole(account, "ROLE_ADMIN");
		removeRole(account, "ROLE_USER");
		account.getRoles().add(roleDao.findByName("ROLE_ADMIN"));
		accountDao.update(account);
	}
	
	
	private void removeRole(Account account, String roleName) {
		for (Iterator<Role> iterator = account.getRoles().iterator(); iterator.hasNext();) {
			Role role = (Role) iterator.next();
			if (role.getName().equals(roleName)) {
				account.getRoles().remove(role);
				return;
			}
		}
	}

	private void makeUser(Account account) {
		removeRole(account, "ROLE_ADMIN");
		removeRole(account, "ROLE_USER");
		account.getRoles().add(roleDao.findByName("ROLE_USER"));
		accountDao.update(account);
	}

	@Transactional(readOnly = false)
	public void update(Long id, Account newAccount, BindingResult errors) {
		validateEmailUnique(newAccount.getEmail(), id, errors);
		if (!errors.hasErrors()) {
			Account account = accountDao.get(id);
			account.setCredit(newAccount.getCredit());
			account.setEmail(newAccount.getEmail());
			account.setEnabled(newAccount.isEnabled());
			account.setFirstName(newAccount.getFirstName());
			account.setLastName(newAccount.getLastName());
			account.setGroup(newAccount.getGroup());
			account.setEmailOk(newAccount.isEmailOk());
			accountDao.update(account);
		}
	}

}
