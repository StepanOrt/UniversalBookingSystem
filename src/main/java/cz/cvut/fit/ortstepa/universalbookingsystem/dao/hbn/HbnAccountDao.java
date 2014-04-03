package cz.cvut.fit.ortstepa.universalbookingsystem.dao.hbn;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import cz.cvut.fit.ortstepa.universalbookingsystem.dao.AccountDao;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

@Repository
public class HbnAccountDao extends AbstractHbnDao<Account> implements AccountDao {

	private static final Logger log = LoggerFactory.getLogger(HbnAccountDao.class);

	private static final String UPDATE_PASSWORD_SQL =
			"update account set password = ? where email = ?";

	@Autowired 
	private JdbcTemplate jdbcTemplate;
	@Autowired 
	private PasswordEncoder passwordEncoder;

	@Override
	public void create(Account account, String password) {
		log.debug("Creating account: {}", account);
		create(account);

		log.debug("Updating password");
		updatePassword(account.getEmail(), password);
	}

	@Override
	public Account findByEmail(String email) {
		return (Account) getSession()
				.getNamedQuery("findAccountByEmail")
				.setParameter("email", email)
				.uniqueResult();
	}

	@Override
	public void updatePassword(String email, String password) {
		String encPassword = passwordEncoder.encode(password);
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, email);
	}

	@Override
	public Account getByGoogleCredentials(String value) {
		String query = "from " + getDomainClassName() + " where googleCredentials = :value";
		Account account = (Account)getSession().createQuery(query).setParameter("value", value).uniqueResult();
		return account;
	}
}
