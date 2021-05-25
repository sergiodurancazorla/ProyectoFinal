package application.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.VariablesEstaticas;
import application.modelo.PDFGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import orm.dao.DaoIncidencia;
import orm.pojos.Incidencia;

public class AjustesController implements Initializable {

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

	@FXML
	private JFXComboBox<String> comboMes;

	static JFXDialog dialogo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rellenarCombo();
	}

	private void rellenarCombo() {

		ObservableList<String> listaMes = FXCollections.observableArrayList("Enero", "Febrero", "Marzo", "Abril",
				"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
		comboMes.setItems(listaMes);

	}

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
	void seleccionarMes(ActionEvent event) {
		if (!comboMes.getSelectionModel().isEmpty()) {
			btnInformeMensual.setDisable(false);
		}
	}

	@FXML
	void clickInformeMensual(ActionEvent event) throws IOException {

		// Mes que quiere
		String stringMes = comboMes.getSelectionModel().getSelectedItem();
		int mes = transformarMes(stringMes);

		// Solicitar incidencias de ese mes
		DaoIncidencia daoIncidencia = new DaoIncidencia();
		ArrayList<Incidencia> listadoIncidencias = daoIncidencia.getIncidenciasMes(mes);

		// Pasar listado de incidencias en forma de lista?

		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("pdf", "*.pdf"));
		chooser.setTitle("¿Donde quieres guardar el informe?");
		File fichero = chooser.showSaveDialog(btnDatosPersonales.getScene().getWindow());

		PDFGenerator pdf = new PDFGenerator(fichero, listadoIncidencias);
		pdf.start();
	}

	private int transformarMes(String stringMes) {
		int mes = 0;

		switch (stringMes) {
		case "Enero":
			mes = 1;

			break;
		case "Febrero":
			mes = 2;

			break;
		case "Marzo":
			mes = 3;

			break;
		case "Abril":
			mes = 4;

			break;
		case "Mayo":
			mes = 5;

			break;
		case "Junio":
			mes = 6;

			break;
		case "Julio":
			mes = 7;

			break;
		case "Agosto":
			mes = 8;

			break;
		case "Septiembre":
			mes = 9;

			break;
		case "Octubre":
			mes = 10;

			break;
		case "Noviembre":
			mes = 11;

			break;
		case "Diciembre":
			mes = 12;

			break;

		}

		return mes;
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
