package application.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.modelo.Alerta;
import application.modelo.DialogoEditar;
import application.modelo.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import orm.dao.DaoAula;
import orm.dao.DaoDepartamento;
import orm.dao.DaoEstado;
import orm.dao.DaoIncidencia;
import orm.dao.DaoInfoHardware;
import orm.dao.DaoProfesor;
import orm.dao.DaoTipo;
import orm.pojos.Aula;
import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Incidencia;
import orm.pojos.InfoHardware;
import orm.pojos.Profesor;
import orm.pojos.Tipo;
import orm.pojos.TipoHarware;
import utiles.excepciones.BusinessException;
import utiles.fechas.Fechas;

public class EditarIncidenciaController implements Initializable {

	private Incidencia incidencia;

	@FXML
	private JFXDatePicker fechaIncidencia;

	@FXML
	private JFXButton btnSubirArchivo;

	@FXML
	private JFXComboBox<Tipo> comboTipoIncidencia;

	@FXML
	private JFXComboBox<Profesor> comboProfesor;

	@FXML
	private JFXComboBox<Departamento> comboDepartamento;

	@FXML
	private JFXComboBox<Aula> comboUbicacion;

	@FXML
	private JFXComboBox<Estado> comboEstado;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private GridPane gridHardware;

	@FXML
	private JFXTextField txtModelo;

	@FXML
	private JFXTextField txtNumeroSerie;

	@FXML
	private JFXComboBox<TipoHarware> comboTipoHardware;

	@FXML
	private GridPane gridHardware1;

	@FXML
	private JFXComboBox<Profesor> comboResponsableSolucion;

	@FXML
	private TextArea txtObservaciones;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXCheckBox checkSAI;

	@FXML
	private StackPane idStackPane;

	private Profesor profesor;

	private Boolean incidenciaModificada;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.profesor = Main.profesor;
			incidenciaModificada = false;

			iniciar();
			addListenerIncidencia();

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que controla si se modifica algún valor de la incidencia. Si algun
	 * valor se modifica al "Confirmar" se actualizará la incidencia.
	 */
	private void addListenerIncidencia() {

		// tipo
		comboTipoIncidencia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tipo>() {

			@Override
			public void changed(ObservableValue<? extends Tipo> observable, Tipo oldValue, Tipo newValue) {
				incidencia.setTipo(newValue);
				incidenciaModificada = true;
			}

		});

