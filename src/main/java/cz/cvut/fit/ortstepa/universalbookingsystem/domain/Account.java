package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;

@NamedQuery(
		name = "findAccountByEmail",
		query = "from Account where email = :email")
@Entity
@Table(name = "account")
public class Account {
	private Long id;
	private Double credit = 0.0d;
	private String firstName, lastName, email;
	private boolean marketingOk = true, acceptTerms = false, enabled = true, emailOk = true, calendarOk = true, twitterOk = true;
	private String googleCredentials;
	private Date dateCreated;
	private Collection<Role> roles = new HashSet<Role>();
	private AccountGroup group = null;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "first_name")
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "last_name")
	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	@Transient
	public String getFullName() { return firstName + " " + lastName; }

	@NotNull
	@Size(min = 6, max = 50)
	@Email
	@Column(name = "email")
	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	@Column(name = "marketing_ok")
	public boolean isMarketingOk() { return marketingOk; }

	public void setMarketingOk(boolean marketingOk) { this.marketingOk = marketingOk; }

	@AssertTrue(message = "{account.acceptTerms.assertTrue.message}")
	@Column(name = "accept_terms")
	public boolean getAcceptTerms() { return acceptTerms; }

	public void setAcceptTerms(boolean acceptTerms) { this.acceptTerms = acceptTerms; }

	@Column(name = "enabled")
	public boolean isEnabled() { return enabled; }

	public void setEnabled(boolean enabled) { this.enabled = enabled; }

	@Column(name = "email_ok")
	public boolean isEmailOk() {
		return emailOk;
	}

	public void setEmailOk(boolean emailOk) {
		this.emailOk = emailOk;
	}

	@Column(name = "calendar_ok")
	public boolean isCalendarOk() {
		return calendarOk;
	}

	public void setCalendarOk(boolean calendarOk) {
		this.calendarOk = calendarOk;
	}

	@Column(name = "twitter_ok")
	public boolean isTwitterOk() {
		return twitterOk;
	}

	public void setTwitterOk(boolean twitterOk) {
		this.twitterOk = twitterOk;
	}
	
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	public AccountGroup getGroup() {
		return group;
	}
	
	public void setGroup(AccountGroup group) {
		this.group = group;
	}
	
	@Column(name = "credit")
	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name = "account_role",
		joinColumns = { @JoinColumn(name = "account_id", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	public Collection<Role> getRoles() { return roles; }

	public void setRoles(Collection<Role> roles) { this.roles = roles; }
	
	@Transient
	public Set<Permission> getPermissions() {
		Set<Permission> perms = new HashSet<Permission>();
		for (Role role : roles) { perms.addAll(role.getPermissions()); }
		return perms;
	}
	
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(getRoles());
		authorities.addAll(getPermissions());
		return authorities;
	}
	
	@Transient
	public boolean isAdmin() {
		for (Role role : getRoles()) {
			if (role.getName().equals("ROLE_ADMIN")) return true;
		}
		return false;
	}
	
	@Transient
	public boolean isUser() {
		for (Role role : getRoles()) {
			if (role.getName().equals("ROLE_USER")) return true;
		}
		return false;
	}

	@Column(name = "date_created")
	public Date getDateCreated() { return dateCreated; }

	public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("firstName", firstName)
			.append("lastName", lastName)
			.append("email", email)
			.append("marketingOk", marketingOk)
			.append("acceptTerms", acceptTerms)
			.toString();			
	}

	@Column(name = "google_credentials")
	public String getGoogleCredentials() {
		return googleCredentials;
	}

	public void setGoogleCredentials(String googleCredentials) {
		this.googleCredentials = googleCredentials;
	}	
	
	
}
