package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import orm.dao.DaoTipo;
import orm.pojos.Aula;
import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Profesor;
import orm.pojos.Tipo;

public class AddController implements Initializable {

	@FXML
	private GridPane gridHardware;

	@FXML
	private TextField txtModelo;

	@FXML
	private TextField txtNumeroSerie;

	@FXML
	private ComboBox<String> comboTipoHardware;

	@FXML
	private JFXDatePicker fechaIncidencia;

	@FXML
	private ComboBox<Tipo> comboTipoIncidencia;

	@FXML
	private ComboBox<Profesor> comboProfesor;

	@FXML
	private ComboBox<Departamento> comboDepartamento;

	@FXML
	private ComboBox<Aula> comboUbicacion;

	@FXML
	private ComboBox<Estado> comboEstado;

	@FXML
	private ComboBox<Profesor> comboResponsable;

	@FXML
	private JFXButton btnSubirArchivo;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private JFXButton btnAnyadirIncidencia;

	@FXML
	private JFXButton btnLimpiar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarCombos();
	}

	public void iniciarCombos() {

		// DAOS
		orm.dao.DaoTipo daoTipo = new DaoTipo();

		// LISTAS OBSERVABLES
		ObservableList<Tipo> lista = FXCollections.observableArrayList();
		lista.addAll(daoTipo.tiposIncidencias());

		// RELLENAR
		comboTipoIncidencia.setItems(lista);

	}
}
