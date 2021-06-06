package application.modelo;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.pojos.Incidencia;

/**
 * Clase que crea una alerta bloqueante personalizada y la muestra en un
 * StackPane.
 * 
 * @author Sergio Duran
 *
 */
public class Alerta {
	private StackPane stackPane;
	private String titulo;
	private String cuerpo;
	private Incidencia incidencia;

	/**
	 * Crea una alerta simple con titulo y cuerpo
	 * 
	 * @param stackPane
	 * @param titulo
	 * @param cuerpo
	 */
	public Alerta(StackPane stackPane, String titulo, String cuerpo) {
		this.stackPane = stackPane;
		this.titulo = titulo;
		this.cuerpo = cuerpo;
	}

	/**
	 * Crea una alerta personalizada para editar incidencia.
	 * 
	 * @param stackPane
	 * @param titulo
	 * @param incidencia
	 */
	public Alerta(StackPane stackPane, String titulo, Incidencia incidencia) {
		this.stackPane = stackPane;
		this.titulo = titulo;
		this.incidencia = incidencia;
	}

	/**
	 * Metodo que genera la alerta y la muestra. Es bloqueante hasta que el usuario
	 * no pulse en aceptar la app se queda ahí parada.
	 */
	public void mostrarAlerta() {

		JFXAlert<String> alert = new JFXAlert<>((Stage) stackPane.getScene().getWindow());
		alert.initModality(Modality.APPLICATION_MODAL);

		JFXDialogLayout contenido = new JFXDialogLayout();
		contenido.setHeading(new Text(titulo));

		if (incidencia == null) {
			contenido.setBody(new Text(cuerpo));
		} else {

			contenido.setBody(new Text(incidencia.toString()));
		}

		JFXButton boton = new JFXButton("Aceptar");
		boton.setDefaultButton(true);
		boton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				alert.hideWithAnimation();
			}
		});

		contenido.setActions(boton);
		alert.setContent(contenido);
		alert.showAndWait();

	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

}
