package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity
@Table(name = "resource")
public class Resource {
 
    private Long id, capacity, duration;
    private Double price;
    private boolean visible;
    private Set<ResourcePropertyValue> values = new HashSet<ResourcePropertyValue>(0);
     
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    public Long getId() {
        return id;
    }
     
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="capacity")
	public Long getCapacity() {
		return capacity;
		
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	
	@Column(name="duration")
	public Long getDuration() {
		return duration;
		
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
    
	@Column(name="price")
    public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name="visible")
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@OneToMany(mappedBy = "pk.resource", cascade=CascadeType.ALL, orphanRemoval=true)
    public Set<ResourcePropertyValue> getPropertyValues() {
        return this.values ;
    }
 
    public void setPropertyValues(Set<ResourcePropertyValue> values) {
        this.values = values;
    }
}