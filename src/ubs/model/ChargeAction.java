package ubs.model;

public class ChargeAction implements Action {

	private float amount;
	
	public ChargeAction(float amount) {
		super();
		this.amount = amount;
	}

	@Override
	public void execute(ReservationSystem system, Reservation reservation) {
		CreditWallet wallet = reservation.getUser().getCreditWallet();
		wallet.charge(amount);
	}

}
