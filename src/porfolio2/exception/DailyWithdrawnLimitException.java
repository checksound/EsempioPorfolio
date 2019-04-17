package porfolio2.exception;

public class DailyWithdrawnLimitException extends PorfolioException {
	
	private int amount;
	private int total;
	
	public DailyWithdrawnLimitException(int amount, int total) {
		this.amount = amount;
		this.total = total;
	}

	@Override
	public String toString() {
		return "DailyLimitException [amount=" + amount + ", total=" + total + "]";
	}
	
	
}
