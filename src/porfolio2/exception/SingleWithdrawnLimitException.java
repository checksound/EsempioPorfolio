package porfolio2.exception;

public class SingleWithdrawnLimitException extends PorfolioException {
	
	private int amount;
	private int limit;
	
	public SingleWithdrawnLimitException(int amount, int limit) {
		this.amount = amount;
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "SingleWithdrawnLimitException [amount=" + amount + ", limit=" + limit + "]";
	}
	
	
}
