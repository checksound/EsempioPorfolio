package porfolio2.test;

import static java.lang.System.out;

import porfolio2.core.Portafoglio;
import porfolio2.exception.DailyWithdrawnLimitException;
import porfolio2.exception.PorfolioException;
import porfolio2.exception.SingleWithdrawnLimitException;

public class TestPortafoglio {
	
	private static void wait(int numSeconds) {
		
		System.out.println("Aspetto " + numSeconds + " sec");
		
		/**
		 * Thread per stampare carattere '.' ogni 200 ms.
		 * 
		 * Utilizzato dalla funzione wait per far vedere scorrere tempo.
		 * @author cam
		 */
		class OutputThread extends Thread {
			
			private volatile boolean isRunning; 
			
			@Override
			public void run() {
				isRunning = true;
				int counter = 0;
				while(isRunning) {
					System.out.print(".");
					try {
						Thread.sleep(200);
						counter++;
						if(counter % 80 == 0)
							System.out.println("");
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("");
			}
			
			public void doStop() {
				isRunning = false;
			}
		}
		
		OutputThread tD = new OutputThread();
		try {
			tD.start();
			Thread.sleep(numSeconds * 1000);
			
			tD.doStop();
			tD.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("FINE WAIT!!!");
		
		
	}
	
	private static void statusPortafoglio(Portafoglio portafoglio) {
		
		out.println("** STATUS PORTAFOGLIO **");
		
		out.println("* Disponibilità: " + portafoglio.getDisponibilita());
		out.println("* Prelievo giornaliero: " + portafoglio.getPrelievoGiornaliero());
		out.println("* Lista prelievi: " + portafoglio.getListPrelievi());
		
		out.println("********************************");
	}
	
	private static int preleva(Portafoglio portafoglio, int quantita) {
		try {
			return portafoglio.preleva(quantita);
		} catch (SingleWithdrawnLimitException e) {
			System.out.println(e.getMessage());
		} catch (DailyWithdrawnLimitException e) {
			System.out.println(e.getMessage());
		} catch (PorfolioException e) {
			System.out.println(e.getMessage());
		} 
		
		return -1;
	}
    	
	public static void main(String[] args) throws TestException {
		
		Portafoglio portafoglio = new Portafoglio();
		
		out.println("Verso 400 euro");
		portafoglio.versa(400);
		statusPortafoglio(portafoglio);
		
		wait(6);
		
		out.println("Cerco di prelevare 600 euro");
		preleva(portafoglio, 600); // portafoglio limit exception
		statusPortafoglio(portafoglio);
		
		wait(5);
		
		out.println("Cerco di prelavare 500 euro");
		preleva(portafoglio, 500); // portafoglio exception
		statusPortafoglio(portafoglio);
		
		wait(8);
		
		out.println("Verso 200 euro");
		portafoglio.versa(200);
		statusPortafoglio(portafoglio);
		
		wait(7);
		
		out.println("Cerco di prelavere 500 euro");
		int residuo = preleva(portafoglio, 500);
		
		if(residuo == 100)
			out.println("Residuo 100 OK");
		else {
			throw new TestException("Errore prelievo");
		}
		
		wait(9);
		
		out.println("Verso 2000 euro");
		portafoglio.versa(2000); 
		
		statusPortafoglio(portafoglio);
		
		wait(8);
		
		preleva(portafoglio, 500);
		statusPortafoglio(portafoglio);
		
		wait(10);
		
		preleva(portafoglio, 500);
		
		statusPortafoglio(portafoglio);
		
		out.println("Cerco di prelevare: " + 100);
		
		preleva(portafoglio, 100);
		
		statusPortafoglio(portafoglio);
		
        wait(60);
        
		preleva(portafoglio, 100);
		
		System.out.println(portafoglio.getListPrelievi());
		
		wait(70);
		
		preleva(portafoglio, 100);
		
        statusPortafoglio(portafoglio);
		
		System.out.println(portafoglio.getListPrelievi());
	}

}