		// fecha ********************************************************************
		fechaIncidencia.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				Date date = Date.from(newValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				incidencia.setFechaIncidencia(date);
				incidenciaModificada = true;
			}

		});

		// profesor
		comboProfesor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {

			@Override
			public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
				incidencia.setProfesorByProfesorIdprofesor(newValue);
				incidenciaModificada = true;
			}
		});

		// departamento
		comboDepartamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Departamento>() {

			@Override
			public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue,
					Departamento newValue) {
				incidencia.setDepartamento(newValue);
				incidenciaModificada = true;

			}
		});
		// ubicacion
		comboUbicacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Aula>() {

			@Override
			public void changed(ObservableValue<? extends Aula> observable, Aula oldValue, Aula newValue) {
				incidencia.setAula(newValue);
				incidenciaModificada = true;

			}
		});

		// descripcion
		txtDescripcion.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				incidenciaModificada = true;
				incidencia.setDescripcion(newValue);

			}
		});

		// estado
		comboEstado.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estado>() {

			@Override
			public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
				incidencia.setEstado(newValue);
				incidenciaModificada = true;

			}

		});

		// archivo *****************************

		// Responsable Solucion
		comboResponsableSolucion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {

			@Override
			public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
				incidenciaModificada = true;
				incidencia.setProfesorByResponsableSolucion(newValue);
				System.out.println(newValue);
			}

		});

		// Resolver por el SAI
		checkSAI.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					comboResponsableSolucion.getSelectionModel().clearSelection();
					comboResponsableSolucion.setEditable(false);
					comboResponsableSolucion.setDisable(true);
					comboResponsableSolucion.setPromptText("SAI");
				} else {
					comboResponsableSolucion.setDisable(false);
					comboResponsableSolucion.getSelectionModel().clearSelection();
					comboResponsableSolucion.setPromptText("");

				}
			}
		});

		// Observaciones
		txtObservaciones.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(newValue);
				incidenciaModificada = true;

			}
		});

	}

	/***
	 * Metodo que inicia y rellena los combos del formulario
	 * 
	 * @throws BusinessException
	 */

	private void iniciar() throws BusinessException {

		// DAOS
		orm.dao.DaoTipo daoTipo = new DaoTipo();
		orm.dao.DaoProfesor daoProfesor = new DaoProfesor();
		orm.dao.DaoDepartamento daoDepartamento = new DaoDepartamento();
		orm.dao.DaoAula daoAula = new DaoAula();
		orm.dao.DaoEstado daoEstado = new DaoEstado();
		orm.dao.DaoInfoHardware daoInfoHardware = new DaoInfoHardware();
		orm.dao.DaoInfoHardware daoHardware = new DaoInfoHardware();

		// LISTAS OBSERVABLES
		ObservableList<Tipo> listaTipos = FXCollections.observableArrayList(daoTipo.tiposIncidencias());
		ObservableList<Profesor> listaProfesor = FXCollections.observableArrayList(daoProfesor.listadoProfesores());
		ObservableList<Departamento> listaDepartamento = FXCollections
				.observableArrayList(daoDepartamento.listadoDepartamentos());
		ObservableList<Aula> listaAulas = FXCollections.observableArrayList(daoAula.listadoAulas());
		ObservableList<Estado> listaEstados = FXCollections.observableArrayList(daoEstado.listadoEstados());
		ObservableList<TipoHarware> listaTipoHardware = FXCollections.observableArrayList(daoHardware.listadoTiposHw());

		// PONER FECHA INCIDENCIA
		Date date = incidencia.getFechaIncidencia();
		fechaIncidencia.setValue(Fechas.convertDateObject(date));

		// RELLENAR combos
		comboProfesor.setItems(listaProfesor);
		comboProfesor.getSelectionModel().select(incidencia.getProfesorByProfesorIdprofesor());

		comboTipoIncidencia.setItems(listaTipos);
		comboTipoIncidencia.getSelectionModel().select(incidencia.getTipo());

		comboDepartamento.setItems(listaDepartamento);
		comboDepartamento.getSelectionModel().select(incidencia.getDepartamento());

		comboUbicacion.setItems(listaAulas);
		comboUbicacion.getSelectionModel().select(incidencia.getAula());

		comboEstado.setItems(listaEstados);
		comboEstado.getSelectionModel().select(incidencia.getEstado());

		comboResponsableSolucion.setItems(listaProfesor);
		comboResponsableSolucion.getSelectionModel().select(incidencia.getProfesorByResponsableSolucion());

		// RELLENAR el resto de info

		if (incidencia.getDescripcion() != null) {
			txtDescripcion.setText(incidencia.getDescripcion());
		}

		// tipo hardware:
		if (incidencia.getTipo().getTipo().equals("Hardware")) {

			gridHardware.setVisible(true);
			comboTipoHardware.setItems(listaTipoHardware);

			InfoHardware infoHardware = daoInfoHardware.informacionIncidencia(incidencia);
			comboTipoHardware.setValue(infoHardware.getTipoHarware());
			if (infoHardware.getModelo() != null) {
				txtModelo.setText(infoHardware.getModelo());
			}

			if (infoHardware.getNumeroSerie() != null) {
				txtNumeroSerie.setText(infoHardware.getNumeroSerie());
			}

		}

		// MODIFICAR****************************
		if (incidencia.getInformacion() != null) {
			btnSubirArchivo.setText("TIENE ARCHIVO");
		}

		// si tiene rol diferente a admin no se permite modificar el profesor que la
		// crea y asignar responsable y SAI bloqueados.
		if (profesor.getRol().getIdrol() != 1) {
			comboProfesor.getSelectionModel().select(profesor);
			comboProfesor.setDisable(true);
			comboProfesor.setOpacity(1);
			comboResponsableSolucion.setDisable(true);
			checkSAI.setDisable(true);
		}

	}

	/**
	 * Añade la incidencia que quieras editar
	 * 
	 * @param i incidencia
	 */
	public void addIncidencia(Incidencia i) {
		this.incidencia = i;

	}

	/**
	 * Metodo que rellena el combo de tipos de hardware
	 */
	private void iniciarComboHardware() {
		orm.dao.DaoInfoHardware daoHardware = new DaoInfoHardware();
		ObservableList<TipoHarware> listaTipos = FXCollections.observableArrayList(daoHardware.listadoTiposHw());
		comboTipoHardware.setItems(listaTipos);

	}

	/***
	 * Metodo que hace visible/oculta el grid de hardware
	 * 
	 * @param event
	 */
	@FXML
	void seleccionarItem(ActionEvent event) {
		if (!comboTipoIncidencia.getSelectionModel().isEmpty()
				&& comboTipoIncidencia.getSelectionModel().getSelectedItem().getTipo().equals("Hardware")) {
			gridHardware.setVisible(true);
			iniciarComboHardware();
		} else {
			gridHardware.setVisible(false);
		}

	}

	@FXML
	void guardarCambios(ActionEvent event) throws BusinessException, IOException {
		DaoIncidencia daoIncidencia = new DaoIncidencia();
		if (incidenciaModificada) {
			daoIncidencia.actualizar(incidencia);

			DialogoEditar.cerrarDialogo();

		} else {
			Alerta alerta = new Alerta(idStackPane, "Algo no ha salido como esperaba", "No has modificado nada!");
			alerta.mostrarAlerta();
		}

	}

	@FXML
	void btnCancelar(ActionEvent event) {
		DialogoEditar.cerrarDialogo();

	}

}
