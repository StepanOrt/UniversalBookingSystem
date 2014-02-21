package cz.cvut.fit.ortstepa.universalbookingsystem.web.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.ScriptAssert;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

@ScriptAssert(
		lang = "javascript",
		script = "_this.confirmPassword.equals(_this.password)",
		message = "account.password.mismatch.message")
public class RegistrationForm {
	private String username, password, confirmPassword, firstName, lastName, email;
	private boolean marketingOk = true, acceptTerms = false;

	@NotNull
	@Size(min = 1, max = 50)
	public String getUsername() { return username; }

	public void setUsername(String userName) { this.username = userName; }

	@NotNull
	@Size(min = 6, max = 50)
	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public String getConfirmPassword() { return confirmPassword; }

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

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

	@AssertTrue(message = "{account.acceptTerms.assertTrue.message}")
	public boolean getAcceptTerms() { return acceptTerms; }

	public void setAcceptTerms(boolean acceptTerms) { this.acceptTerms = acceptTerms; }

	public void fill(Account account) {
		account.setUsername(username);
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setEmail(email);
		account.setMarketingOk(marketingOk);
		account.setAcceptTerms(acceptTerms);
		account.setEnabled(true);
	}	
}
