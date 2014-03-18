package cz.cvut.fit.ortstepa.universalbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.UserDetailsDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.UserDetailsAdapter;
import cz.cvut.fit.ortstepa.universalbookingsystem.service.AccountService;

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceAdapter implements UserDetailsService {
	@Autowired
	AccountService accountService;
	@Autowired
	UserDetailsDao userDetailsDao;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException, DataAccessException {

		Account account = accountService.getAccountByEmail(email);

		if (account == null) {
			throw new UsernameNotFoundException("No user with email: " + email);
		} else if (account.getRoles().isEmpty()) {
			throw new UsernameNotFoundException("User with email " + email + " has no authorities");
		}

		UserDetailsAdapter user = new UserDetailsAdapter(account);
		user.setPassword(userDetailsDao.findPasswordByEmail(email));
		return user;
	}
}
