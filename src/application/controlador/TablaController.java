
package application.controlador;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import orm.dao.DaoDepartamento;
import orm.dao.DaoEstado;
import orm.dao.DaoIncidencia;
import orm.dao.DaoTipo;
import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Incidencia;
import orm.pojos.Tipo;
import utiles.excepciones.BusinessException;

public class TablaController implements Initializable {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXButton btnAnyadirIncidencia;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXTreeTableView<Incidencia> tablaIncidencias;

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

	@FXML
	void btnLimpiarFiltros(ActionEvent event) {

		filtroDepartamento.getSelectionModel().clearSelection();
		filtroEstado.getSelectionModel().clearSelection();
		filtroTipo.getSelectionModel().clearSelection();
		tablaIncidencias.refresh();
	}

	@FXML
	void btnEditar(ActionEvent event) {

		TreeItem item = tablaIncidencias.getSelectionModel().getSelectedItem();

		System.out.println(((Incidencia) item.getValue()).getIdincidencia());
	}

	@FXML
	void clickAnyadirIncidencia(ActionEvent event) throws IOException {

		// Abrir Add incidencia

		StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Add.fxml"));

		((BorderPane) idAnchorPane.getParent()).setCenter(pane);

		// Limpiar efectos

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			// MOSTRAR INFORMACION EN LAS TABLAS

			// DAOS
			orm.dao.DaoIncidencia daoIncidencia = new DaoIncidencia();

			data = FXCollections.observableArrayList(daoIncidencia.buscarTodos());

			// COLUMNAS

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
								valor.set("Responsable sin asignar");
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

			tablaIncidencias.getColumns().setAll(columnaId, columnaTipo, columnaFecha, columnaProfesor,
					columnaDepartamento, columnaAula, columnaResolucionProfesor, columnaResolucionFecha);

			// Caracteristicas de las tablas
			tablaIncidencias.setRoot(root);
			tablaIncidencias.setShowRoot(false);

			// Listener de seleccion
			tablaIncidencias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

				@Override
				public void changed(ObservableValue observable, Object oldValue, Object newValue) {

					btnEditar.setDisable(false);

				}

			});

			inciarCombosFiltros();

			// Listener de Filtros busqueda

			listaFiltros = new FilteredList<>(data, e -> true);

			busquedaProfesor.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					tablaIncidencias.setPredicate(new Predicate<TreeItem<Incidencia>>() {

						@Override
						public boolean test(TreeItem<Incidencia> incidencia) {

							Boolean resultado = incidencia.getValue().getProfesorByProfesorIdprofesor().toString()
									.contains(newValue);

							return resultado;
						}
					});

				}
			});
			filtroEstado.valueProperty().addListener(new ChangeListener<Estado>() {

				@Override
				public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
					tablaIncidencias.setPredicate(new Predicate<TreeItem<Incidencia>>() {

						@Override
						public boolean test(TreeItem<Incidencia> incidencia) {

							Boolean resultado;

							if (newValue == null) {
								resultado = true;
							} else {
								resultado = incidencia.getValue().getEstado().equals(newValue);
							}

							return resultado;
						}
					});
				}

			});
			filtroDepartamento.valueProperty().addListener(new ChangeListener<Departamento>() {

				@Override
				public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue,
						Departamento newValue) {
					tablaIncidencias.setPredicate(new Predicate<TreeItem<Incidencia>>() {

						@Override
						public boolean test(TreeItem<Incidencia> incidencia) {

							Boolean resultado;

							if (newValue == null) {
								resultado = true;
							} else {
								resultado = incidencia.getValue().getDepartamento().equals(newValue);
							}

							return resultado;
						}
					});
				}
			});

			filtroTipo.valueProperty().addListener(new ChangeListener<Tipo>() {

				@Override
				public void changed(ObservableValue<? extends Tipo> observable, Tipo oldValue, Tipo newValue) {

					tablaIncidencias.setPredicate(new Predicate<TreeItem<Incidencia>>() {

						@Override
						public boolean test(TreeItem<Incidencia> incidencia) {

							Boolean resultado;

							if (newValue == null) {
								resultado = true;
							} else {
								resultado = incidencia.getValue().getTipo().equals(newValue);
							}

							return resultado;
						}
					});

				}
			});

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

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

}
