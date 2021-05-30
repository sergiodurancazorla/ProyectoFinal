package application.modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

import application.VariablesEstaticas;
import orm.pojos.Incidencia;

/**
 * Clase que genera y controla los logs de la aplicacion. Se diferencian dos
 * tipos de logs: general y de incidencias.
 * 
 * @author Sergio Duran
 *
 */
public class LogIncidencia {

	private BufferedWriter bufferedWriter;
	private String ruta = "./logs";

	/**
	 * Metodo que controla los logs generales. Añade en un fichero fecha, usuario y
	 * el texto agregado
	 * 
	 * @param linea texto que se quiere añadir al log general
	 */
	public void logGeneral(String linea) {
		try {
			this.bufferedWriter = new BufferedWriter(new FileWriter(ruta + "/logsGenerales.txt", true));
			this.bufferedWriter.write("\n[" + Date.from(Instant.now()) + "] " + "usuario: "
					+ VariablesEstaticas.profesor.toString() + "\n\t" + linea);
			this.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que controla los logs de una incidencia. Crea o modifica un fichero de
	 * txt con el nombre del id de la incidencia. En este log se guarda los valores
	 * cada vez que se modifica o añade una incidencia.
	 * 
	 * @param i incidencia
	 */
	public void logIncidencia(Incidencia i) {

		try {
			this.bufferedWriter = new BufferedWriter(
					new FileWriter(ruta + "/incidencias/" + i.getIdincidencia() + ".txt", true));
			this.bufferedWriter.write("\n[" + Date.from(Instant.now()) + "] " + "\n" + i.toString());

			this.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo privada que usa para cerrar el bufferedWritter
	 * 
	 * @throws IOException
	 */
	private void close() throws IOException {
		this.bufferedWriter.close();
	}

}
