package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@NamedQuery(name = "findRoleByName", query = "from Role where name= :name")
public class Role implements GrantedAuthority {
	private Long id;
	private String name;
	private Set<Permission> permissions = new HashSet<Permission>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "role_permission",
		joinColumns = { @JoinColumn(name = "role_id") },
		inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	public Set<Permission> getPermissions() { return permissions; }

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	@Transient
	public String getAuthority() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		GrantedAuthority ga = (GrantedAuthority) o;
		return (getAuthority().equals(ga.getAuthority()));
	}

	@Override
	public int hashCode() {
		return getAuthority().hashCode();
	}
}
