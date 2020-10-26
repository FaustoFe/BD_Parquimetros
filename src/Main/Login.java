package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Login {

	private static Connection cnx = null;

	/*
	 * Retorna una conexion si la clave es correcta (solo se utiliza para admin).
	 */
	public static void conectarBD(String clave) {
		establecerConexion("admin", clave);
	}
	
	/*
	 * Retorna una conexion si los datos corresponden a un inspector.
	 */
	public static String conectarBD(String usuario, String clave) {
		establecerConexion("inspector", "inspector");
		String nombreApellido = null;
		try {
			
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nombre, apellido FROM inspectores WHERE legajo = " + usuario + " AND password = md5('" + clave + "')");
			if(!rs.next()) { // No hay inspector con esos datos
				
				cnx = null;
			}
			else {
				nombreApellido = rs.getString("nombre") + " " + rs.getString("apellido");
			}
			
			rs.close();
			stmt.close();
			
		} catch (java.sql.SQLException ex) {
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
			cnx = null;
		}
		
		return nombreApellido;
	}
	
	
	/*
	 * Retorna una conexion si los datos son correctos.
	 */
	private static Connection establecerConexion(String usuario, String clave) {
		
		// Conexi�n con el servidor			
		java.sql.DriverManager.setLoginTimeout(10);
		
		String servidor = "localhost:3306";
		String baseDatos = "parquimetros";
		String url = "jdbc:mysql://" + servidor + "/" +baseDatos+
		"?serverTimezone=America/Argentina/Buenos_Aires";
		
		try {
			cnx = java.sql.DriverManager.getConnection(url, usuario, clave);
		} catch (java.sql.SQLException ex) {
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return cnx;
		
	}
	
	public static void desconectarBD() {
	      if (cnx != null) {
	         try {
	            cnx.close();
	            cnx = null;
	         }
	         catch (SQLException ex) {
//	            System.out.println("SQLException: " + ex.getMessage());
//	            System.out.println("SQLState: " + ex.getSQLState());
//	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	}
	
	public static Connection getConexion() {
		return cnx;
	}
	
}
