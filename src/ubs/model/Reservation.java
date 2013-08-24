package ubs.model;

public class Reservation {
	private User user;
	private ReservationItem item;
	private ReservationStatus status;
	
	public Reservation(User user, ReservationItem item, ReservationStatus defaultStatus) {
		this.user = user;
		this.item = item;
		this.status = defaultStatus;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public ReservationItem getItem() {
		return item;
	}
}
