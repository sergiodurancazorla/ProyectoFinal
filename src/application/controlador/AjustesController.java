package application.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.VariablesEstaticas;
import application.modelo.PDFGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
	void clickInformeMensual(ActionEvent event) throws IOException {

		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("pdf", ".pdf"));
		chooser.setTitle("¿Donde quieres guardar el informe?");
		File fichero = chooser.showSaveDialog(btnDatosPersonales.getScene().getWindow());

		PDFGenerator pdf = new PDFGenerator(fichero);
		pdf.start();
	}

	@FXML
	void clickPass(ActionEvent event) throws IOException {
		URL fxmlLocation = getClass().getClassLoader().getResource("application/vista/EditarPass.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlLocation);
		Parent panel = loader.load();

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text("Cambiar contraseña"));
		layout.setBody(panel);

		dialogo = new JFXDialog(((StackPane) idAnchorPane.getParent()), layout, JFXDialog.DialogTransition.CENTER);
		dialogo.show();
	}

	@FXML
	void clickRol(ActionEvent event) {

	}

	@FXML
	void clickTiposHardware(ActionEvent event) {

	}

}
