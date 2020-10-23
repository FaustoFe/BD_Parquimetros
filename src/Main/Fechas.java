package Main;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Fechas {
	
	public static String getDia() {
		
		String diaEspaniol = null;
		
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("E");
	    String diaIngles = ft.format(dNow);
	    
	    switch(diaIngles) {
	    	case("Monday"):{
	    		diaEspaniol = "Lu";
	    	}
	    	case("Tuesday"):{
	    		diaEspaniol = "Ma";
	    	}
	    	case("Wednesday"):{
	    		diaEspaniol = "Mi";
	    	}
	    	case("Thursday"):{
	    		diaEspaniol = "Ju";
	    	}
	    	case("Friday"):{
	    		diaEspaniol = "Vi";
	    	}
	    	case("Saturday"):{
	    		diaEspaniol = "Sa";
	    	}
	    	case("Sunday"):{
	    		diaEspaniol = "Do";
	    	}
	    	default: {}
	    }
		
		return diaEspaniol;
	}
	
	public static String getTurno() {
		return null;
	}
	
}
