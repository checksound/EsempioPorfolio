package porfolio1.core;

import static java.lang.System.out;

import porfolio1.exception.DailyWithdrawnLimitException;
import porfolio1.exception.AmountWithdrawnException;
import porfolio1.exception.SingleWithdrawnLimitException;

public class TestPortafoglio {
	
	public static int preleva(Portafoglio portafoglio, int quantita) {
		try {
			return portafoglio.preleva(quantita);
		} catch (AmountWithdrawnException e) {
			System.out.println("PortafoglioException: " + e.getMessage());
		} catch (SingleWithdrawnLimitException e) {
			System.out.println("PorfolioLimitException: " + e.getMessage());
		} catch (DailyWithdrawnLimitException e) {
			System.out.println("DailyLimitException: " + e.getMessage());
		}
		
		return -1;
	}
    	
	public static void main(String[] args) throws TestException {
		
		Portafoglio portafoglio = new Portafoglio();
		
		out.println("VERSO 400");
		portafoglio.versa(400);
		
		out.println("Prelevo 600");
		preleva(portafoglio, 600); // portafoglio limit exception
		
		out.println("Prelevo 500");
		preleva(portafoglio, 500); // portafoglio exception
		
		out.println("Verso 200");
		portafoglio.versa(200); 
		
		out.println("Prelevo 500");
		int residuo = preleva(portafoglio, 500);
		
		if(residuo == 100)
			out.println("Residuo 100 OK");
		else {
			throw new TestException("Errore prelievo");
		}
		
		out.println("Verso 2000");
		portafoglio.versa(2000); 
		
		out.println("Disponibilità: " + portafoglio.getDisponibilita());
		
		preleva(portafoglio, 500);
		
		preleva(portafoglio, 500);
		
		out.println("Prelievo giornaliero: " + portafoglio.getPrelievoGiornaliero());
		
		out.println("Disponibilità: " + portafoglio.getDisponibilita());
		
		out.println("Prelevo: " + 100);
		preleva(portafoglio, 100);
		
        out.println("Prelievo giornaliero: " + portafoglio.getPrelievoGiornaliero());
		
		out.println("Disponibilità: " + portafoglio.getDisponibilita());
		
		portafoglio.resetPrelievoGiornaliero();
		
        preleva(portafoglio, 100);
		
        out.println("Prelievo giornaliero: " + portafoglio.getPrelievoGiornaliero());
		
		out.println("Disponibilità: " + portafoglio.getDisponibilita());
	}

}
