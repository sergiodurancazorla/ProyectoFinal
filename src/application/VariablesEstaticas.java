package application;

import application.modelo.LogIncidencia;
import orm.pojos.Profesor;

/**
 * Clase donde se guardan las constantes y variables estaticas que tienen uso
 * compartido en la toda la aplicacion.
 * 
 * @author Sergio Duran
 *
 */
public class VariablesEstaticas {
	// ***********************CONTSTANTES*********************

	// Gestion de emails:
	public static final String EMAIL_APLICACION = "proyectofinalaplicacion@gmail.com";
	public static final String PASS = "123456789A_";
	public static final String EMAIL_COORDINADOR_TIC = "proyectofinalcoordinador@gmail.com";

	// *******************VARIABLES DE USO ESTATICO********************

	/**
	 * Variable que guarda el profesor que ha inciado sesión
	 */
	public static Profesor profesor;
	/**
	 * Variable que guarda el profesor SAI.
	 */
	public static Profesor SAI;

	/**
	 * Variable que guarda el profesor del cual se quiere modiicar cualquier dato.
	 */
	public static Profesor editarProfesor;

	/**
	 * Variable que se utiliza para generar logs. Hay dos tipos de logs relacionados
	 * con las incidencias y los generales de la app.
	 */
	public static LogIncidencia log;

}
