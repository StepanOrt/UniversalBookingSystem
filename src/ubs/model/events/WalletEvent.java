package ubs.model.events;


public class WalletEvent extends Event {
	
	private float balance;
	private float amount;

	public WalletEvent(EventType type, float balance, float amount) throws InvalidEventTypeException {
		super(type);
		this.balance = balance;
		this.amount = amount;
	}

	public float getBalance() {
		return balance;
	}



	public float getAmount() {
		return amount;
	}


	@Override
	protected EventType[] validEventTypes() {
		return new EventType[] { 
				
			EventType.TOPUP, 
			EventType.CHARGE 
			
		};
	}
}
