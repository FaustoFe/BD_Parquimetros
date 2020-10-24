package Main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Inspector {

	private Connection cnx;
	private int legajo;
	private ArrayList<String> patentesRegistradas;
	
	public Inspector(Connection cnx, int legajo) {
		this.cnx = cnx;
		this.legajo = legajo;
		patentesRegistradas = new ArrayList<String>();
	}

	
	/*
	 * Retorna un ArrayList con las calles y alturas (ubicaciones) de todos los parquimetros.
	 */
	public ArrayList<String> getUbicaciones(){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try {
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT calle, altura, numero FROM parquimetros");
			
			while(rs.next()) {
				resultado.add(rs.getString("calle") + " " + rs.getString("altura"));
			}
		
		
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		return resultado;
	}
	
	/*
	 * Retorna un ArrayList con todos los parquimetros de una ubicacion (calle, altura).
	 */
	public ArrayList<String> getParquimetros(){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try {
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT numero FROM parquimetros");
			
			while(rs.next()) {
				resultado.add(rs.getString("calle") + " " + rs.getString("altura"));
			}
		
		
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		return resultado;
	}
	
	
	/*
	 * Retorna true si el inspector esta asociado a la ubicacion para la fecha y turno dado.
	 */
	private boolean estaAsociado(Statement stmt, Fecha fecha, String calle, String altura) {
		
		boolean resultado = false;
		
		String dia = fecha.getDia();
		String turno = fecha.getTurno();
		
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery("SELECT id_asociado_con " + 
									"FROM asociado_con " + 
									"WHERE dia = " + dia + "AND turno = " + turno + 
									"AND calle = " + calle + "AND altura = " + altura + 
									"AND legajo = " + legajo);
		
			if (rs.isBeforeFirst()) {
				resultado = true;
			}
			
			rs.close();
		
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		return resultado;
	}
	
	/*
	 * Simula la coneccion del inspector al parquimetro, chequeando si esta en condiciones de labrar multas en la ubicacion
	 * pasada por parametro para el dia y turno actual.
	 * En el caso que pueda, se realizaran multas a los vehiculos estacionados cuyas patentes no esten en la base de datos,
	 * y se registra el acceso del inspector al parquimetro.
	 * Caso contrario muestra un mensaje notificando que el inspector no esta autorizado a labrar multas. 
	 */
	public void conectarParquimetro(String calle, String altura) {
		
		Fecha fecha = new Fecha();
		
		// Controlar que el inspector tenga asociada la ubicacion correspondiente al parquimetro seleccionado, 
		// para el dia y hora en que se realiza la conexion. 
		try {
			Statement stmt = cnx.createStatement();
			
			if (estaAsociado(stmt, fecha, calle, altura)) { // Hay un inspector asociado a la ubicacion para el dia y turno actual.
				
				ResultSet rs = stmt.executeQuery("SELECT patente FROM estacionados WHERE calle = " + calle + "AND altura = " + altura);
				
				while(rs.next()){
					patentesRegistradas.contains(rs.getString("patente"));
				}
				
				// Generar multas correspondientes
				// Retornar un listado de las multas labradas con la siguiente informacion: numero de 
				// multa, fecha, hora, calle, altura, patente del auto y legajo del inspector.
				
				// Registrar acceso del inspector al parquimetro
			} 
			else {
				// De no ser asi, no se generaron las multas y se debera mostrar un mensaje indicando que el inspector no esta
				// autorizado para labrar multas en esa ubicacion.
			}
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
	}
	
	
	public void addPatente(String patente) {
		patentesRegistradas.add(patente);
	}
	
	public boolean removePatente(String patente) {
		return patentesRegistradas.remove(patente);
	}
	
	public void limpiarListaPatentes(String patente) {
		patentesRegistradas = new ArrayList<String>();
	}
	
}
