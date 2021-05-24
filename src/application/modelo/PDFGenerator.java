package application.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.VariablesEstaticas;
import orm.pojos.Incidencia;

public class PDFGenerator extends Thread {

	private Incidencia incidencia;
	private File fichero;

	/**
	 * PDF de todas las incidencias
	 * 
	 * @param url
	 */
	public PDFGenerator(File fichero) {
		this.fichero = fichero;
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

		if (fichero != null) {

			try {
				// Se crea el documento
				Document documento = new Document();

				// El OutPutStream para el fichero donde crearemos el PDF
				FileOutputStream ficheroPDF = new FileOutputStream(fichero.getAbsolutePath());

				// Se asocia el documento de OutPutStream
				PdfWriter.getInstance(documento, ficheroPDF);

				// Se abre el documento
				documento.open();

				// Parrafo
				Paragraph titulo = new Paragraph("Lista de incidencias \n\n",
						FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE));

				// Añadimos el titulo al documento
				documento.add(titulo);

				// bucle que añade parrafos por incidencia

				for (int i = 0; i < VariablesEstaticas.data.size(); i++) {
					Paragraph parrafo = new Paragraph(VariablesEstaticas.data.get(i).toString());
					documento.add(parrafo);
				}

				documento.close();

			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
