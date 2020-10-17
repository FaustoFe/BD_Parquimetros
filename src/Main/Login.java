package Main;

import java.sql.Connection;
import java.sql.SQLException;

public class Login {

	private Connection cnx;
	
	public static void main(String[] args) {
		
		
		
	}

	public Admin conectar(String clave) {
		conectar("admin", clave);
		return new Admin(cnx);
	}
	
	public Inspector conectar(String usuario, String clave) {
		
		/*
		 *	Conección con el servidor
		 */
		
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
		return new Inspector(cnx);
	}
	
	
}
