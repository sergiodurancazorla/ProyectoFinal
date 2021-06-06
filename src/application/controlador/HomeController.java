package application.controlador;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.VariablesEstaticas;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import orm.dao.DaoIncidencia;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import orm.pojos.RolPermiso;
import utiles.excepciones.BusinessException;

/**
 * Controlador de la vista Home. En esta clase se implementa el codigo del
 * codigo de la pantalla principal de la aplicacion.
 * 
 * @author Sergio Duran
 *
 */
public class HomeController implements Initializable {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXTreeTableView<Incidencia> tablaIncidencias;

	@FXML
	private Label txtNombreSuperior;

	@FXML
	private Label txtNombre;

	@FXML
	private Label txtEmail;

	@FXML
	private Label txtDepartamento;

	@FXML
	private Label txtTotalIncidencias;

	@FXML
	private JFXCheckBox checkAdd;

	@FXML
	private JFXCheckBox checkModificarIncidencia;

	@FXML
	private JFXCheckBox checkModificarTipos;

	@FXML
	private JFXCheckBox checkRoles;

	@FXML
	private JFXCheckBox checkImportacion;

	@FXML
	private JFXCheckBox checkInformes;

	@FXML
	private Label txtPermisosDentroApp;

	private int totalIncidencias;
	private Profesor profesor;
	private ObservableList<Incidencia> data;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			profesor = VariablesEstaticas.profesor;

			iniciarTabla();

			txtNombreSuperior.setText(profesor.toString());
			txtNombre.setText(profesor.toString());
			txtEmail.setText(profesor.getEmail());
			txtDepartamento.setText(profesor.getDepartamento().getNombre());
			totalIncidencias = tablaIncidencias.getExpandedItemCount();
			txtTotalIncidencias.setText("Total de incidencias creadas: " + totalIncidencias);
			txtPermisosDentroApp.setText("Permisos dentro de la app: " + profesor.getRol().getDescripcion());

			rellenarChecks();

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que rellena los checks en funcion del rol y los permisos que tengas
	 * asignados
	 */

	private void rellenarChecks() {
		ArrayList<RolPermiso> listaPermisos = new ArrayList<RolPermiso>();
		listaPermisos.addAll(profesor.getRol().getRolPermisos());
		for (int i = 0; i < listaPermisos.size(); i++) {

			switch (listaPermisos.get(i).getPermiso().getIdpermiso()) {
			case 1:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkAdd.setSelected(true);
				break;
			case 2:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkModificarIncidencia.setSelected(true);
				break;
			case 3:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkModificarTipos.setSelected(true);
				break;
			case 4:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkRoles.setSelected(true);
				break;
			case 5:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkImportacion.setSelected(true);
				break;
			case 6:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkInformes.setSelected(true);
				break;

			default:
				break;
			}

		}

	}

	/**
	 * Metodo que inicia la tabla
	 * 
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void iniciarTabla() throws BusinessException {
		// DAO NECESARIO
		orm.dao.DaoIncidencia daoIncidencia = new DaoIncidencia();

		// Lista incidencias profesor
		data = FXCollections.observableArrayList(daoIncidencia.getIncidenciasProfesor(profesor));

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
	}

}
