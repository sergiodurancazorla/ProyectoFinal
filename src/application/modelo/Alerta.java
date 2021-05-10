package application.modelo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Alerta {
	private StackPane stackPane;
	private String titulo;
	private String cuerpo;

	public Alerta(StackPane stackPane, String titulo, String cuerpo) {
		super();
		this.stackPane = stackPane;
		this.titulo = titulo;
		this.cuerpo = cuerpo;
	}

	public void mostrar() {

		JFXDialogLayout contenido = new JFXDialogLayout();
		contenido.setHeading(new Text(titulo));
		contenido.setBody(new Text(cuerpo));

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
