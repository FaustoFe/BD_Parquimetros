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
	
	public Inspector(Connection cnx) {
		this.cnx = cnx;
	}

	
	/*
	 * Retorna un ArrayList con las calles y alturas (ubicaciones) de todos los parquimetros.
	 */
	public ArrayList<String> getUbicaciones(){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try {
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT calle, altura FROM parquimetros");
			
			while(rs.next()) {
				resultado.add(rs.getString("calle") + " " + rs.getString("altura"));
			}
		
		
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		
		return null;
	}
	
	public void conectarParquimetro(String calle, String altura) {
		
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

	    System.out.println("Current Date: " + ft.format(dNow));
		
		// Registrar acceso del inspector al parquimetro (ANTES DE CONTROLAR O DESPUES?)
		/*
		 * Se debera controlar que el inspector tenga asociada la ubicacion correspondiente al parquimetro 
		 * seleccionado, para el dia y hora en que se realiza la conexion. De no ser asi, no
		 * se generaron las multas y se debera mostrar un mensaje indicando que el inspector no esta
		 * autorizado para labrar multas en esa ubicacion. Considere que el turno mañana es de 8 a
		 * 13:59 hs. y el turno tarde es de 14 a 20 hs.
		 */
		
		// Obtener fecha y hora actual, para comparar con la asociada del inspector.
		
		try {
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT calle, altura FROM parquimetros");
			
		
		
		} catch (java.sql.SQLException ex) {
			System.out.println("Mensaje: " + ex.getMessage()); // Mensaje retornado por MySQL
			System.out.println("Código: " + ex.getErrorCode()); // Código de error de MySQL 
			System.out.println("SQLState: " + ex.getSQLState()); // Código de error del SQL standart
		}
		// Generar multas correspondientes
		// Retornar un listado de las multas labradas con la siguiente informacion: numero de 
		// multa, fecha, hora, calle, altura, patente del auto y legajo del inspector.
	}
	
	/*
	public void addPatente() {
		
	}
	*/
}
