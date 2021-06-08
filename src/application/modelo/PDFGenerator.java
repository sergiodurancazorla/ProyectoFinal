package application.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.VariablesEstaticas;
import orm.pojos.Incidencia;

/**
 * Clase que se usa para generar PDF de listados de incidencias o de una
 * incidencia sola. Al tratarse de un hilo no basta con instanciarlo hay que
 * start().
 * 
 * @author Sergio Duran
 *
 */
public class PDFGenerator extends Thread {

	private Incidencia incidencia;
	private File fichero;
	ArrayList<Incidencia> listadoIncidencias;

	/**
	 * PDF de un listado de incidencias.
	 * 
	 * @param fichero
	 * @param listadoIncidencias
	 */
	public PDFGenerator(File fichero, ArrayList<Incidencia> listadoIncidencias) {
		this.fichero = fichero;
		this.listadoIncidencias = listadoIncidencias;
	}

	/**
	 * PDF de una incidencia en concreto
	 * 
	 * @param url
	 * @param incidencia
	 */
	public PDFGenerator(File fichero, Incidencia incidencia) {
		this.fichero = fichero;
		this.incidencia = incidencia;
	}

	@Override
	public void run() {
		try {
			// LOG
			VariablesEstaticas.log
					.logGeneral("[INFO]" + VariablesEstaticas.profesor.getDni() + " ha solicitado generar un pdf");

			// Se crea el documento
			Document documento = new Document();

			// El OutPutStream para el fichero donde crearemos el PDF
			FileOutputStream ficheroPDF = new FileOutputStream(fichero.getAbsolutePath());
			// Se asocia el documento de OutPutStream
			PdfWriter.getInstance(documento, ficheroPDF);

			// Se abre el documento
			documento.open();

			if (fichero != null && incidencia == null) {
				// *******LISTADO MENSUAL******

				// Parrafo titulo
				Paragraph titulo = new Paragraph("Listado de incidencias \n",
						FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));
				titulo.setAlignment(Element.ALIGN_CENTER);

				// Añadimos el titulo al documento
				documento.add(titulo);

				// bucle que añade parrafos por incidencia

				for (int i = 0; i < listadoIncidencias.size(); i++) {
					Paragraph parrafo = new Paragraph(listadoIncidencias.get(i).toString());
					documento.add(parrafo);
					Paragraph separador = new Paragraph(
							"--------------------------------------------------------------------------------------------------------------------------------");
					documento.add(separador);
				}

			} else if (fichero != null && incidencia != null) {

				// *******INCIDENCIA INDIVIDUAL**********

				// Parrafo titulo
				Paragraph titulo = new Paragraph("Información de la incidencia\n",
						FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));
				titulo.setAlignment(Element.ALIGN_CENTER);

				// Añadimos el titulo al documento
				documento.add(titulo);

				Paragraph parrafo = new Paragraph(incidencia.toString());
				documento.add(parrafo);
			}

			// al finalizar cerramos el documento.
			documento.close();

		} catch (FileNotFoundException | DocumentException | NullPointerException e) {
			VariablesEstaticas.log.logGeneral("[ERROR] No se ha podido generar PDF \n" + e.getMessage());
		}

	}

}
