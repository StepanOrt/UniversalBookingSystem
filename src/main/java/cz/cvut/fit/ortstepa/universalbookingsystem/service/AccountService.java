package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.services.oauth2.model.Userinfoplus;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.AccountDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.dao.RoleDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Reservation;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Role;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.UserDetailsAdapter;
import cz.cvut.fit.ortstepa.universalbookingsystem.helper.GoogleAuthHelper;
import cz.cvut.fit.ortstepa.universalbookingsystem.web.form.AccountForm;

@Service
@Transactional(readOnly = true)
public class AccountService {
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private GoogleAuthHelper googleAuthHelper;
	@Autowired 
	private AccountDao accountDao;
	@Autowired 
	private RoleDao roleDao;
	
	public String getGoogleCredentials(String email) {
		Account account = accountDao.findByEmail(email);
		return account.getGoogleCredentials();
	}
	
	@Transactional(readOnly=false)
	public void updateGoogleCredentials(String email, String value) {
		Account account = accountDao.findByEmail(email);
		account.setGoogleCredentials(value);
		accountDao.update(account);
	}
	
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
		Set<Reservation> reservations = account.getReservations();
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
			account.setInternal(newAccount.getInternal());
			accountDao.update(account);
		}
	}
	
	@Transactional(readOnly=false)
	public Userinfoplus getGoogleUserinfo() {
		registerTokensDataStoreListeners();
		return googleAuthHelper.getUserinfo(authorizedUserEmail());
	}
	

	private void deleteGoogleCredentials(String id) {
		updateGoogleCredentials(id, null);
	}
	
	private void registerTokensDataStoreListeners() {
		if (authorizedUserEmail() != null) {
			StoredCredentialDataStoreFactory.getInstance().new ChangeNotifyListener(authorizedUserEmail()) {

				@Override
				public <V extends Serializable> void onChange(String id, StoredCredential value) {
					if (value == null) 
						deleteGoogleCredentials(id);
					else {
						Object[] values = new Object[] { value.getAccessToken(),
								value.getRefreshToken(),
								value.getExpirationTimeMilliseconds()}; 						
						String serialized = "";
						for (Object v : values) {
							if (v != null)	
								serialized += v.toString();
							else serialized += "null";
							if (v!=values[values.length-1]) serialized += "\t";
						}
						updateGoogleCredentials(id, serialized);
					}
				}
			};
			
			StoredCredentialDataStoreFactory.getInstance().new UpdateRequestListener(authorizedUserEmail()) {
				
				@Override
				public <V extends Serializable> StoredCredential onRequest(String id, StoredCredential value) {
					String dbCredentials = getGoogleCredentials(id);
					if (dbCredentials != null) {
						String[] values = dbCredentials.split("\t");
						if (value == null) value = new StoredCredential();
						if (!values[0].equals("null")) value.setAccessToken(values[0]); else value.setAccessToken(null);
						if (!values[1].equals("null")) value.setRefreshToken(values[1]); else value.setExpirationTimeMilliseconds(null);
						if (!values[2].equals("null")) value.setExpirationTimeMilliseconds(Long.parseLong(values[2])); else value.setExpirationTimeMilliseconds(null);
						return value;
					}
					return null;
				}
				
			};
		}
	}

	@Transactional(readOnly=false)
	public String googleConnectUrl(HttpSession session) {
		String url = googleAuthHelper.buildLoginUrl();
		session.setAttribute("state", googleAuthHelper.getStateToken());
		return url;
	}

	@Transactional(readOnly=false)
	public void authorizeGoogle(String code, String state, HttpSession session) {
		if (session.getAttribute("state").equals(state))
			googleAuthHelper.authorize(code, authorizedUserEmail());
	}
	
	private String authorizedUserEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			return ((UserDetailsAdapter)auth.getPrincipal()).getEmail();
		} catch (Exception e) {}
		return null;
	}
	
	@Transactional(readOnly=false)
	public void forgetGoogle() {
		deleteGoogleCredentials(authorizedUserEmail());
	}

}
