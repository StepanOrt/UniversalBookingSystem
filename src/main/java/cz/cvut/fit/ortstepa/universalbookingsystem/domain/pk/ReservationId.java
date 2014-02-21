package cz.cvut.fit.ortstepa.universalbookingsystem.domain.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Account;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Schedule;
 
@Embeddable
public class ReservationId implements Serializable {
 
    private Schedule schedule;
    private Account account;
     
    @ManyToOne
    public Schedule getSchedule() {
        return schedule;
    }
 
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
     
    @ManyToOne
    public Account getAccount() {
        return account;
    }
 
    public void setAccount(Account account) {
        this.account = account;
    }
     
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
  
        ReservationId that = (ReservationId) o;
  
        if (schedule != null ? !schedule.equals(that.schedule) : that.schedule != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null)
            return false;
  
        return true;
    }
  
    public int hashCode() {
        int result;
        result = (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }
}
