package porfolio2.exception;

public class SingleWithdrawnLimitException extends PorfolioException {
	
	private int limit;
	
	public SingleWithdrawnLimitException(int amount, int limit) {
		super(amount);
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "SingleWithdrawnLimitException [amount=" + amount + ", limit=" + limit + "]";
	}
	
	
}
