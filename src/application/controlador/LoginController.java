package application.controlador;

import java.util.Optional;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.modelo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.dao.DaoProfesor;
import orm.pojos.Profesor;
import utiles.excepciones.BusinessException;

public class LoginController {

	@FXML
	private JFXTextField inpName;

	@FXML
	private JFXPasswordField inpPassword;

	@FXML
	private ImageView imgExit;

	@FXML
	private JFXButton btnSignIn;

	private Profesor profesor;
	private DaoProfesor daoProfesor;

	@FXML
	public void handleSignIn(ActionEvent actionEvent) throws Exception {
		System.out.println(inpName.getText());
		System.out.println(inpPassword.getText());

		daoProfesor = new DaoProfesor();

		if (daoProfesor.login(inpName.getText(), inpPassword.getText())) {
			profesor = daoProfesor.obtenerProfesor(inpName.getText());

			if (inpName.getText().equals(inpPassword.getText())) {
				cambioDePass();
			}

			System.out.println("Te has conectado");

			System.out.println("HAS CONSEGUIDO ACCEDER, EL USUARIO ES : " + profesor.toString() + "\n tiene rol de: "
					+ profesor.getRol().getDescripcion());

			Main principal = new Main(profesor);
			Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			principal.start(stage);

		} else

		{
			System.out.println("ERROR, AQUI MOSTRAR ERROR DE CONEXION");
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR AL INICIAR SESION");
			alerta.setHeaderText(null);
			alerta.setContentText("Usuario y/o contraseña incorrectas");
			alerta.setGraphic(new ImageView("/recursos/iconos/warning.png"));
			alerta.showAndWait();

			inpName.setText("");
			inpPassword.setText("");

		}

	}

	/**
	 * Metodo que se lanza si tu pass coincide con tu dni, que casualmente suele ser
	 * el primer acceso.
	 */
	private void cambioDePass() {

		JFXAlert<String> alert = new JFXAlert<>((Stage) btnSignIn.getScene().getWindow());
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
			if (pass.getText() == null || !pass.getText().equals(repetirPass.getText())) {
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
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			alert.setResult(null);
		}

	}

	@FXML
	void imgExitClick(MouseEvent event) {
		System.out.println("Aplicacion cerrada");
		System.exit(0);
	}

}
