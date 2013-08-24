package ubs.model;

public class TopUpAction implements Action {

	private float amount;
	
	public TopUpAction(float amount) {
		super();
		this.amount = amount;
	}

	@Override
	public void execute(BookingSystem system, Reservation reservation) {
		CreditWallet wallet = reservation.getUser().getCreditWallet();
		wallet.topup(amount);
	}

}
