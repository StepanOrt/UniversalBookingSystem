package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Status;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.pk.ReservationId;
 
@Entity
@Table(name="reservation")
@AssociationOverrides({
        @AssociationOverride(name = "pk.schedule", 
            joinColumns = @JoinColumn(name = "schedule_id")),
        @AssociationOverride(name = "pk.account", 
            joinColumns = @JoinColumn(name = "USER_ID")) })
public class Reservation implements Serializable {
     
    private ReservationId pk = new ReservationId();
    private Status status;
	private Date dateCreated, dateCanceled;
     
    public Reservation() {}
     
    @EmbeddedId
    public ReservationId getPk() {
        return pk;
    }
     
    public void setPk(ReservationId pk) {
        this.pk = pk;
    }
     
    @Transient
    public Schedule getSchedule() {
        return getPk().getSchedule();
    }
     
    public void setSchedule(Schedule schedule) {
        getPk().setSchedule(schedule);
    }
     
    @Transient
    public Account getAccount() {
        return getPk().getAccount();
    }
     
    public void setAccount(Account account) {
        getPk().setAccount(account);
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

	public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
  
        Reservation that = (Reservation) o;
  
        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;
  
        return true;
    }
  
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
