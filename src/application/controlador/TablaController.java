
package application.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.DialogoEditar;
import application.modelo.PDFGenerator;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import orm.dao.DaoDepartamento;
import orm.dao.DaoEstado;
import orm.dao.DaoIncidencia;
import orm.dao.DaoTipo;
import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import orm.pojos.Tipo;
import utiles.excepciones.BusinessException;

/**
 * Controlador de la vista Tabla. En esta clase se implemente el codigo para
 * rellenar la tabla de las incidencias y las diferentes gestiones que en ella
 * puedes hacer.
 * 
 * @author Sergio Duran
 *
 */
public class TablaController implements Initializable {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXButton btnAnyadirIncidencia;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXTreeTableView<Incidencia> tablaIncidencias;

	/**
	 * Variable que guarda la lista de incidencias que se va a mostrar en la tabla
	 */
	private ObservableList<Incidencia> data;

	@FXML
	private TextField busquedaProfesor;

	@FXML
	private ComboBox<Estado> filtroEstado;

	@FXML
	private ComboBox<Departamento> filtroDepartamento;

	@FXML
	private ComboBox<Tipo> filtroTipo;

	@FXML
	private JFXButton btnLimpiarFiltro;

	private FilteredList<Incidencia> listaFiltros;

	private Profesor profesor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			this.profesor = VariablesEstaticas.profesor;

			menuContextual();

			iniciarTabla();

			inciarCombosFiltros();

			filtros();

