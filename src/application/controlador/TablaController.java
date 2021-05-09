
package application.controlador;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import orm.dao.DaoIncidencia;
import orm.pojos.Incidencia;
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
	void btnEditar(ActionEvent event) {

	}

	@FXML
	void clickAnyadirIncidencia(ActionEvent event) throws IOException {

		// Abrir Add incidencia

		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Add.fxml"));

		((BorderPane) idAnchorPane.getParent()).setCenter(pane);

		// Limpiar efectos

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// MOSTRAR INFORMACION EN LAS TABLAS

		// DAOS
		orm.dao.DaoIncidencia daoIncidencia = new DaoIncidencia();

		try {

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
			// TIPO
			JFXTreeTableColumn<Incidencia, String> columnaTipo = new JFXTreeTableColumn<>("Tipo");
			columnaTipo.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

						@Override
						public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {
							return new SimpleStringProperty(param.getValue().getValue().getTipo().getTipo());
						}
					});

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

			// DEPARTAMENTO

			JFXTreeTableColumn<Incidencia, String> columnaDepartamento = new JFXTreeTableColumn<>("Departamento");
			columnaDepartamento.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

						@Override
						public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {

							return new SimpleStringProperty(param.getValue().getValue().getDepartamento().getNombre());
						}
					});

			// AULA

			JFXTreeTableColumn<Incidencia, String> columnaAula = new JFXTreeTableColumn<>("Aula");
			columnaAula.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<Incidencia, String>, ObservableValue<String>>() {

						@Override
						public ObservableValue<String> call(CellDataFeatures<Incidencia, String> param) {

							return new SimpleStringProperty(param.getValue().getValue().getAula().getDescripcion());
						}
					});

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

			final TreeItem<Incidencia> root = new RecursiveTreeItem<Incidencia>(data, RecursiveTreeObject::getChildren);

			// Add a la tabla las columnas

			tablaIncidencias.getColumns().setAll(columnaId, columnaTipo, columnaFecha, columnaProfesor,
					columnaDepartamento, columnaAula, columnaResolucionProfesor, columnaResolucionFecha);
			tablaIncidencias.setRoot(root);
			// tablaIncidencias.setShowRoot(false);

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

}
