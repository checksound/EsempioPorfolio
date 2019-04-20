package porfolio4.app;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import porfolio4.core.Portafoglio;
import porfolio4.exception.PorfolioException;
import porfolio4.util.OperationsOnFilePersistent;

public class AppPortafoglio {
	
	private static String DATA_FILE_NAME = ".porfolio";
	
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
		
		/* Create a dataFile variable of type File to represent the
         * data file that is stored in the user's home directory.
         */

        File userHomeDirectory = new File( System.getProperty("user.home") );
        File dataFile = new File( userHomeDirectory, DATA_FILE_NAME );


        /* If the data file already exists, then the data in the file is
         * read and is used to initialize the phone directory.  The format
         * of the file must be as follows:  Each line of the file represents
         * one directory entry, with the name and the number for that entry
         * separated by the character '%'.  If a file exists but does not
         * have this format, then the program terminates; this is done to
         * avoid overwriting a file that is being used for another purpose.
         */

        if ( ! dataFile.exists() ) {
            System.out.println("No phone book data file found.  A new one");
            System.out.println("will be created, if you add any entries.");
            System.out.println("File name:  " + dataFile.getAbsolutePath());
        }
        else {
            System.out.println("Reading phone book data...");
        }
        
        OperationsOnFilePersistent pers = 
        		new OperationsOnFilePersistent(dataFile);
		
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
				out.println("SALVATAGGIO DATI");
				try {
					pers.writeToFile(portafoglio.getListOperazioni());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
