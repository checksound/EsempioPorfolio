package porfolio2.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Prelievo {
	
	public final int quantita;
	public final long timestamp;
	
	public Prelievo(int quantita, long timestamp) {
		this.quantita = quantita;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		Date date = new Date();
		date.setTime(timestamp);
		
		SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy.MM.dd-hh:mm:ss", 
                Locale.ITALIAN);
		
		return "P [qt=" + quantita + ", dt='" + formatter.format(date) + "']";
	}
	
	

}
