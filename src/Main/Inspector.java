package Main;
import java.sql.Connection;

public class Inspector {

	private Connection cnx;
	
	public Inspector(Connection cnx) {
		this.cnx = cnx;
	}
	
	
	/*
	 * Se debera controlar que el inspector tenga asociada la ubicacion correspondiente al parquimetro 
	 * seleccionado, para el dia y hora en que se realiza la conexion. De no ser asi, no
	 * se generaron las multas y se debera mostrar un mensaje indicando que el inspector no esta
	 * autorizado para labrar multas en esa ubicacion. Considere que el turno mañana es de 8 a
	 * 13:59 hs. y el turno tarde es de 14 a 20 hs.
	 */
	public void conectarParquimetro() {
		// Registrar acceso del inspector al parquimetro
		// Generar multas correspondientes
		// Retornar un listado de las multas labradas con la siguiente informacion: numero de 
		// multa, fecha, hora, calle, altura, patente del auto y legajo del inspector.
	}
	
	/*
	public void addPatente() {
		
	}
	*/
}
