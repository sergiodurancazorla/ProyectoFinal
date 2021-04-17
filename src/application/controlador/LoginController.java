package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.modelo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import orm.dao.DaoProfesor;

public class LoginController implements Initializable {

	@FXML
	private JFXTextField inpName;

	@FXML
	private JFXPasswordField inpPassword;

	@FXML
	private ImageView imgExit;

	@FXML
	private JFXButton btnSignIn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void handleSignIn(ActionEvent actionEvent) throws Exception {
		System.out.println(inpName.getText());
		System.out.println(inpPassword.getText());

		DaoProfesor daoProfesor = new DaoProfesor();

		if (daoProfesor.login(inpName.getText(), inpPassword.getText())) {
			System.out.println("Te has conectado");
			Main principal = new Main();
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
