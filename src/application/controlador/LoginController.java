package application.controlador;

import java.util.Optional;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.dao.DaoProfesor;
import orm.pojos.Profesor;
import utiles.excepciones.BusinessException;

public class LoginController {

	// FXML
	@FXML
	private StackPane idStackPane;

	@FXML
	private JFXTextField inpName;

	@FXML
	private JFXPasswordField inpPassword;

	@FXML
	private ImageView imgExit;

	@FXML
	private JFXButton btnEntrar;

	// ATRIBUTOS PRIVADOS
	private Profesor profesor;
	private DaoProfesor daoProfesor;

	@FXML
	public void clickEntrar(ActionEvent actionEvent) throws Exception {
		daoProfesor = new DaoProfesor();

		if (daoProfesor.login(inpName.getText().toUpperCase(), inpPassword.getText())) {
			profesor = daoProfesor.obtenerProfesor(inpName.getText());

			if (inpName.getText().toUpperCase().equals(inpPassword.getText().toUpperCase())) {
				cambioDePass();
			}

			// Creamos ventana principal y le asignamos el profesor que va a usar la
			// aplicación.
			Main principal = new Main(profesor);
			Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			principal.start(stage);

			// LOG
			VariablesEstaticas.log.logGeneral("[INICIO DE SESION] El usuario " + profesor.getDni() + " con ROL: "
					+ profesor.getRol().getDescripcion() + " ha iniciado sesión.");

		} else

		{

			Alerta alerta = new Alerta(idStackPane, "ERROR AL INICIAR SESION", "Usuario y/o contraseña incorrectas");
			alerta.mostrarAlerta();
//			Alert alerta = new Alert(AlertType.ERROR);
//			alerta.setTitle("ERROR AL INICIAR SESION");
//			alerta.setHeaderText(null);
//			alerta.setContentText("Usuario y/o contraseña incorrectas");
//			alerta.setGraphic(new ImageView("/recursos/iconos/warning.png"));
//			alerta.showAndWait();

			inpName.setText("");
			inpPassword.setText("");

		}

	}

	/**
	 * Metodo que se lanza si tu pass coincide con tu dni, que casualmente suele ser
	 * el primer acceso.
	 */
	private void cambioDePass() {

		JFXAlert<String> alert = new JFXAlert<>((Stage) btnEntrar.getScene().getWindow());
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setOverlayClose(false);
		alert.setResultConverter(buttonType -> {
			return null;
		});

		// Objetos que va a tener el alert
		JFXPasswordField pass = new JFXPasswordField();
		pass.setPromptText("Escriba una contraseña nueva");
		pass.setPadding(new Insets(10));

		JFXPasswordField repetirPass = new JFXPasswordField();
		repetirPass.setPromptText("Repita la contraseña");
		repetirPass.setPadding(new Insets(10));

		Label label = new Label("Las contraseñas no coinciden");
		label.setVisible(false);
		label.setTextFill(Paint.valueOf("#E10202"));

		// Create the content of the JFXAlert with JFXDialogLayout
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label("Cambio de contraseña"));
		layout.setBody(new VBox(
				new Label("Hemos visto que no ha modificado su contraseña, por seguridad es recomendable cambiarla"),
				pass, repetirPass, label));

		// Buttons get added into the actions section of the layout.
		JFXButton addButton = new JFXButton("CONFIRMAR");
		addButton.setDefaultButton(true);
		addButton.setOnAction(addEvent -> {
			// Cuando se pulse el boton comprobar que pass y repetirPass sean iguales
			if (pass.getText() == null || pass.getText().trim().length() <= 0
					|| !pass.getText().equals(repetirPass.getText())) {
				label.setVisible(true);
				pass.setText("");
				repetirPass.setText("");

			} else {
				alert.setResult(pass.getText());
				alert.hideWithAnimation();
			}
		});

		JFXButton cancelButton = new JFXButton("CANCELAR");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());

		layout.setActions(addButton, cancelButton);
		alert.setContent(layout);

		// Si el alert devuelve una nueva pass modificamos y actualizamos profesor
		Optional<String> resultado = alert.showAndWait();
		if (resultado.isPresent()) {
			profesor.setPassword(resultado.get());
			try {
				daoProfesor.actualizar(profesor);
				VariablesEstaticas.log
						.logGeneral("[CAMBIO DE PASS] El usuario " + profesor.toString() + " ha cambiado su pass");
			} catch (BusinessException e) {
				e.printStackTrace();
				VariablesEstaticas.log.logGeneral("[ERROR] El usuario " + profesor.toString()
						+ " no ha podido modificar su pass " + e.toString());
			}
			alert.setResult(null);
		}

	}

	/**
	 * Metodo que cierra la app.
	 * 
	 * @param event
	 */
	@FXML
	void imgExitClick(MouseEvent event) {
		System.exit(0);
	}

}
