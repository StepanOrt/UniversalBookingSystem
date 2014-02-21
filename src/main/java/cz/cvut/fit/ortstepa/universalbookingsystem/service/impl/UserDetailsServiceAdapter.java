package cz.cvut.fit.ortstepa.universalbookingsystem.service.impl;

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
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		Account account = accountService.getAccountByUsername(username);

		if (account == null) {
			throw new UsernameNotFoundException("No such user: " + username);
		} else if (account.getRoles().isEmpty()) {
			throw new UsernameNotFoundException("User " + username + " has no authorities");
		}

		UserDetailsAdapter user = new UserDetailsAdapter(account);
		user.setPassword(userDetailsDao.findPasswordByUsername(username));
		return user;
	}
}
