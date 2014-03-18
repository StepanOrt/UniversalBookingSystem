package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Status;
 
@Entity
@Table(name="reservation", 
uniqueConstraints = @UniqueConstraint(columnNames = { "schedule_id", "account_id" }))
public class Reservation implements Serializable {
     
    private Long id;
    private Status status;
	private Date dateCreated, dateCanceled;
	private Schedule schedule;
	private Account account;
     
    public Reservation() {}
     
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
    
    public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
    public Schedule getSchedule() {
        return schedule;
    }
     
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
     
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
    public Account getAccount() {
        return account;
    }
     
    public void setAccount(Account account) {
        this.account = account;
    }
     
    @Column(name="status")
    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "date_created")
	public Date getDateCreated() { return dateCreated; }

	public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
	
	@Column(name = "date_canceled")
	public Date getDateCanceled() { return dateCanceled; }

	public void setDateCanceled(Date dateCanceled) { this.dateCanceled = dateCanceled; }
	
	@Transient
	public boolean isValid() {
		if (status == Status.RESERVED) return true;
		return false;
	}
}
