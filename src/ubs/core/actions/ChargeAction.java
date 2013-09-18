package ubs.core.actions;

import ubs.core.Wallet;

public class ChargeAction extends Action {

	private float amount;
	private Wallet wallet;
	
	public ChargeAction(float amount) {
		this.amount = amount;
		this.wallet = null;
	}
	
	public void setCreditWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public void execute() throws Exception {
		if (wallet == null) throw new Exception("Wallet not set!");
		wallet.charge(amount);
	}

}
