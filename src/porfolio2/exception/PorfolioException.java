package porfolio2.exception;

/**
 * 
 * @author cam
 *
 */
public class PorfolioException extends Exception {
	
	protected final int amount;
	
	public PorfolioException(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String getMessage() {
		return toString();
	}
}
