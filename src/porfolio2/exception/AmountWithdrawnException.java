package porfolio2.exception;

public class AmountWithdrawnException extends PorfolioException {
	
	private int amount;
	private int balance;
	
	public AmountWithdrawnException(int amount, int balance) {
		this.amount = amount;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "AmountWithdrawnException [amount=" + amount + ", balance=" + balance + "]";
	}
	
}
