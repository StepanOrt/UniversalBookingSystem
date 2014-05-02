package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
 
@Entity
@Table(name = "resource")
public class Resource {
 
    private Long id;
    private int capacity = 1;
    private int duration = 60;
    private double price = 0.0;
    private boolean visible = false;
    private Set<ResourcePropertyValue> propertyValues;
	private Set<Schedule> schedules;
	
	@Transient
	private Map<String, String> propertyValuesMap;
	
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
	public int getCapacity() {
		return capacity;
		
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	@Column(name="duration")
	public int getDuration() {
		return duration;
		
	}

	public void setDuration(int duration) {
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

	@OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
    public Set<ResourcePropertyValue> getPropertyValues() {
        return propertyValues ;
    }
 
    public void setPropertyValues(Set<ResourcePropertyValue> values) {
        this.propertyValues = values;
    }
    
    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
    public Set<Schedule> getSchedules() {
    	return schedules;
    }
    
    public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}
    
    @Transient
    public Set<Schedule> getVisibleSchedules() {
		Set<Schedule> selection = new HashSet<Schedule>();
		for (Schedule schedule : getSchedules()) {
			if (schedule.isVisible())
				selection.add(schedule);
		}
		return selection;
	}
    
    @Transient
	public void setPropertyValuesMap(Map<String, String> propertyValuesMap) {
		this.propertyValuesMap = propertyValuesMap;
	}

	@Transient
	public Map<String, String> getPropertyValuesMap() {
    	return propertyValuesMap;
	}
    
    @Transient
    public List<Schedule> getSortedVisibleSchedules() {
    	List<Schedule> list = new ArrayList<Schedule>(getVisibleSchedules());
    	Collections.sort(list);
    	return list;
    }
    
    @Transient
    public List<Schedule> getSortedSchedules() {
    	List<Schedule> list = new ArrayList<Schedule>(getSchedules());
    	Collections.sort(list);
    	return list;
    }

    @Transient
	@Override
	public String toString() {
    	String out = "Reservation: ";
		for (ResourcePropertyValue propertyValue : getPropertyValues()) {
			out += propertyValue.getProperty().getName() + "=" + propertyValue.getValue() + " ";
		}
		return out.substring(0, out.length() - 1);
	}
    
    
    
    
}