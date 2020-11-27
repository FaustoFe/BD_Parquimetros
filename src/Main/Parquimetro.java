package Main;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class Parquimetro {

	/*
	 * Retorna un ArrayList con las calles y alturas (ubicaciones) de todos los parquimetros.
	 */
	public static ArrayList<String> getUbicaciones(){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try {
			Statement stmt = Login.getConexion().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT calle, altura FROM parquimetros");
			
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
	 * Retorna un ArrayList con las IDs de los parquimetros que estan en la calle y altura pasada por parametro.
	 */
	public static ArrayList<String> getParquimetros(String calle, String altura){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try {
			Statement stmt = Login.getConexion().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id_parq FROM parquimetros WHERE calle = '" + calle + "' AND altura = " + altura);
			
			while(rs.next()) {
				resultado.add(rs.getString("id_parq"));
			}
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		return resultado;
	}
	
	/*
	 * Retorna un ArrayList con las IDs de las targetas disponibles.
	 */
	public static ArrayList<String> getTarjetas(){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try {
			Statement stmt = Login.getConexion().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id_tarjeta FROM tarjetas");
			
			while(rs.next()) {
				resultado.add(rs.getString("id_tarjeta"));
			}
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		return resultado;
	}
	
	/*
	 * Simula la conexión al parquimetro, ya sea para apertura o cierre.
	 */
	public static ArrayList<String> conectarParquimetro(String id_tarjeta, String id_parq) {
		 
		 ArrayList<String> resultado = new ArrayList<String>();
			
			try {
				Statement stmt = Login.getConexion().createStatement();
				ResultSet rs = stmt.executeQuery("CALL conectar(" + id_tarjeta + ", " + id_parq + ")");
				
				if(rs.next()) {
					
					ResultSetMetaData rsmd = rs.getMetaData();
					int cantColumnas = rsmd.getColumnCount();					
					if(cantColumnas > 1) {
						String operacion = rs.getString(1);
						resultado.add("Operacion: " + operacion);
						if(operacion.equals("Apertura")) {
							resultado.add("Resultado: " + rs.getString(2));
							resultado.add("Tiempo disponible: " + rs.getString(3));
						}
						else {
							resultado.add("Tiempo transcurrido: " + rs.getString(2));
							resultado.add("Saldo actualizado: " + rs.getString(3));
						}
					}
					else {
						resultado.add(rs.getString(1));
					}
				}
			
			} catch (java.sql.SQLException ex) {
				System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
				System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
				System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
			}
			
			return resultado;
	}
	
}
