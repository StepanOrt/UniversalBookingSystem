package ubs.model;

public class User {
	private String name;
	private String email;
	private String password;
	private CreditWallet creditWallet;
	private boolean emailNotifications;
	private boolean socialNetworkPosts;
	private boolean calendarUpdate;
	private String googleAccountID;
	private String facebookID;
	
	public User(String name, String email, String password, CreditWallet creditWallet) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.creditWallet = creditWallet;
		emailNotifications = true;
		socialNetworkPosts = true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public CreditWallet getCreditWallet() {
		return creditWallet;
	}
	public void setCreditWallet(CreditWallet creditWallet) {
		this.creditWallet = creditWallet;
	}
	public boolean isEmailNotifications() {
		return emailNotifications;
	}
	public void setEmailNotifications(boolean emailNotifications) {
		this.emailNotifications = emailNotifications;
	}
	public boolean isSocialNetworkPosts() {
		return socialNetworkPosts;
	}
	public void setSocialNetworkPosts(boolean socialNetworkPosts) {
		this.socialNetworkPosts = socialNetworkPosts;
	}
	public boolean isCalendarUpdate() {
		return calendarUpdate;
	}
	public void setCalendarUpdate(boolean calendarUpdate) {
		this.calendarUpdate = calendarUpdate;
	}
	public String getGoogleAccountID() {
		return googleAccountID;
	}
	public void setGoogleAccountID(String googleAccountID) {
		this.googleAccountID = googleAccountID;
	}
	public String getFacebookID() {
		return facebookID;
	}
	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User u = (User)obj;
			if (email.equals(u.getEmail())) return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int)(((long)email.hashCode() + (long)this.hashCode()) % (long)Integer.MAX_VALUE + (long)1);
	}
	
	
	
}
