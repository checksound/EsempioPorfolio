package porfolio2.exception;

public class AmountWithdrawnException extends PorfolioException {
	
	private int balance;
	
	public AmountWithdrawnException(int amount, int balance) {
		super(amount);
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "AmountWithdrawnException [amount=" + amount + ", balance=" + balance + "]";
	}
	
}
