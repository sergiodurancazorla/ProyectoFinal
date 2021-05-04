package application.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import orm.dao.DaoAula;
import orm.dao.DaoDepartamento;
import orm.dao.DaoEstado;
import orm.dao.DaoInfoHardware;
import orm.dao.DaoProfesor;
import orm.dao.DaoTipo;
import orm.pojos.Aula;
import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Profesor;
import orm.pojos.Tipo;
import orm.pojos.TipoHarware;

public class AddController implements Initializable {

	@FXML
	private GridPane gridHardware;

	@FXML
	private TextField txtModelo;

	@FXML
	private TextField txtNumeroSerie;

	@FXML
	private ComboBox<TipoHarware> comboTipoHardware;

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
	private JFXButton btnSubirArchivo;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private JFXButton btnAnyadirIncidencia;

	@FXML
	private JFXButton btnLimpiar;

	/***
	 * Metodo que hace visible/oculta el grid de harware
	 * 
	 * @param event
	 */
	@FXML
	void seleccionarItem(ActionEvent event) {
		if (comboTipoIncidencia.getSelectionModel().getSelectedItem().getTipo().equals("Hardware")) {
			gridHardware.setVisible(true);
			iniciarComboHardware();
		} else {
			gridHardware.setVisible(false);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciar();
	}

	private void iniciar() {

		// DAOS
		orm.dao.DaoTipo daoTipo = new DaoTipo();
		orm.dao.DaoProfesor daoProfesor = new DaoProfesor();
		orm.dao.DaoDepartamento daoDepartamento = new DaoDepartamento();
		orm.dao.DaoAula daoAula = new DaoAula();
		orm.dao.DaoEstado daoEstado = new DaoEstado();

		// LISTAS OBSERVABLES
		ObservableList<Tipo> listaTipos = FXCollections.observableArrayList(daoTipo.tiposIncidencias());
		ObservableList<Profesor> listaProfesor = FXCollections.observableArrayList(daoProfesor.listadoProfesores());
		ObservableList<Departamento> listaDepartamento = FXCollections
				.observableArrayList(daoDepartamento.listadoDepartamentos());
		ObservableList<Aula> listaAulas = FXCollections.observableArrayList(daoAula.listadoAulas());
		ObservableList<Estado> listaEstados = FXCollections.observableArrayList(daoEstado.listadoEstados());

		// PONER FECHA ACTUAL
		fechaIncidencia.setValue(LocalDate.now());

		// RELLENAR
		comboTipoIncidencia.setItems(listaTipos);
		comboProfesor.setItems(listaProfesor);
		comboDepartamento.setItems(listaDepartamento);
		comboUbicacion.setItems(listaAulas);
		comboEstado.setItems(listaEstados);

	}

	/**
	 * Metodo que rellena el combo de tipos de hardware
	 */
	private void iniciarComboHardware() {
		orm.dao.DaoInfoHardware daoHardware = new DaoInfoHardware();
		ObservableList<TipoHarware> listaTipos = FXCollections.observableArrayList(daoHardware.listadoTiposHw());
		comboTipoHardware.setItems(listaTipos);

	}
}
