package porfolio2.exception;

public class DailyWithdrawnLimitException extends PorfolioException {
	
	private int total;
	
	public DailyWithdrawnLimitException(int amount, int total) {
		super(amount);
		this.total = total;
	}

	@Override
	public String toString() {
		return "DailyLimitException [amount=" + amount + ", total=" + total + "]";
	}
	
	
}
