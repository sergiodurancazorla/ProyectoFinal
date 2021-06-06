package application.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.CorreoElectronico;
import application.modelo.DialogoEditar;
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

/**
 * Controlador de la vista editar incidencia. En esta clase se implementa el
 * código para editar una incidencia.
 * 
 * @author Sergio Duran
 *
 */
public class EditarIncidenciaController implements Initializable {

	// FXML
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

	@FXML
	private JFXButton btnEliminar;

	// ATRIBUTOS PRIVADOS
	private Incidencia incidencia;
	private Profesor profesor;
	private Boolean incidenciaModificada;

	// atributos modificacion
	private boolean booleanTipo;
	private Tipo modificadoTipo;
	private boolean booleanFecha;
	private Date modificadoFecha;
	private boolean booleanProfesor;
	private Profesor modificadoProfesor;
	private boolean booleanDepartamento;
	private Departamento modificadoDepartamento;
	private boolean booleanAula;
	private Aula modificadoAula;
	private boolean booleanDescripcion;
	private String modificadoDescripcion;
	private boolean booleanEstado;
	private Estado modificadoEstado;
	private boolean booleanResponsableSolucion;
	private Profesor modificadoResponsableSolucion;
	private boolean booleanObservaciones;
	private String modificadoObservaciones;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// se guarda el profesor en una variable privada
			this.profesor = VariablesEstaticas.profesor;
			incidenciaModificada = false;

