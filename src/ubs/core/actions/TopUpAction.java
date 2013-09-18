package ubs.core.actions;

import ubs.core.CreditWallet;

public class TopUpAction implements Action {

	private float amount;
	private CreditWallet wallet;
	
	public TopUpAction(float amount) {
		super();
		this.amount = amount;
	}

	public void setWallet(CreditWallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public void execute() throws Exception {
		if (wallet == null) throw new Exception("Wallet not set!");
		wallet.topup(amount);
	}

}
