package Main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;

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
	public ArrayList<String> getParquimetros(){
		//Dictionary<String, Pair<String, String>> resultado = new Dictionary<String, Pair<String, String>>();
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
	 * Retorna id_asociado_con del inspector que esta asociado a la ubicacion para la fecha y turno dado en caso de que exista.
	 * Caso contrario retorna null.
	 */
	private String getAsociado(Statement stmt, Fecha fecha, String calle, String altura) {
		
		String resultado = null;
		
		String dia = fecha.getDia();
		String turno = fecha.getTurno();
		
		ResultSet rs = null;
		
		try {
//			String sql = "SELECT id_asociado_con " + 
//					"FROM asociado_con " + 
//					"WHERE dia = " + dia + " AND turno = " + turno + 
//					" AND calle = " + calle + " AND altura = " + altura + 
//					" AND legajo = " + legajo;
			String sql = "SELECT id_asociado_con " + 
					"FROM asociado_con " + 
					"WHERE dia = '" + dia + "' AND turno = '" + turno + 
					"' AND calle = '" + calle + "' AND altura = " + altura + 
					" AND legajo = " + legajo;
			rs = stmt.executeQuery(sql);
		
			if (rs.next()) {
				resultado = rs.getString("id_asociado_con");
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
	 * Caso contrario retorna null.
	 */
	public ArrayList<ArrayList<String>> conectarParquimetro(String calle, String altura) {
		
		ArrayList<ArrayList<String>> patentesMultadas = null;
		Fecha fecha = new Fecha();
		
		// Controlar que el inspector tenga asociada la ubicacion correspondiente al parquimetro seleccionado, 
		// para el dia y hora en que se realiza la conexion. 
		try {
			Statement stmt = cnx.createStatement();
			
			//String id_asociado = getAsociado(stmt, fecha, calle, altura);
			String id_asociado = "id_PRUEBA";
			
			if (id_asociado != null) { // Hay un inspector asociado a la ubicacion para el dia y turno actual.
				
				//ResultSet rs = stmt.executeQuery("SELECT patente FROM estacionados WHERE calle = " + calle + " AND altura = " + altura);
				ResultSet rs = stmt.executeQuery("SELECT patente FROM estacionados WHERE calle = '" + calle + "' AND altura = " + altura);
				
				String patente = null;
				while(rs.next()){
					patente = rs.getString("patente");
					if(patentesRegistradas.contains(patente)) {
						patentesRegistradas.remove(patente);
					}
				}
				rs.close();
				
				// Genera la lista de multas que se va a retornar, y se añade al batch (para insertarlas posteriormente a la base de datos)
				patentesMultadas = new ArrayList<ArrayList<String>>();
				
				if(!patentesRegistradas.isEmpty()) {
					String sql;
					
					// Obtener siguiente id (numero de la multa)
					ResultSet rs_id = stmt.executeQuery("SELECT numero FROM multa ORDER BY numero ASC LIMIT 1");
					rs_id.next();
					int numeroMulta = Integer.parseInt(rs_id.getString("numero")) + 1;
					rs_id.close();
					
					for(String p : patentesRegistradas) {
						ArrayList<String> pMultada = new ArrayList<String>();
						
						//CONTINUAR
						sql = "INSERT INTO multa(numero, fecha, hora, patente, id_asociado_con) VALUES (" + numeroMulta + ", " + fecha.getDateSQL() + ", " + fecha.getTimeSQL() + ", " + p + ", " + id_asociado + ");";
//						sql = "INSERT INTO multa(fecha, hora, patente, id_asociado_con) VALUES ('"+ fecha.getDateSQL() + "', '" + fecha.getTimeSQL() + "', " + p + ", " + id_asociado + ");";
						
						pMultada.add(String.valueOf(numeroMulta));
						pMultada.add(String.valueOf(fecha.getDateSQL()));
						pMultada.add(String.valueOf(fecha.getTimeSQL()));
						pMultada.add(calle);
						pMultada.add(altura);
						pMultada.add(p);
						pMultada.add(String.valueOf(legajo));
						
						patentesMultadas.add(pMultada);
						
						
						
						stmt.addBatch(sql);
						//stmt.addBatch("INSERT INTO multa(numero, fecha, hora, patente, id_asociado_con) VALUES (" + numeroMulta + ", '" + 
						//				fecha.getDateSQL() + "', '" + fecha.getTimeSQL() + "', " + p + ", " + id_asociado + ");");
						
						++numeroMulta;
					}
				}
				
				// Registrar acceso del inspector al parquimetro
				stmt.addBatch("INSERT INTO accede(legajo, id_parq, fecha, hora) VALUES (" + legajo + ", " + null + ", " + fecha.getDateSQL() + ", " + fecha.getTimeSQL() + ");");
				
				int[] b = stmt.executeBatch();
			} 
			else {
				// De no ser asi, no se generaron las multas y se debera mostrar un mensaje indicando que el inspector no esta
				// autorizado para labrar multas en esa ubicacion.
			}
		
			stmt.close();
			
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		patentesRegistradas = new ArrayList<String>();
		
		return patentesMultadas;
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
