package application.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import orm.dao.DaoDepartamento;
import orm.dao.DaoProfesor;
import orm.dao.DaoRol;
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

	@FXML
	private JFXButton btnCrearRol;

	private ObservableList<Profesor> data;
	private FilteredList<Profesor> listaFiltros;
	private Profesor profesor;
	static JFXDialog dialogo;

	@FXML
	void btnEditar(ActionEvent event) {

	}

	@FXML
	void btnLimpiarFiltros(ActionEvent event) {
		filtroDepartamento.getSelectionModel().clearSelection();
		filtroRol.getSelectionModel().clearSelection();
		busquedaProfesor.setText("");
		tablaProfesores.refresh();
	}

	@FXML
	void clickAnyadirProfesor(ActionEvent event) throws IOException {

		URL fxmlLocation = getClass().getClassLoader().getResource("application/vista/CrearUsuario.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlLocation);
		Parent panel = loader.load();

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text("Crear nuevo usuario"));
		layout.setBody(panel);

		dialogo = new JFXDialog(((StackPane) idAnchorPane.getParent()), layout, JFXDialog.DialogTransition.CENTER);
		dialogo.show();

	}

	@FXML
	void clickCrearRol(ActionEvent event) {

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

		// Listener de seleccion, cuando un elemento de la lista es pulsado se habilita
		// el boton
		tablaProfesores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {

				btnEditar.setDisable(false);

			}

		});

	}

	/**
	 * Metodo que inicia los comboboxes de los filtros.
	 */

	private void inciarCombosFiltros() {
		orm.dao.DaoRol daoRol = new DaoRol();
		orm.dao.DaoDepartamento daoDepartamento = new DaoDepartamento();

		ObservableList<Departamento> listaDepartamento = FXCollections
				.observableArrayList(daoDepartamento.listadoDepartamentos());

		ObservableList<Rol> listaRol = FXCollections.observableArrayList(daoRol.listadoRol());
		filtroDepartamento.setItems(listaDepartamento);
		filtroRol.setItems(listaRol);

	}

	/**
	 * Método que crea los filtros de la tabla
	 */
	private void filtros() {
		ObjectProperty<Predicate<Profesor>> nombreFiltro = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Profesor>> rolFiltro = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Profesor>> departamentoFiltro = new SimpleObjectProperty<>();

		nombreFiltro.bind(Bindings.createObjectBinding(
				() -> profesor -> profesor.toString().toLowerCase().contains(busquedaProfesor.getText().toLowerCase()),
				busquedaProfesor.textProperty()

		));

		departamentoFiltro.bind(Bindings.createObjectBinding(
				() -> profesor -> filtroDepartamento.getValue() == null
						|| profesor.getDepartamento().equals(filtroDepartamento.getValue()),
				filtroDepartamento.valueProperty()

		));

		rolFiltro.bind(Bindings.createObjectBinding(
				() -> profesor -> filtroRol.getValue() == null || profesor.getRol().equals(filtroRol.getValue()),
				filtroRol.valueProperty()

		));

		listaFiltros = new FilteredList<Profesor>(data);
		final TreeItem<Profesor> root = new RecursiveTreeItem<Profesor>(listaFiltros, RecursiveTreeObject::getChildren);
		tablaProfesores.setRoot(root);
		listaFiltros.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> nombreFiltro.get().and(departamentoFiltro.get()).and(rolFiltro.get()), nombreFiltro,
						departamentoFiltro, rolFiltro));

	}

}
