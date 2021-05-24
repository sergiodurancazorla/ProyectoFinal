package application.controlador;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.VariablesEstaticas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AjustesController {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXButton btnDatosPersonales;

	@FXML
	private JFXButton btnPass;

	@FXML
	private JFXButton btnInformeMensual;

	@FXML
	private JFXButton btnRol;

	@FXML
	private JFXButton btnTiposHardware;

	static JFXDialog dialogo;

	@FXML
	void clickDatosPersonales(ActionEvent event) throws IOException {
		VariablesEstaticas.editarProfesor = VariablesEstaticas.profesor;
		URL fxmlLocation = getClass().getClassLoader().getResource("application/vista/EditarUsuario.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlLocation);
		Parent panel = loader.load();

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text("Modificar datos personales"));
		layout.setBody(panel);

		dialogo = new JFXDialog(((StackPane) idAnchorPane.getParent()), layout, JFXDialog.DialogTransition.CENTER);
		dialogo.show();
	}

	@FXML
	void clickInformeMensual(ActionEvent event) {

	}

	@FXML
	void clickPass(ActionEvent event) {

	}

	@FXML
	void clickRol(ActionEvent event) {

	}

	@FXML
	void clickTiposHardware(ActionEvent event) {

	}

}
