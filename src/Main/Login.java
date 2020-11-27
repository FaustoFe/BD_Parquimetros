package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Login {

	private static Connection cnx = null;

	/*
	 * Establece una conección de tipo de usuario admin.
	 */
	public static void conectarParquimetroBD() {
		establecerConexion("parquimetro", "parq");
	}
	
	/*
	 * Establece una conección de tipo de usuario admin.
	 */
	public static void conectarBD(String clave) {
		establecerConexion("admin", clave);
	}
	
	/*
	 * Establece una conección de tipo de usuario inspector retornando el nombre y apellido del inspector que ingresa.
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
	 * Retorna una conexion si los datos pasados por parámetro son correctos.
	 */
	private static Connection establecerConexion(String usuario, String clave) {
		
		// Conexión con el servidor			
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
	
	/*
	 * Cierra la conección con la base de datos.
	 */
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
	
	/*
	 * Retorna la conección establecida con la base de datros, de no haberla retorna null.
	 */
	public static Connection getConexion() {
		return cnx;
	}
	
}
