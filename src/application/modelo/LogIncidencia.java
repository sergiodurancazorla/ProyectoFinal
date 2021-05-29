package application.modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

import orm.pojos.Incidencia;

public class LogIncidencia {

	private BufferedWriter bufferedWriter;
	private String ruta = "./logs";

	public LogIncidencia() throws IOException {
		// this.open(true);
	}

	public void addLine(String linea) {

	}

	public void logIncidencia(Incidencia i) throws IOException {

		this.bufferedWriter = new BufferedWriter(
				new FileWriter(ruta + "/incidencias/" + i.getIdincidencia() + ".txt", true));

		this.bufferedWriter.write("\n[" + Date.from(Instant.now()) + "] " + "\n" + i.toString());
		this.close();

	}

	private void close() throws IOException {
		this.bufferedWriter.close();
	}

}
