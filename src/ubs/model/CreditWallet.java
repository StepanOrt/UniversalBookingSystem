package ubs.model;

public class CreditWallet {
	private float balance;
	
	public CreditWallet(float balance) {
		balance = 0;
	}

	public void topup(float amount) {
		balance += amount;
	}
	
	public void charge(float amount) {
		balance -= amount;
	}
	
	public float getBalance() {
		return balance;
	}
}
