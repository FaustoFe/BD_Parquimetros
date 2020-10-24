package Main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {
	
	private String dia;
	private String turno;
	private String dateSQL;
	private String timeSQL;
	
	public Fecha() {
		
		Date dNow = new Date();
		
		// Set del las iniciales del dia
	    switch(new SimpleDateFormat("E").format(dNow)) {
	    	case("Monday"):{
	    		this.dia = "Lu";
	    	}
	    	case("Tuesday"):{
	    		this.dia = "Ma";
	    	}
	    	case("Wednesday"):{
	    		this.dia = "Mi";
	    	}
	    	case("Thursday"):{
	    		this.dia = "Ju";
	    	}
	    	case("Friday"):{
	    		this.dia = "Vi";
	    	}
	    	case("Saturday"):{
	    		this.dia = "Sa";
	    	}
	    	case("Sunday"):{
	    		this.dia = "Do";
	    	}
	    	default: {}
	    }
		
	    
	    // Set del turno
	    Date turno_maniana_inicio = new Date();
	    
	}
	
	
}
