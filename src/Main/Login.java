package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Login {

	private Connection cnx = null;

	public Login() {
		
	}
	
	public Connection conectarBD(String clave) {
		return conectarBD("admin", clave);
	}
	
	public Connection conectarBD(String usuario, String clave) {
	
		// Conexión con el servidor			
		java.sql.DriverManager.setLoginTimeout(10);
		
		String servidor = "localhost:3306";
		String baseDatos = "parquimetros";
		String url = "jdbc:mysql://" + servidor + "/" +baseDatos+
		"?serverTimezone=America/Argentina/Buenos_Aires";
		
		try {
			cnx = java.sql.DriverManager.getConnection(url, usuario, clave);
		} catch (java.sql.SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return cnx;
	}
	
	public void desconectarBD() {
	      if (this.cnx != null) {
	         try {
	            this.cnx.close();
	            this.cnx = null;
	         }
	         catch (SQLException ex) {
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	  }
	
}
