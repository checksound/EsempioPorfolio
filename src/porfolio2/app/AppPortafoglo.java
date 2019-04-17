package porfolio2.app;

import static java.lang.System.out;

import java.util.InputMismatchException;
import java.util.Scanner;

import porfolio2.core.Portafoglio;
import porfolio2.exception.DailyWithdrawnLimitException;
import porfolio2.exception.PorfolioException;
import porfolio2.exception.SingleWithdrawnLimitException;

public class AppPortafoglo {
	
	private static void statusPortafoglio(Portafoglio portafoglio) {
		
		out.println("** STATUS PORTAFOGLIO **");
		
		out.println("* Disponibilità: " + portafoglio.getDisponibilita());
		out.println("* Prelievo giornaliero: " + portafoglio.getPrelievoGiornaliero());
		out.println("* Lista prelievi: " + portafoglio.getListPrelievi());
		
		out.println("********************************");
	}

	private static void menu() {
		System.out.println("Esegui operazioni portafoglio:");
		System.out.println("Digita 1 per versare;");
		System.out.println("Digita 2 per prelevare;");
		System.out.println("Digita 3 per stato conto;");
		System.out.println("Digita 4 per uscire;");
		System.out.println("Operazione?");
	}
	
	private static int menuVersamento(Scanner scanner) {
		System.out.println("Quanto vuoi versare?");
		
		int quantita = 0;
		
		boolean isError = false;
		
		try {
			quantita = scanner.nextInt();
		} catch (InputMismatchException e) {
			isError = true;			
		}
		
		if(isError) {
			System.out.println("Errore: Devi digitare un numero come quantità da versare");
			return menuVersamento(scanner);
		}
		
		if(quantita < 0) {
			System.out.println("La quantità deve essere positiva");
			return menuVersamento(scanner);
		}
		
		return quantita;
	}
	
	private static int menuPrelievo(Scanner scanner) {
		System.out.println("Quanto vuoi prelevare?");
		int quantita = 0;
		
		boolean isError = false;
		
		try {
			quantita = scanner.nextInt();
		} catch (InputMismatchException e) {
			isError = true;			
		}
		
		if(isError) {
			System.out.println("Errore: Devi digitare un numero come quantità da prelevare");
			return menuPrelievo(scanner);
		}
		
		if(quantita < 0) {
			System.out.println("La quantità deve essere positiva");
			return menuPrelievo(scanner);
		}
		
		return quantita;
	}
	
	public static void main(String[] args) {
		
		Portafoglio portafoglio = new Portafoglio();
		
		Scanner scanner = new Scanner(System.in);
		
		menu();
		
		while(true) {
			
			int opzione = 0;
			boolean isError = false;
			
			try {
				opzione = scanner.nextInt();
			} catch (InputMismatchException e) {
				isError = true;
			}
			
			if(isError) {
				System.out.println("Errore: Devi digitare un numero per scegliere l'opzione");
				menu();
				continue;
			}
			
			if (opzione != 1 && opzione != 2 && opzione != 3 && opzione != 4) {
				System.out.println("Opzione non valida");
				menu();
				continue;
			}
			
			if(opzione == 3) {
				// status conto
				statusPortafoglio(portafoglio);
				menu();
				continue;
			}
			
			if(opzione == 4) {
				// exit
				System.out.println("Applicazione terminata");
				break;
			}
			
			int quantita;
			
			switch(opzione) {
				case 1:
					quantita = menuVersamento(scanner);
					portafoglio.versa(quantita);
					break;
				case 2:
					quantita = menuPrelievo(scanner);
					
					try {
						portafoglio.preleva(quantita);
					} catch (SingleWithdrawnLimitException e) {
						System.out.println(e.getMessage());
					} catch (DailyWithdrawnLimitException e) {
						System.out.println(e.getMessage());
					} catch (PorfolioException e) {
						System.out.println(e.getMessage());
					}
					
					break;
			}
			
			menu();
			
		}
			

	}

}
