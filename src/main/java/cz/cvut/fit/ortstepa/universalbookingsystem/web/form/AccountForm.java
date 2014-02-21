package cz.cvut.fit.ortstepa.universalbookingsystem.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

public class AccountForm {
	private String username, firstName, lastName, email;
	private boolean marketingOk = true;

	@NotNull
	@Size(min = 1, max = 50)
	public String getUsername() { return username; }

	public void setUsername(String userName) { this.username = userName; }

	@NotNull
	@Size(min = 1, max = 50)
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	@NotNull
	@Size(min = 1, max = 50)
	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	@NotNull
	@Size(min = 6, max = 50)
	@Email
	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public boolean isMarketingOk() { return marketingOk; }

	public void setMarketingOk(boolean marketingOk) { this.marketingOk = marketingOk; }

	public static AccountForm create(Account account) {
		AccountForm form = new AccountForm();
		form.setEmail(account.getEmail());
		form.setFirstName(account.getFirstName());
		form.setLastName(account.getLastName());
		form.setMarketingOk(account.isMarketingOk());
		form.setUsername(account.getUsername());
		return form;
	}

	public void fill(Account account) {
		account.setEmail(email);
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setMarketingOk(marketingOk);
		account.setUsername(username);
	}
}
