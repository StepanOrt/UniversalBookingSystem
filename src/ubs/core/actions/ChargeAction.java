package ubs.core.actions;

import ubs.core.CreditWallet;

public class ChargeAction implements Action {

	private float amount;
	private CreditWallet wallet;
	
	public ChargeAction(float amount) {
		this.amount = amount;
		this.wallet = null;
	}
	
	public void setCreditWallet(CreditWallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public void execute() throws Exception {
		if (wallet == null) throw new Exception("Wallet not set!");
		wallet.charge(amount);
	}

}
