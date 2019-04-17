package porfolio2.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import porfolio2.exception.AmountWithdrawnException;
import porfolio2.exception.DailyWithdrawnLimitException;
import porfolio2.exception.PorfolioException;
import porfolio2.exception.SingleWithdrawnLimitException;

public class Portafoglio {
	
	public static final long RANGE = 2 * 60 * 1000;
	
	private List<Prelievo> listPrelievi = new ArrayList<Prelievo>();
	
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
	
	private int getSumPrelievoInRange(long now, long range) {
		int prelievoInRange = 0;
		
		ListIterator<Prelievo> lIterator = 
				listPrelievi.listIterator(listPrelievi.size());
		while(lIterator.hasPrevious()) {
			Prelievo elem = lIterator.previous();
			if(elem.timestamp < now - range) {
				break;
			}
			
			prelievoInRange += elem.quantita;
		}
		return prelievoInRange;
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
	 * @param amount
	 * @return la disponibilità ad operazione avvenuta
	 * @throws PorfolioException se la disponibilità è < della richiesta di 
	 * prelievo.
	 * 
	 */
	public int preleva(int amount) throws PorfolioException {
		
		if(this.SINGLE_WITHDRAWAL_LIMIT< amount)
			throw new SingleWithdrawnLimitException(amount, this.SINGLE_WITHDRAWAL_LIMIT);
		
		if(this.DAYLY_WITHDRAWAL_LIMIT < 
				getSumPrelievoInRange(System.currentTimeMillis(), RANGE) + amount)
			throw new DailyWithdrawnLimitException(amount, getPrelievoGiornaliero());
		
		if(this.disponibilita < amount) 
			throw new AmountWithdrawnException(amount, this.disponibilita);
		
		this.disponibilita -= amount;
		this.listPrelievi.add(
				new Prelievo(amount, System.currentTimeMillis()));
		return this.disponibilita;
	}
	
	public int getDisponibilita() {
		return this.disponibilita;
	}
	
	public int getPrelievoGiornaliero() {
		return getSumPrelievoInRange(System.currentTimeMillis(), RANGE);
	}

	public List<Prelievo> getListPrelievi() {
		return Collections.unmodifiableList(listPrelievi);
	}

}