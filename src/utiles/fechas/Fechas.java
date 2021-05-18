package utiles.fechas;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Fechas {

	/**
	 * Metodo que convierte un Date en LocalDate capturando la excepción cuando es
	 * un sql.Date
	 * 
	 * @param fecha Date
	 * @return LocalDate
	 */
	public static LocalDate convertDateObject(java.util.Date fecha) {

		try {

			return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		} catch (UnsupportedOperationException e) {
		}

		java.util.Date safeDate = new Date(fecha.getTime());

		return safeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	}

}
