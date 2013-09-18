package ubs.core.actions;

import ubs.core.Wallet;

public class TopUpAction extends Action {

	private float amount;
	private Wallet wallet;
	
	public TopUpAction(float amount) {
		super();
		this.amount = amount;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public void execute() throws Exception {
		if (wallet == null) throw new Exception("Wallet not set!");
		wallet.topup(amount);
	}

}
