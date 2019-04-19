package porfolio3.app;

import static java.lang.System.out;

import java.util.InputMismatchException;
import java.util.Scanner;

import porfolio3.core.Portafoglio;
import porfolio3.exception.PorfolioException;

public class AppPortafoglio {
	
	private static void statusPortafoglio(Portafoglio portafoglio) {
		
		out.println("** STATUS PORTAFOGLIO **");
		
		out.println("* Disponibilità: " + portafoglio.getDisponibilita());
		out.println("* Prelievo giornaliero: " + portafoglio.getPrelievoGiornaliero());
		out.println("* Lista operazioni: " + portafoglio.getListOperazioni());
		
		out.println("********************************");
	}

	private static void menu() {
		out.println("Esegui operazioni portafoglio:");
		out.println("* Digita 1 per versare;");
		out.println("* Digita 2 per prelevare;");
		out.println("* Digita 3 per stato conto;");
		out.println("* Digita 4 per uscire;");
		out.println("Operazione?");
	}
	
	private static int menuVersamento(Scanner scanner) {
		out.println("Quanto vuoi versare?");
		
		int quantita = 0;
		
		boolean isError = false;
		
		try {
			quantita = scanner.nextInt();
		} catch (InputMismatchException e) {
			isError = true;			
		}
		
		if(isError) {
			out.println("Errore: Devi digitare un numero come quantità da versare");
			return menuVersamento(scanner);
		}
		
		if(quantita < 0) {
			out.println("La quantità deve essere positiva");
			return menuVersamento(scanner);
		}
		
		return quantita;
	}
	
	private static int menuPrelievo(Scanner scanner) {
		out.println("Quanto vuoi prelevare?");
		int quantita = 0;
		
		boolean isError = false;
		
		try {
			quantita = scanner.nextInt();
		} catch (InputMismatchException e) {
			isError = true;			
		}
		
		if(isError) {
			out.println("Errore: Devi digitare un numero come quantità da prelevare");
			return menuPrelievo(scanner);
		}
		
		if(quantita < 0) {
			out.println("La quantità deve essere positiva");
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
				out.println("Errore: Devi digitare un numero per scegliere l'opzione");
				menu();
				continue;
			}
			
			if (opzione != 1 && opzione != 2 && opzione != 3 && opzione != 4) {
				out.println("Opzione non valida");
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
				out.println("Applicazione terminata");
				break;
			}
			
			int quantita;
			
			switch(opzione) {
				case 1:
					quantita = menuVersamento(scanner);
					portafoglio.versa(quantita);
					
					out.println("OK versamento: " + quantita);
					break;
				case 2:
					quantita = menuPrelievo(scanner);
					
					try {
						portafoglio.preleva(quantita);
						
						out.println("OK prelievo: " + quantita);
					} catch (PorfolioException e) {
						out.println("ERRORE su prelievo: " + e.getMessage());
					} 
					
					break;
			}
			
			menu();
			
		}
			

	}

}
