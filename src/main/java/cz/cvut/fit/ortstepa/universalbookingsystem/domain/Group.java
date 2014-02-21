package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Group {
	
	private Long id;
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }

	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	@Column(name = "name")
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
}
