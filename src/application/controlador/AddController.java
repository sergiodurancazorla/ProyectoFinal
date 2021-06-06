package application.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.CorreoElectronico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
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

/**
 * Controlador de la vista Add. En esta clase se implementa el código para
 * guardar una nueva incidencia.
 * 
 * @author Sergio Duran
 *
 */
public class AddController implements Initializable {

	// FXML
	@FXML
	private StackPane stackPane;

	@FXML
	private AnchorPane idAnchorPane;

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

	// ATRIBUTOS PRIVADOS
	private FileChooser fileChooser;
	private Profesor profesor;
	private File archivo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.profesor = VariablesEstaticas.profesor;

		try {
			iniciar();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
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
	 * Cuando el usuario pincha en la X cierra add incidencia y vuelve al home.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void imgExitClick(MouseEvent event) throws IOException {

		StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Home.fxml"));

		((BorderPane) idAnchorPane.getParent().getParent()).setCenter(pane);

	}

	/**
	 * Método que guarda, si es todo correcto, la incidenccia en la BBDD
	 * 
	 * @param event
	 */
	@FXML
	void clickAnyadirIncidencia(ActionEvent event) {

		if (validar()) {
			Estado estado = comboEstado.getValue();
			Tipo tipo = comboTipoIncidencia.getValue();
			Profesor profesor = comboProfesor.getValue();
			Departamento departamento = comboDepartamento.getValue();
			Aula aula = comboUbicacion.getValue();

			// Crear incidencia
			Incidencia incidencia = new Incidencia(aula, departamento, estado, profesor, tipo);

			// ELEMENTOS EXTRA, fecha - descripcion - file - introduccion fecha introduccion
			if (fechaIncidencia.getValue() != null) {
				incidencia.setFechaIncidencia(Date.valueOf(fechaIncidencia.getValue()));
			} else {
				incidencia.setFechaIncidencia(Date.from(Instant.now()));

			}

			if (txtDescripcion.getText() != null) {
				incidencia.setDescripcion(txtDescripcion.getText());
			}

			if (archivo != null) {
				try {
					incidencia.setInformacion(Files.readAllBytes(archivo.toPath()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			incidencia.setFechaIntroduccion(Date.from(Instant.now()));

			// DAO y guardar incidencia
			orm.dao.DaoIncidencia daoIncidencia = new DaoIncidencia();

			try {
				daoIncidencia.grabar(incidencia);

				if (comboTipoIncidencia.getValue().toString().equals("Hardware")) {
					orm.dao.DaoInfoHardware daoHardware = new DaoInfoHardware();
					InfoHardware tipoHardware = new InfoHardware(incidencia, comboTipoHardware.getValue(),
							txtModelo.getText(), txtNumeroSerie.getText());
					daoHardware.grabar(tipoHardware);

				}

				// Guardar en log

				VariablesEstaticas.log.logIncidencia(incidencia);
				VariablesEstaticas.log.logGeneral("Ha creado una nueva incidencia");

				// mostra alerta
				Alerta alerta = new Alerta(stackPane, "Incidencia creada", "La incidencia se ha creado con éxito");
				alerta.mostrarAlerta();

				// Enviar correo al propietario si tiene email y no es el coordinador TIC
				if (profesor.getEmail() != null && !profesor.getNombre().equals("TIC")) {
					CorreoElectronico correo = new CorreoElectronico(profesor.getEmail(),
							"Creación de nueva incidencia",
							"Has creado una nueva incidencia, se ha avisado al coordinador TIC para que la gestione en el menor tiempo posible.\n"
									+ "ID incidencia: " + incidencia.getIdincidencia() + "\n" + incidencia.toString()
									+ "\n\nGracias!");
					correo.start();
				}

				// enviar correo a coordinador TIC informando de la incidencia.

				CorreoElectronico correo = new CorreoElectronico(VariablesEstaticas.EMAIL_COORDINADOR_TIC,
						"Han creado una nueva incidencia",
						"El usuario " + profesor.getNombre() + " " + profesor.getApellido1()
								+ " ha creado una nueva incidencia: \n \n" + incidencia.toString() + "\n\nGracias!");
				correo.start();

			} catch (BusinessException e) {
				// Mostrar error al guardar incidencia
				e.printStackTrace();
			}

			// una vez guardado limpiamos el formulario
			btnLimpiar.fire();

			// Abrimos listado de incidencias

			abrirListadoIincidencias();
		}
	}

	/**
	 * Metodo que abre el listado de incindencias cuando el usuario añade una
	 * incidencia.
	 */
	private void abrirListadoIincidencias() {

		try {
			AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Tabla.fxml"));
			((BorderPane) idAnchorPane.getParent().getParent()).setCenter(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método que controla el "Seleccionar un archivo" de la vista.
	 * 
	 * @param event
	 */
	@FXML
	void clickSubirArchivo(ActionEvent event) {

		fileChooser = new FileChooser();

		fileChooser.setTitle("Selecciona un archivo para añadir a la incidencia");

		// Seleccionar y guardar archivo
		archivo = fileChooser.showOpenDialog(idAnchorPane.getScene().getWindow());

		if (archivo != null) {
			btnSubirArchivo.setText(archivo.getName());
		}

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

		comboProfesor.setItems(listaProfesor);
		comboTipoIncidencia.setItems(listaTipos);
		comboDepartamento.setItems(listaDepartamento);
		comboUbicacion.setItems(listaAulas);
		// comboEstado.setItems(listaEstados);
		comboEstado.setValue(daoEstado.buscarPorId(1));
		comboEstado.setDisable(true);

		// si tiene rol diferente a admin no se permite modificar el profesor que la
		// crea
		if (profesor.getRol().getIdrol() != 1) {
			comboProfesor.getSelectionModel().select(profesor);
			comboProfesor.setDisable(true);
			comboProfesor.setOpacity(1);
		}

	}

	/**
	 * Metodo que limpia los inputs y combobox del formulario
	 * 
	 * @param event
	 */
	@FXML
	void btnLimpiar(ActionEvent event) {

		// vaciar
		txtModelo.setText("");
		txtNumeroSerie.setText("");
		comboTipoHardware.setValue(null);
		fechaIncidencia.setValue(LocalDate.now());
		comboTipoIncidencia.setValue(null);
		comboProfesor.setValue(null);
		comboDepartamento.setValue(null);
		comboUbicacion.setValue(null);
		txtDescripcion.setText("");
		fileChooser = null;
		btnSubirArchivo.setText("Subir archivo");

		// quitar estilo si los tuviera
		comboTipoIncidencia.setStyle(null);
		comboProfesor.setStyle(null);
		comboDepartamento.setStyle(null);
		comboUbicacion.setStyle(null);
		comboEstado.setStyle(null);

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
	 * Metodo que valida el formulario
	 * 
	 * @return True si todo es correcto
	 */

	private boolean validar() {
		boolean resultado = true;

		// tipo de incidencia

		if (comboTipoIncidencia.getValue() == null) {
			resultado = false;
			comboTipoIncidencia.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			new animatefx.animation.Shake(comboTipoIncidencia).play();

		} else {
			comboTipoIncidencia.setStyle(null);

			// Incidencia tipo hardware
			if (comboTipoIncidencia.getValue().toString().equals("Hardware") && comboTipoHardware.getValue() == null) {
				resultado = false;
				comboTipoHardware.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
				new animatefx.animation.Shake(comboTipoHardware).play();
			} else {
				comboTipoHardware.setStyle(null);
			}
		}

		// profesor

		if (comboProfesor.getValue() == null) {
			resultado = false;
			comboProfesor.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			new animatefx.animation.Shake(comboProfesor).play();

		} else {
			comboProfesor.setStyle(null);
		}

		// combo departamento
		if (comboDepartamento.getValue() == null) {
			resultado = false;
			comboDepartamento.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			new animatefx.animation.Shake(comboDepartamento).play();

		} else {
			comboDepartamento.setStyle(null);
		}

		// aula

		if (comboUbicacion.getValue() == null) {
			resultado = false;
			comboUbicacion.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			new animatefx.animation.Shake(comboUbicacion).play();

		} else {
			comboUbicacion.setStyle(null);
		}

		// estado

		if (comboEstado.getValue() == null) {
			resultado = false;
			comboEstado.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			new animatefx.animation.Shake(comboEstado).play();

		} else {
			comboEstado.setStyle(null);
		}

		return resultado;
	}
}
