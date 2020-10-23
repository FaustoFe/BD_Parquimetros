package Main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin {

	private Connection cnx;
	
	public Admin(Connection cnx) {
		this.cnx = cnx;
	}
	
	/*
	 * Ejecuta una sentencia SQL pasada por parametro (DEVERÍA RETORNAR EL ResultSet CUANDO SE PUEDA)
	 */
	public ResultSet sentenciaSQL(String consulta){
		
		boolean isResultSet = false;
		Statement stmt = null;
		ResultSet rslt = null;
		
		try {
			// Se crea una sentencia jdbc para realizar la consulta
			stmt = cnx.createStatement();
			
			isResultSet = stmt.execute(consulta); 
			if (isResultSet) { //La sentencia tiene ResultSet
				rslt = stmt.getResultSet();
			}
			
			//stmt.close();
			
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
			/*
			 * Mostrar mensaje de error.
			 */
		}
		return rslt;
	}
	
	/*
	 * Retorna un ArrayList con los nombres de todas las tablas de la base de datos.
	 */
	public ArrayList<String> getTablas() {
		
		Statement stmt = null;
		ResultSet rslt = null;
		ArrayList<String> resultado = new ArrayList<String>();
	    
		try {
			stmt = cnx.createStatement();
		
			rslt = stmt.executeQuery("Show tables");
		    
			while(rslt.next()) {
				resultado.add(rslt.getString(1));
		    }
		    
			rslt.close();
		    stmt.close();
	
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		    
	    return resultado;
	}
	
	/*
	 * Retorna un ArrayList con los nombres de todos los atributos de la tabla pasada por parametro.
	 */
	public ArrayList<String> getAtributos(String tabla) {
		
		Statement stmt = null;
		ResultSet rslt = null;
		ArrayList<String> resultado = new ArrayList<String>();
	    
		try {
			stmt = cnx.createStatement();
			rslt = stmt.executeQuery("DESCRIBE " + tabla);
		    
			while(rslt.next()) {
				resultado.add(rslt.getString("Field"));
		    }
		    
		    rslt.close();
		    stmt.close();
	
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
	    
	    return resultado;
	}
	
	
}
