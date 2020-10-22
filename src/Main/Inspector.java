package Main;
import java.sql.Connection;

public class Inspector {

	private Connection cnx;
	private GUI_Inspector gui;
	
	public Inspector(GUI_Inspector gui, Connection cnx) {
		this.cnx = cnx;
		this.gui = gui;
	}
	
	public void addPatente() {
		
	}
}
