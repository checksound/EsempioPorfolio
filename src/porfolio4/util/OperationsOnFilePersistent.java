package porfolio4.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import porfolio4.core.OperationType;
import porfolio4.core.Operazione;
import porfolio4.exception.ParsingFormatException;

public class OperationsOnFilePersistent {
	
	private File dataFile;
	
	public OperationsOnFilePersistent(File dataFile) {
		this.dataFile = dataFile;
	}
	
	public List<Operazione> readFromFile() 
			throws IOException, ParsingFormatException {
		
		List<Operazione> listOperations = new ArrayList<Operazione>();
		
		try( Scanner scanner = new Scanner(dataFile) ) {
            while (scanner.hasNextLine()) {
                String porfolioEntry = scanner.nextLine();
                Operazione op = parsePorfolioEntry(porfolioEntry);
				// add operazione
				listOperations.add(op);
				
            }
		}
		return listOperations;
	}
	
	public boolean writeToFile(List<Operazione> listOperazioni) 
			throws IOException {
		
		System.out.println("Saving phone directory changes to file " + 
                dataFile.getAbsolutePath() + " ...");
        PrintWriter out;
        try {
            out = new PrintWriter( new FileWriter(dataFile) );
        }
        catch (IOException e) {
            System.out.println("ERROR: Can't open data file for output.");
            return false;
        }

        for(Operazione op: listOperazioni) {
			String lineEntry = formatPorfolioEntry(op);
			out.println(lineEntry);
		}
        
        out.flush();
        out.close();
        
        if (out.checkError()) {
            System.out.println("ERROR: Some error occurred while writing data file.");
            return false;
        }
        
        return true;
		
		
	}
	
	private String formatPorfolioEntry(Operazione operazione) {
	    OperationType operationType = operazione.operationType;
	    int quantita = operazione.quantita;
	    long timestamp = operazione.timestamp;
	    
	    Date date = new Date();
		date.setTime(timestamp);
		
		SimpleDateFormat formatter = new SimpleDateFormat(
                "dd.MM.yy-hh:mm:ss", 
                Locale.ITALIAN);
		
		String stringDate = formatter.format(date);
		
		return operationType + "|" + quantita + "|" + stringDate;
	}
	
	private Operazione parsePorfolioEntry(String porfolioEntry) 
			throws ParsingFormatException {
		
		StringTokenizer st = new StringTokenizer(porfolioEntry, "|");
		
		int counter = 0;
		String operationString = null;
		String valueString = null;
		String dateString = null;
		
	    while (st.hasMoreTokens()) {
	       String token = st.nextToken();
	       
	       if(counter == 0)
	    	   operationString = token;
	       else if (counter == 1)
	    	   valueString = token;
	       else if (counter == 2)
	    	   dateString = token;
	       
	       counter++;
	    }
	    
	    if(counter != 3) {
	    	throw new ParsingFormatException();
	    }
	    
	    //parsing operation type 
	    OperationType operationType = null;
	    
	    if(operationString.equals("VERSAMENTO")) {
	    	operationType = OperationType.VERSAMENTO;
	    } else if(operationString.equals("PRELIEVO")) {
	    	operationType = OperationType.PRELIEVO;
	    } else {
	    	throw new ParsingFormatException();
	    }
	    
	    // parsing quantita
	    
	    int quantita;
	    
	    try {
	      quantita = Integer.parseInt(valueString);
	    } catch (NumberFormatException e) {
	    	throw new ParsingFormatException();
	    }
	    
	    // parse date
	    SimpleDateFormat formatter = new SimpleDateFormat(
                "dd.MM.yy-hh:mm:ss", 
                Locale.ITALIAN);
		
	    long timestamp = -1; 
	    
		try {
			Date date = formatter.parse(dateString);
			timestamp = date.getTime();
		} catch (ParseException e) {
			throw new ParsingFormatException();
		}
		
		return new Operazione(operationType, quantita, timestamp);
	}

}
