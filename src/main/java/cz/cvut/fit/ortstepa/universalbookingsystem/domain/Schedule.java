package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name="schedule")
@AssociationOverrides({
        @AssociationOverride(name = "pk.schedule", 
            joinColumns = @JoinColumn(name = "schedule_id")),
        @AssociationOverride(name = "pk.account", 
            joinColumns = @JoinColumn(name = "USER_ID")) })
public class Schedule implements Serializable {
     
    private Long id, capacity, duration;
    private Date start;
    private String note;
	private Resource resource;
     
    public Schedule() {}
     
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }

	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
     
	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
    public Resource getResource() {
        return resource;
    }
     
    public void setResource(Resource resource) {
        this.resource = resource;
    }
     
    @Column(name="start")
    public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@Column(name="capacity")
	public Long getCapacity() {
		if (capacity == null) return resource.getCapacity();
		return capacity;
		
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	
	@Column(name="duration")
	public Long getDuration() {
		if (duration == null) return resource.getDuration();
		return duration;
		
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Column(name="note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}

