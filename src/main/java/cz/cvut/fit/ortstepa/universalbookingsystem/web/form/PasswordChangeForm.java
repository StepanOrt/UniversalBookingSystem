package cz.cvut.fit.ortstepa.universalbookingsystem.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(
		lang = "javascript",
		script = "_this.confirmPassword.equals(_this.password)",
		message = "account.password.mismatch.message")
public class PasswordChangeForm {
	private String currentPassword, password, confirmPassword;


	@NotNull
	@Size(min = 6, max = 50)
	public String getCurrentPassword() { return currentPassword; }

	public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
	
	@NotNull
	@Size(min = 6, max = 50)
	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public String getConfirmPassword() { return confirmPassword; }

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