			iniciar();
			addListenerIncidencia();

		} catch (BusinessException e) {
			e.printStackTrace();
			VariablesEstaticas.log.logGeneral("[ERROR] No se ha podido iniciar editar incidencia\n\t " + e.toString());
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
				booleanTipo = true;
				modificadoTipo = newValue;
				incidenciaModificada = true;
			}

		});

		// fecha
		fechaIncidencia.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				booleanFecha = true;
				modificadoFecha = Date.from(newValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				incidenciaModificada = true;
			}

		});

		// profesor
		comboProfesor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {

			@Override
			public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
				booleanProfesor = true;
				modificadoProfesor = newValue;
				incidenciaModificada = true;
			}
		});

		// departamento
		comboDepartamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Departamento>() {

			@Override
			public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue,
					Departamento newValue) {
				incidenciaModificada = true;
				booleanDepartamento = true;
				modificadoDepartamento = newValue;

			}
		});
		// ubicacion
		comboUbicacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Aula>() {

			@Override
			public void changed(ObservableValue<? extends Aula> observable, Aula oldValue, Aula newValue) {
				incidenciaModificada = true;
				booleanAula = true;
				modificadoAula = newValue;
			}
		});

		// descripcion
		txtDescripcion.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				incidenciaModificada = true;
				booleanDescripcion = true;
				modificadoDescripcion = newValue;
			}
		});

		// estado
		comboEstado.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Estado>() {

			@Override
			public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
				incidenciaModificada = true;
				booleanEstado = true;
				modificadoEstado = newValue;
			}

		});

		// Responsable Solucion
		comboResponsableSolucion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Profesor>() {

			@Override
			public void changed(ObservableValue<? extends Profesor> observable, Profesor oldValue, Profesor newValue) {
				incidenciaModificada = true;
				booleanResponsableSolucion = true;
				modificadoResponsableSolucion = newValue;
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
					comboResponsableSolucion.setValue(VariablesEstaticas.SAI);
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
				incidenciaModificada = true;
				booleanObservaciones = true;
				modificadoObservaciones = newValue;

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

		if (incidencia.getInformacion() != null) {
			btnSubirArchivo.setText("TIENE ARCHIVO");
		}

		// Si el responsable es el SAI checkear el checkbox
		if (incidencia.getProfesorByResponsableSolucion() != null
				&& incidencia.getProfesorByResponsableSolucion().equals(VariablesEstaticas.SAI)) {

			checkSAI.fire();
			comboResponsableSolucion.setEditable(false);
			comboResponsableSolucion.setDisable(true);

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

	/**
	 * Metodo que guarda la incidencia modificada si se ha modificado algo
	 * 
	 * @param event
	 * @throws BusinessException
	 * @throws IOException
	 */
	@FXML
	void guardarCambios(ActionEvent event) throws BusinessException, IOException {
		DaoIncidencia daoIncidencia = new DaoIncidencia();
		if (incidenciaModificada) {

			// Si la incidencia tiene el estado "Solucionada" o "Solucion inviable" se le
			// pone fecha de resolucion y se calcula su tiempo de resolucion
			if (incidencia.getEstado().getNombre().toUpperCase().equals("SOLUCIONADA")
					|| incidencia.getEstado().getNombre().toUpperCase().equals("SOLUCION INVIABLE")) {
				incidencia.setFechaResolucion(Date.from(Instant.now()));
				incidencia.setTiempoResolucion(calcularTiempoResolucion());

			} else {
				incidencia.setFechaResolucion(null);
				incidencia.setTiempoResolucion(null);
			}

			// setear datos modificados, si se ha modificado.
			if (booleanTipo) {
				incidencia.setTipo(modificadoTipo);
			}
			if (booleanFecha) {
				incidencia.setFechaIncidencia(modificadoFecha);
			}
			if (booleanAula) {
				incidencia.setAula(modificadoAula);
			}
			if (booleanDepartamento) {
				incidencia.setDepartamento(modificadoDepartamento);
			}
			if (booleanDescripcion) {
				incidencia.setDescripcion(modificadoDescripcion);
			}
			if (booleanEstado) {
				incidencia.setEstado(modificadoEstado);
			}
			if (booleanObservaciones) {
				incidencia.setObservaciones(modificadoObservaciones);
			}
			if (booleanProfesor) {
				incidencia.setProfesorByProfesorIdprofesor(modificadoProfesor);
			}
			if (booleanResponsableSolucion) {
				incidencia.setProfesorByResponsableSolucion(modificadoResponsableSolucion);
			}

			// ACTUALIZAR
			daoIncidencia.actualizar(incidencia);

			// GUARDAR LOGS
			VariablesEstaticas.log.logIncidencia(incidencia);

			// COMUNICAR EMAIL
			enviarEmails();

			// CERRAR
			DialogoEditar.cerrarDialogo();

		} else {
			// Si no se ha modificado nada se muestra alerta
			Alerta alerta = new Alerta(idStackPane, "Algo no ha salido como esperaba", "No has modificado nada!");
			alerta.mostrarAlerta();
		}

	}

	/**
	 * Metodo que elimina una incidencia
	 * 
	 * @param event
	 */
	@FXML
	void clickEliminar(ActionEvent event) {
		DaoIncidencia daoIncidencia = new DaoIncidencia();

		try {

			// Comprobacion si es de tipo hardware primero eliminar estos datos:
			if (incidencia.getTipo().getTipo().toUpperCase().equals("HARDWARE")) {
				DaoInfoHardware daoInfoHardware = new DaoInfoHardware();
				// Obtener informacion
				InfoHardware infoHardware = daoInfoHardware.informacionIncidencia(incidencia);

				// eliminar
				daoInfoHardware.borrar(infoHardware);

			}

			daoIncidencia.borrar(incidencia);

			// Mostrar alerta
			Alerta alerta = new Alerta(idStackPane, "Incidencia eliminada", "Se ha eliminado con exito la incidencia.");
			alerta.mostrarAlerta();

			// CERRAR
			DialogoEditar.cerrarDialogo();

		} catch (BusinessException e) {
			VariablesEstaticas.log.logGeneral("[ERROR ELMINAR INCIDENCIA] No se ha podido eliminar incidencia:"
					+ incidencia.getIdincidencia() + " \n\t" + e.getMessage());

		} catch (Exception e) {
			// No se puede eliminar
			VariablesEstaticas.log.logGeneral("[ERROR ELMINAR INCIDENCIA] No se ha podido eliminar incidencia:"
					+ incidencia.getIdincidencia() + " \n\t" + e.getMessage());

			// Mostrar alerta
			Alerta alerta = new Alerta(idStackPane, "ERROR AL ELIMINAR", "No se ha podido eliminar la incidencia");
			alerta.mostrarAlerta();
		}

	}

	/**
	 * Metodo que calcula el tiempo en SEGUNDOS que ha llevado resolver una
	 * incidencia
	 * 
	 * @return el tiempo en segundos
	 */
	private Integer calcularTiempoResolucion() {
		Integer respuesta = 0;

		// se obtiene la fecha
		Date dateInicio = incidencia.getFechaIncidencia();
		Date dateFin = incidencia.getFechaResolucion();

		// se pasa a dateTime
		DateTime dt1 = new DateTime(dateInicio);
		DateTime dt2 = new DateTime(dateFin);

		// Se usa libreria JodaTime para calcular los dias horas minutos y segundos
		int dias = Days.daysBetween(dt1, dt2).getDays();
		int horas = Hours.hoursBetween(dt1, dt2).getHours() % 24;
		int minutos = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
		int segundos = Seconds.secondsBetween(dt1, dt2).getSeconds() % 60;

		// se pasa a segundos
		respuesta = (dias * 86400) + (horas * 3600) + (minutos * 60) + segundos;
		return respuesta;
	}

	/**
	 * Metodo que envia los emails correspondientes cuando la incidencia se ha
	 * modificado
	 */
	private void enviarEmails() {

		// Mensaje
		String asunto = "Modificación incidencia";
		String cuerpo = "Actualizaciones de la incidencia " + incidencia.getIdincidencia() + ". \n"
				+ incidencia.toString();

		// Enviar correo electronico a Coordinador TIC

		CorreoElectronico correoElectronicoTIC = new CorreoElectronico(VariablesEstaticas.EMAIL_COORDINADOR_TIC, asunto,
				cuerpo);
		correoElectronicoTIC.start();

		// Si el responsable y el creador es el mismo, enviar solo un correo:

		Boolean repetido = false;

		// enviar al que la ha creado (si tiene email).
		if (incidencia.getProfesorByProfesorIdprofesor().getEmail() != null) {
			CorreoElectronico correoElectronicoCreador = new CorreoElectronico(
					incidencia.getProfesorByProfesorIdprofesor().getEmail(), asunto, cuerpo);
			correoElectronicoCreador.start();

			// Si son la misma persona
			if (incidencia.getProfesorByResponsableSolucion() != null && incidencia.getProfesorByResponsableSolucion()
					.equals(incidencia.getProfesorByProfesorIdprofesor())) {
				repetido = true;
			}

		}

		// si tiene responsable y no esta repetido
		if (!repetido && incidencia.getProfesorByResponsableSolucion() != null) {
			String emailResponsable = incidencia.getProfesorByResponsableSolucion().getEmail();

			CorreoElectronico correoElectronico = new CorreoElectronico(emailResponsable, asunto, cuerpo);
			correoElectronico.start();
		}

	}

	/**
	 * Metodo que cierra el dialogo
	 * 
	 * @param event
	 */
	@FXML
	void btnCancelar(ActionEvent event) {
		DialogoEditar.cerrarDialogo();

	}

}
