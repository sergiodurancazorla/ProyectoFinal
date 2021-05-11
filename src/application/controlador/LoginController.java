package application.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.modelo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import orm.dao.DaoProfesor;
import orm.pojos.Profesor;

public class LoginController {

	@FXML
	private JFXTextField inpName;

	@FXML
	private JFXPasswordField inpPassword;

	@FXML
	private ImageView imgExit;

	@FXML
	private JFXButton btnSignIn;

	@FXML
	public void handleSignIn(ActionEvent actionEvent) throws Exception {
		System.out.println(inpName.getText());
		System.out.println(inpPassword.getText());

		DaoProfesor daoProfesor = new DaoProfesor();

		if (daoProfesor.login(inpName.getText(), inpPassword.getText())) {
			System.out.println("Te has conectado");

			Profesor profesor = daoProfesor.obtenerProfesor(inpName.getText());
			System.out.println("HAS CONSEGUIDO ACCEDER, EL USUARIO ES : " + profesor.toString() + "\n tiene rol de: "
					+ profesor.getRol().getDescripcion());

			Main principal = new Main(profesor);
			Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			principal.start(stage);

		} else {
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

	@FXML
	void imgExitClick(MouseEvent event) {
		System.out.println("Aplicacion cerrada");
		System.exit(0);
	}

}