			// controlador doble click
			tablaIncidencias.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getClickCount() == 2) {
						btnEditar.fire();
					}
				}
			});

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que limpia los filtros de la tabla
	 * 
	 * @param event
	 */

	@FXML
	void btnLimpiarFiltros(ActionEvent event) {

		filtroDepartamento.getSelectionModel().clearSelection();
		filtroEstado.getSelectionModel().clearSelection();
		filtroTipo.getSelectionModel().clearSelection();
		busquedaProfesor.setText("");
		tablaIncidencias.refresh();

	}

	/**
	 * Metodo que se lanza cuando pulsas el boton de editar. Abre dialogo para
	 * editar la incidencia que se pulsa
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void btnEditar(ActionEvent event) throws IOException {

		tablaIncidencias.refresh();

		TreeItem<Incidencia> item = tablaIncidencias.getSelectionModel().getSelectedItem();

		if (profesor.getRol().getIdrol() == 1 || profesor.getRol().getIdrol() == 3) {
			// Eres admin/coordinador TIC, abres la incidencia
			DialogoEditar editar = new DialogoEditar(item.getValue());
			editar.mostrarDialogo((StackPane) (idAnchorPane.getParent().getParent()));

		} else if (item.getValue().getProfesorByProfesorIdprofesor().getDni().equals(profesor.getDni())
				|| item.getValue().getProfesorByResponsableSolucion() != null
						&& item.getValue().getProfesorByResponsableSolucion().getDni().equals(profesor.getDni())) {
			// Eres creador o responsable de la incidencia
			DialogoEditar editar = new DialogoEditar(item.getValue());
			editar.mostrarDialogo((StackPane) (idAnchorPane.getParent().getParent()));

		} else {
			// no tienes permisos

			Alerta alerta = new Alerta((StackPane) (idAnchorPane.getParent().getParent()),
					"No puedes editar la incidencia",
					"No tienes permisos suficientes para poder editar esta incidencia.");
			alerta.mostrarAlerta();
		}
		tablaIncidencias.refresh();
	}

	/**
	 * Abre dialogo para anyadir nueva incidencia.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void clickAnyadirIncidencia(ActionEvent event) throws IOException {

		// Abrir Add incidencia

		StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Add.fxml"));

		((BorderPane) idAnchorPane.getParent()).setCenter(pane);

		// Limpiar efectos

	}

	/**
	 * Metodo que genera el menu al clicar boton derecha sobre una incidencia.
	 */
	private void menuContextual() {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem menuItem1 = new MenuItem("Editar");
		MenuItem menuItem2 = new MenuItem("Generar informe");
		menuItem1.setOnAction((event) -> {
			btnEditar.fire();

		});

		menuItem2.setOnAction((event) -> {
			generarInforme(event);
		});

		contextMenu.getItems().addAll(menuItem1, menuItem2);
		tablaIncidencias.contextMenuProperty().set(contextMenu);
	}

	private void generarInforme(ActionEvent event) {
		// Coger incidencia clickada
		TreeItem<Incidencia> item = tablaIncidencias.getSelectionModel().getSelectedItem();

		// Filechooser, donde se va a guardar?
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("pdf", "*.pdf"));
		chooser.setTitle("¿Donde quieres guardar el informe?");
		File fichero = chooser.showSaveDialog(btnEditar.getScene().getWindow());

		// Generar fichero pasando la incidencia

		PDFGenerator pdf = new PDFGenerator(fichero, item.getValue());
		pdf.start();
	}

	/**
	 * Método que crea los filtros de la tabla
	 */

	private void filtros() {
		ObjectProperty<Predicate<Incidencia>> nombreFiltro = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Incidencia>> estadoFiltro = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Incidencia>> departamentoFiltro = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<Incidencia>> tipoFiltro = new SimpleObjectProperty<>();

		nombreFiltro
				.bind(Bindings
						.createObjectBinding(
								() -> incidencia -> incidencia.getProfesorByProfesorIdprofesor().toString()
										.toLowerCase().contains(busquedaProfesor.getText().toLowerCase()),
								busquedaProfesor.textProperty()

						));

		estadoFiltro
				.bind(Bindings
						.createObjectBinding(
								() -> incidencia -> filtroEstado.getValue() == null
										|| incidencia.getEstado().equals(filtroEstado.getValue()),
								filtroEstado.valueProperty()

						));

		departamentoFiltro.bind(Bindings.createObjectBinding(
				() -> incidencia -> filtroDepartamento.getValue() == null
						|| incidencia.getDepartamento().equals(filtroDepartamento.getValue()),
				filtroDepartamento.valueProperty()

		));

		tipoFiltro.bind(Bindings.createObjectBinding(
				() -> incidencia -> filtroTipo.getValue() == null || incidencia.getTipo().equals(filtroTipo.getValue()),
				filtroTipo.valueProperty()

		));

		listaFiltros = new FilteredList<Incidencia>(data);

		final TreeItem<Incidencia> root = new RecursiveTreeItem<Incidencia>(listaFiltros,
				RecursiveTreeObject::getChildren);

		tablaIncidencias.setRoot(root);
		listaFiltros.predicateProperty().bind(Bindings.createObjectBinding(
				() -> nombreFiltro.get().and(estadoFiltro.get()).and(departamentoFiltro.get()).and(tipoFiltro.get()),
				nombreFiltro, estadoFiltro, departamentoFiltro, tipoFiltro));
	}

	/**
	 * Metodo que genera la tabla y añade las columnas
	 * 
	 * @throws BusinessException
	 */

	@SuppressWarnings("unchecked")
	private void iniciarTabla() throws BusinessException {
		// DAO NECESARIO
		orm.dao.DaoIncidencia daoIncidencia = new DaoIncidencia();

		// Lista incidencias
		data = FXCollections.observableArrayList(daoIncidencia.buscarTodos());

		// -------COLUMNAS-----------

		// Estado
		JFXTreeTableColumn<Incidencia, String> columnaId = new JFXTreeTableColumn<>("Estado");
		columnaId.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getEstado().getNombre());
					}
				});
		columnaId.setContextMenu(null);

		// TIPO
		JFXTreeTableColumn<Incidencia, String> columnaTipo = new JFXTreeTableColumn<>("Tipo");
		columnaTipo.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getTipo().getTipo());
					}
				});
		columnaTipo.setContextMenu(null);

		// FECHA

		JFXTreeTableColumn<Incidencia, String> columnaFecha = new JFXTreeTableColumn<>("Fecha creada");
		columnaFecha.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {

						SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");

						return new SimpleStringProperty(
								format.format(param.getValue().getValue().getFechaIncidencia()).toString());
					}
				});
		columnaFecha.setContextMenu(null);

		// PROFESOR
		JFXTreeTableColumn<Incidencia, String> columnaProfesor = new JFXTreeTableColumn<>("Informador");
		columnaProfesor.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {

						return new SimpleStringProperty(
								param.getValue().getValue().getProfesorByProfesorIdprofesor().toString());
					}
				});
		columnaProfesor.setContextMenu(null);

		// DEPARTAMENTO

		JFXTreeTableColumn<Incidencia, String> columnaDepartamento = new JFXTreeTableColumn<>("Departamento");
		columnaDepartamento.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {

						return new SimpleStringProperty(param.getValue().getValue().getDepartamento().getNombre());
					}
				});
		columnaDepartamento.setContextMenu(null);

		// AULA

		JFXTreeTableColumn<Incidencia, String> columnaAula = new JFXTreeTableColumn<>("Aula");
		columnaAula.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {

						return new SimpleStringProperty(param.getValue().getValue().getAula().getDescripcion());
					}
				});
		columnaAula.setContextMenu(null);

		// PROFESOR RESOLUCION

		JFXTreeTableColumn<Incidencia, String> columnaResolucionProfesor = new JFXTreeTableColumn<>("Responsable");
		columnaResolucionProfesor.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {
						SimpleStringProperty valor = new SimpleStringProperty();
						if (param.getValue().getValue().getProfesorByResponsableSolucion() == null) {
							valor.set("Sin asignar");
						} else {
							valor.set(param.getValue().getValue().getProfesorByResponsableSolucion().toString());
						}

						return valor;
					}
				});
		columnaResolucionProfesor.setContextMenu(null);

		// FECHA RESOLUCION

		JFXTreeTableColumn<Incidencia, String> columnaResolucionFecha = new JFXTreeTableColumn<>("Resolucion");
		columnaResolucionFecha.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {
						SimpleStringProperty valor = new SimpleStringProperty();
						if (param.getValue().getValue().getFechaResolucion() == null) {
							valor.set("No resuelta");
						} else {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");

							valor.set(format.format(param.getValue().getValue().getFechaResolucion()).toString());
						}

						return valor;
					}
				});
		columnaResolucionFecha.setContextMenu(null);

		final TreeItem<Incidencia> root = new RecursiveTreeItem<Incidencia>(data, RecursiveTreeObject::getChildren);

		// Add a la tabla las columnas

		tablaIncidencias.getColumns().setAll(columnaId, columnaTipo, columnaFecha, columnaProfesor, columnaDepartamento,
				columnaAula, columnaResolucionProfesor, columnaResolucionFecha);

		// Caracteristicas de las tablas
		tablaIncidencias.setRoot(root);
		tablaIncidencias.setShowRoot(false);

		// Listener de seleccion, cuando un elemento de la lista es pulsado se habilita
		// el boton
		tablaIncidencias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

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
		orm.dao.DaoEstado daoEstado = new DaoEstado();
		orm.dao.DaoTipo daoTipo = new DaoTipo();
		orm.dao.DaoDepartamento daoDepartamento = new DaoDepartamento();

		ObservableList<Tipo> listaTipos = FXCollections.observableArrayList(daoTipo.tiposIncidencias());
		ObservableList<Departamento> listaDepartamento = FXCollections
				.observableArrayList(daoDepartamento.listadoDepartamentos());
		ObservableList<Estado> listaEstados = FXCollections.observableArrayList(daoEstado.listadoEstados());

		filtroTipo.setItems(listaTipos);
		filtroEstado.setItems(listaEstados);
		filtroDepartamento.setItems(listaDepartamento);

	}

	/**
	 * Metodo que refresca la tabla y la recarga.
	 * 
	 * @param event
	 * @throws BusinessException
	 */
	@FXML
	void clickRefresh(MouseEvent event) throws BusinessException {
		btnLimpiarFiltro.fire();
		iniciarTabla();
		inciarCombosFiltros();
		filtros();
	}

}
