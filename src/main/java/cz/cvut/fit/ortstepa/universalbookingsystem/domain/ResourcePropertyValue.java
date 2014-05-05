package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
 
@Entity
@Table(name = "resource_property_value", 
uniqueConstraints = @UniqueConstraint(columnNames = { "resource_id", "resource_property_id" }))
public class ResourcePropertyValue implements Serializable {
     
    private Long id;
    private String value;
	private Resource resource;
	private ResourceProperty property;
     
    public ResourcePropertyValue() {}
     
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id", nullable = false)
    public Resource getResource() {
        return resource;
    }
     
    public void setResource(Resource resource) {
        this.resource = resource;
    }
     
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_property_id", nullable = false)
    public ResourceProperty getProperty() {
        return property;
    }
     
    public void setProperty(ResourceProperty property) {
        this.property = property;
    }
     
    @Column(name = "value")
    public String getValue() {
        return this.value;
    }
     
    public void setValue(String value) {
        this.value = value;
    }
}