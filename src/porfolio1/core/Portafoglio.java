package porfolio1.core;

import porfolio1.exception.DailyWithdrawnLimitException;
import porfolio1.exception.AmountWithdrawnException;
import porfolio1.exception.SingleWithdrawnLimitException;

public class Portafoglio {
	
	private int prelievoGiornaliero = 0;
	
	private int disponibilita;
	
	public final int SINGLE_WITHDRAWAL_LIMIT;
	public final int DAYLY_WITHDRAWAL_LIMIT;
	
	public Portafoglio() {
		this(0, 500, 1500);
	}
	
	public Portafoglio(int singleWithdrawalLimit, int daylyWithdrawalLimit) {
		this(0, singleWithdrawalLimit, daylyWithdrawalLimit);
	}
	
	public Portafoglio(int disponibilita, 
			int singleWithdrawalLimit, int daylyWithdrawalLimit) {
		
		this.disponibilita = disponibilita;
		this.SINGLE_WITHDRAWAL_LIMIT = singleWithdrawalLimit;
		this.DAYLY_WITHDRAWAL_LIMIT = daylyWithdrawalLimit;
	}
	
	
	/**
	 * 
	 * @param value
	 * @return la disponibilità ad operazione avvenuta
	 */
	public int versa(int value) {
		this.disponibilita += value;
		return this.disponibilita;
	}
	
	/**
	 * 
	 * @param value
	 * @return la disponibilità ad operazione avvenuta
	 * @throws AmountWithdrawnException se la disponibilità è < della richiesta di 
	 * prelievo.
	 * @throws SingleWithdrawnLimitException
	 * @throws DailyWithdrawnLimitException
	 * 
	 */
	public int preleva(int value) throws AmountWithdrawnException, 
		SingleWithdrawnLimitException, DailyWithdrawnLimitException {
		
		if(this.SINGLE_WITHDRAWAL_LIMIT< value)
			throw new SingleWithdrawnLimitException("Non si può prelevare più di: " + 
					this.SINGLE_WITHDRAWAL_LIMIT + ", non è possibile prelevare: " 
					+ value);
		
		if(this.DAYLY_WITHDRAWAL_LIMIT < 
				this.prelievoGiornaliero + value)
			throw new DailyWithdrawnLimitException("Impossibile superare il limite prelievo giornaliero: " 
					+ this.DAYLY_WITHDRAWAL_LIMIT + ", già prelevati: " 
					+ this.prelievoGiornaliero);
		
		if(this.disponibilita < value) 
			throw new AmountWithdrawnException("Disponiblità: " + this.disponibilita 
					+ " < richiesta prelievo: " + value);
		
		this.disponibilita -= value;
		this.prelievoGiornaliero += value;
		return this.disponibilita;
	}
	
	public int getDisponibilita() {
		return this.disponibilita;
	}
	
	public int getPrelievoGiornaliero() {
		return this.prelievoGiornaliero;
	}
	
	public void resetPrelievoGiornaliero() {
		this.prelievoGiornaliero = 0;
	}
	
}