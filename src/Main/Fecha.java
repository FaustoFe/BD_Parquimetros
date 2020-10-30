package Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Fecha {
	
	private String dia;
	private String turno;
	private java.sql.Date dateSQL;
	private java.sql.Time timeSQL;
	
	
	/*
	 * Crea una Fecha con el tiempo actual en formato:
	 * dia = String {"Do","Lu","Ma","Mi","Ju","Vi","Sa"}
	 * turno = String {"M","T",null} (null en el caso de no corresponder a un horario entre las 8hs y las 20hs)
	 * dateSQL = java.sql.Date con el formato "yyyy-MM-dd"
	 * timeSQL = java.sql.Time con el formato "HH-mm-ss"
	 */
	public Fecha() {
		
		Date dNow = new Date();
		
		// Set de las iniciales del dia
	    switch(new SimpleDateFormat("EEEEEE", Locale.US).format(dNow)) {
	    	case("Monday"):{
	    		this.dia = "Lu";
	    		break;
	    	}
	    	case("Tuesday"):{
	    		this.dia = "Ma";
	    		break;
	    	}
	    	case("Wednesday"):{
	    		this.dia = "Mi";
	    		break;
	    	}
	    	case("Thursday"):{
	    		this.dia = "Ju";
	    		break;
	    	}
	    	case("Friday"):{
	    		this.dia = "Vi";
	    		break;
	    	}
	    	case("Saturday"):{
	    		this.dia = "Sa";
	    		break;
	    	}
	    	case("Sunday"):{
	    		this.dia = "Do";
	    		break;
	    	}
	    }
		
	    if(dia == null) {
	    	System.out.println("Error en el día producto de Local.<Pais>");
	    }
	    
	    // Set del turno
	    int hora = Integer.parseInt(new SimpleDateFormat("H").format(dNow));
	    
	    if(hora >= 8 && hora < 14) {
	    	this.turno = "M";
	    }
	    else {
	    	if(hora >= 14 && hora <= 20) {
	    		this.turno = "T";
	    	}
	    	else {
	    		this.turno = null;
	    	}
	    }
	    
	    // Set del dateSQL
	    dateSQL = java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(dNow));
	    
	    // Set del dateSQL
	    timeSQL = java.sql.Time.valueOf((new SimpleDateFormat("HH:mm:ss")).format(dNow));
	    
	}


	public String getDia() {
		return dia;
	}


	public String getTurno() {
		return turno;
	}


	public java.sql.Date getDateSQL() {
		return dateSQL;
	}


	public java.sql.Time getTimeSQL() {
		return timeSQL;
	}
	
	
}
