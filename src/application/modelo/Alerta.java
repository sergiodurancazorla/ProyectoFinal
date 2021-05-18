package application.modelo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import orm.pojos.Incidencia;

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

	public void mostrarAlerta() {
		JFXDialogLayout contenido = new JFXDialogLayout();
		contenido.setHeading(new Text(titulo));

		if (incidencia == null) {
			contenido.setBody(new Text(cuerpo));
		} else {

			contenido.setBody(new Text(incidencia.toString()));
		}
		JFXDialog dialogo = new JFXDialog();
		dialogo.setContent(contenido);
		dialogo.setTransitionType(JFXDialog.DialogTransition.CENTER);
		dialogo.setDialogContainer(stackPane);

		JFXButton boton = new JFXButton("Aceptar");
		boton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialogo.close();
			}
		});
		contenido.setActions(boton);
		dialogo.show();

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
