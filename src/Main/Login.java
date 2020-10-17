package Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class Login {

	private Connection cnx;

	public boolean conectarBD(String clave) {
		return conectarBD("admin", clave);
	}
	
	public boolean conectarBD(String usuario, String clave) {
	
		// Conexión con el servidor
		
		boolean resultado = true;
		
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
			resultado = false;
		}
		return resultado;
	}
	
	private void desconectarBD() {
	      if (this.cnx != null)
	      {
	         try
	         {
	            this.cnx.close();
	            this.cnx = null;
	         }
	         catch (SQLException ex)
	         {
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }
	      }
	  }
	
	public HashMap<String, String> consultaSQL(String consulta){
		try
		{
			// Se crea una sentencia jdbc para realizar la consulta
			java.sql.Statement stmt = cnx.createStatement();
			
			// Se prepara el string SQL de la inserción
			String sql = "INSERT INTO barcos (nombre_barco, id, capitan) " + "VALUES ('Bismark', 22, 'Ernst Lindeman')";
			
			// Se ejecuta la inserción
			stmt.execute(sql);
			// Se retornan los recursos utilizados cerrando la sentencia
			stmt.close();
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL System.out.println("SQLState: " +
			ex.getSQLState(); // Código de error del SQL standart
		}

		return null;
	}
	
}
