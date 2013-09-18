package ubs.core;

public class Wallet {
	private float balance;
	
	public Wallet(float balance) {
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
