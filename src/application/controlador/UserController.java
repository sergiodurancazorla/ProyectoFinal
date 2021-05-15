package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import orm.dao.DaoProfesor;
import orm.pojos.Departamento;
import orm.pojos.Profesor;
import orm.pojos.Rol;
import utiles.excepciones.BusinessException;

public class UserController implements Initializable {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXButton btnAnyadirProfesor;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXTreeTableView<Profesor> tablaProfesores;

	@FXML
	private TextField busquedaProfesor;

	@FXML
	private ComboBox<Rol> filtroRol;

	@FXML
	private ComboBox<Departamento> filtroDepartamento;

	@FXML
	private JFXButton btnLimpiarFiltro;

	private ObservableList<Profesor> data;

	@FXML
	void btnEditar(ActionEvent event) {

	}

	@FXML
	void btnLimpiarFiltros(ActionEvent event) {

	}

	@FXML
	void clickAnyadirIncidencia(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			iniciarTabla();
			inciarCombosFiltros();
			filtros();

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void iniciarTabla() throws BusinessException {
		// DAO

		orm.dao.DaoProfesor daoProfesor = new DaoProfesor();
		data = FXCollections.observableArrayList(daoProfesor.buscarTodos());

		// -------COLUMNAS-----------

		// dni
		JFXTreeTableColumn<Profesor, String> columnaDni = new JFXTreeTableColumn<>("DNI");
		columnaDni.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getDni());
					}
				});
		columnaDni.setContextMenu(null);

		// nombre
		JFXTreeTableColumn<Profesor, String> columnaNombre = new JFXTreeTableColumn<>("Nombre");
		columnaNombre.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getNombre());
					}
				});
		columnaNombre.setContextMenu(null);

		// apellido 1
		JFXTreeTableColumn<Profesor, String> columnaApellido1 = new JFXTreeTableColumn<>("Apellido 1");
		columnaApellido1.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getApellido1());
					}
				});
		columnaApellido1.setContextMenu(null);

		// apellido 2
		JFXTreeTableColumn<Profesor, String> columnaApellido2 = new JFXTreeTableColumn<>("Apellido 2");
		columnaApellido2.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getApellido2());
					}
				});
		columnaApellido2.setContextMenu(null);
		// departamento
		JFXTreeTableColumn<Profesor, String> columnaDepartamento = new JFXTreeTableColumn<>("Departamento");
		columnaDepartamento.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getDepartamento().getNombre());
					}
				});
		columnaDepartamento.setContextMenu(null);
		// email
		JFXTreeTableColumn<Profesor, String> columnaEmail = new JFXTreeTableColumn<>("Email");
		columnaEmail.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getEmail());
					}
				});
		columnaEmail.setContextMenu(null);
		// rol
		JFXTreeTableColumn<Profesor, String> columnaRol = new JFXTreeTableColumn<>("Rol");
		columnaRol.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Profesor, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Profesor, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getRol().getDescripcion());
					}
				});
		columnaRol.setContextMenu(null);

		final TreeItem<Profesor> root = new RecursiveTreeItem<Profesor>(data, RecursiveTreeObject::getChildren);

		// Añadir columnas a la tabla

		tablaProfesores.getColumns().setAll(columnaDni, columnaNombre, columnaApellido1, columnaApellido2,
				columnaDepartamento, columnaEmail, columnaRol);

		// Caracteristicas de las tablas
		tablaProfesores.setRoot(root);
		tablaProfesores.setShowRoot(false);

	}

	private void inciarCombosFiltros() {
		// TODO Auto-generated method stub

	}

	private void filtros() {
		// TODO Auto-generated method stub

	}

}
