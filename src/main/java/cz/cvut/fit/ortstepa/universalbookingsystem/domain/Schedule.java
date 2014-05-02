package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
 
@Entity
@Table(name="schedule")
public class Schedule implements Serializable, Comparable<Schedule> {
     
    private Long id;
    private Integer capacity = null;
    private Integer duration = null;
    private Date start = Calendar.getInstance().getTime();
    private String note;
	private Resource resource;
	private boolean visible = false;
	private Set<Reservation> reservations;

    public Schedule() {}
     
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }

    public void setId(Long id) {
		this.id = id;
	}

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
	
	@Transient
	public Date getEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.MINUTE, duration);
		return cal.getTime();
	}

	@Column(name="capacity")
	public int getCapacity() {
		if (capacity == null) return resource.getCapacity();
		return capacity;
		
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	@Column(name="duration")
	public int getDuration() {
		if (duration == null) return resource.getDuration();
		return duration;
		
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name="note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name="visible")
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}
		
	@Transient
	public Set<Reservation> getValidReservations() {
		Set<Reservation> valids = new HashSet<Reservation>();
		for (Reservation reservation : getReservations()) {
			if (reservation.isValid()) valids.add(reservation);
		}
		return valids;
	}
	
	@Transient
	public boolean isPast() {
		return start.before(Calendar.getInstance().getTime());
	}
	
	@Transient
	public boolean isFuture() {
		return !isPast();
	}

	@Transient
	public Integer getCapacityAvailable() {
		return getCapacity() - getValidReservations().size();
	}
	
	@Transient
	public String getPrettyDuration() {
		PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
	    .appendDays()
	    .appendSuffix(" day", " days")
	    .appendSeparatorIfFieldsAfter(" ")
	    .appendHours()
	    .appendSuffix(" hour", " hours")
	    .appendSeparatorIfFieldsAfter(" ")
	    .appendMinutes()
	    .appendSuffix(" minute", " minutes")
	    .toFormatter();

		
	  Period period = new Period().withMinutes(getDuration());

	  return daysHoursMinutes.print(period.normalizedStandard());
	}

	@Override
	public int compareTo(Schedule o) {
		return this.getStart().compareTo(o.getStart());
	}
	
	
}

