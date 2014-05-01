package cz.cvut.fit.ortstepa.universalbookingsystem.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;

public class AccountForm {
	private String firstName, lastName, email, currentPassword; 
	private boolean calendarOk = true, googlePlusOk = true;
	private String internal;

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

	public boolean isCalendarOk() {	return calendarOk; }

	public void setCalendarOk(boolean calendarOk) {	this.calendarOk = calendarOk; }

	
	public boolean isGooglePlusOk() { return googlePlusOk; }

	public void setGooglePlusOk(boolean googlePlusOk) { this.googlePlusOk = googlePlusOk; }

	public String getCurrentPassword() { return currentPassword; }

	public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }

	public String getInternal() { return internal; }
	
	public void setInternal(String internal) {	this.internal = internal; }
	
	public static AccountForm create(Account account) {
		AccountForm form = new AccountForm();
		form.setEmail(account.getEmail());
		form.setFirstName(account.getFirstName());
		form.setLastName(account.getLastName());
		form.setCalendarOk(account.isCalendarOk());
		form.setGooglePlusOk(account.isGooglePlusOk());
		form.setInternal(account.getInternal());
		return form;
	}


	public void fill(Account account) {
		account.setEmail(email);
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setCalendarOk(calendarOk);
		account.setGooglePlusOk(googlePlusOk);
		account.setInternal(internal);
	}
}
