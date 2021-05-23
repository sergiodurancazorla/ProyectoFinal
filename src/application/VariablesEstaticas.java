package application;

import javafx.collections.ObservableList;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;

public class VariablesEstaticas {
	// Gestion de emails:
	public static final String EMAIL_APLICACION = "proyectofinalaplicacion@gmail.com";
	public static final String PASS = "123456789A_";
	public static final String EMAIL_COORDINADOR_TIC = "proyectofinalcoordinador@gmail.com";

	/**
	 * Variable que guarda el profesor que ha inciado sesión
	 */
	public static Profesor profesor;

	public static Profesor SAI;

	/**
	 * Variable que guarda todas las incidencias de la app.
	 */
	public static ObservableList<Incidencia> data;

}
