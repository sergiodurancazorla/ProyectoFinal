package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.CorreoElectronico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import orm.dao.DaoDepartamento;
import orm.dao.DaoProfesor;
import orm.dao.DaoRol;
import orm.pojos.Departamento;
import orm.pojos.Profesor;
import orm.pojos.Rol;
import utiles.excepciones.BusinessException;

/**
 * Controlador de la vista crear usuario. En esta clase se implementa el código
 * para crear un nuevo usuario
 * 
 * @author Sergio Duran
 *
 */
public class CreacionUsuarioController implements Initializable {

	// FXML
	@FXML
	private StackPane idStackPane;

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXTextField txtDni;

	@FXML
	private JFXTextField txtNombre;

	@FXML
	private JFXTextField txtApellido1;

	@FXML
	private JFXTextField txtAoellido2;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXComboBox<Departamento> comboDepartamento;

	@FXML
	private JFXComboBox<Rol> comboRol;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			rellenarCombos();
			addListenerLength();

		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que crea listener para controlar caracteres máximos
	 */
	private void addListenerLength() {
		txtDni.addEventFilter(KeyEvent.KEY_TYPED, maxLength(24));
		txtNombre.addEventFilter(KeyEvent.KEY_TYPED, maxLength(44));
		txtApellido1.addEventFilter(KeyEvent.KEY_TYPED, maxLength(44));
		txtAoellido2.addEventFilter(KeyEvent.KEY_TYPED, maxLength(44));
		txtEmail.addEventFilter(KeyEvent.KEY_TYPED, maxLength(44));

	}

	/**
	 * Metodo que rellena los combos del formulario
	 * 
	 * @throws BusinessException
	 */
	private void rellenarCombos() throws BusinessException {

		// DAO
		orm.dao.DaoDepartamento daoDepartamento = new DaoDepartamento();
		orm.dao.DaoRol daoRol = new DaoRol();

		// LISTAS
		ObservableList<Departamento> listaDepartamento = FXCollections
				.observableArrayList(daoDepartamento.listadoDepartamentos());
		ObservableList<Rol> listadoRol = FXCollections.observableArrayList(daoRol.buscarTodos());

		// COMBO
		comboDepartamento.setItems(listaDepartamento);
		comboRol.setItems(listadoRol);

	}

	/**
	 * Metodo que controla cuando se pulsa en el boton cancelar
	 * 
	 * @param event
	 */
	@FXML
	void btnCancelar(ActionEvent event) {
		UserController.dialogo.close();
	}

	/**
	 * Metodo que controla cuando un usuario pulsa en guardar. Si el metodo valida y
	 * es correcto guarda los cambios
	 * 
	 * @param event
	 */
	@FXML
	void guardarCambios(ActionEvent event) {

		if (validar()) {

			// Datos
			String dni = txtDni.getText().toUpperCase();
			String nombre = txtNombre.getText();
			String apellido1 = txtApellido1.getText();
			Departamento departamento = comboDepartamento.getValue();
			Rol rol = comboRol.getValue();

			// Cuando se registra un usuario se le pone de pass su dni y en el primer
			// acceso la tiene que cambiar

			Profesor nuevoProfesor = new Profesor(dni, departamento, rol, nombre, apellido1);
			nuevoProfesor.setPassword(dni);

			// Datos no obligatorios
			if (txtAoellido2.getText() != null) {
				nuevoProfesor.setApellido2(txtAoellido2.getText());
			}

			if (txtEmail.getText() != null) {
				nuevoProfesor.setEmail(txtEmail.getText());
			}

			// DAO
			orm.dao.DaoProfesor daoProfesor = new DaoProfesor();

			try {
				// Grabar
				daoProfesor.grabar(nuevoProfesor);

				// log
				VariablesEstaticas.log.logGeneral("Ha creado un nuevo profesor: " + nuevoProfesor.toString());

				// enviar email
				if (nuevoProfesor.getEmail() != null) {
					CorreoElectronico correo = new CorreoElectronico(nuevoProfesor.getEmail(),
							"Alta nuevo usuario en Gestion de Incidencias",
							"Muy buenas!\nHas sido registrado en la app Gestion de Incidencias. Ahora puedes crear, modificar"
									+ " y ver las incidencias de tu centro. \n\nPara poder entrar a la aplicacion el usuario es su DNI y la contraseña"
									+ " el mismo DNI en mayúsculas. \n\nPara mayor seguridad se recomienda cambiar contraseña.\n\nUn saludo!");

					correo.start();
				}
			} catch (BusinessException e) {
				VariablesEstaticas.log.logGeneral("[ERROR] Al crear un nuevo usuario\n\t" + e.toString());
			} catch (org.hibernate.NonUniqueObjectException e) {
				Alerta alerta = new Alerta(idStackPane, "ERROR", "DNI repetido");
				alerta.mostrarAlerta();
			} finally {
				// Cerrar
				UserController.dialogo.close();
			}

		}

	}

	/**
	 * Metodo que valida que este todo correcto en el formulario
	 * 
	 * @return
	 */
	private boolean validar() {
		boolean resultado = true;

		// dni
		if (txtDni.getText().isEmpty()) {
			resultado = false;
			txtDni.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtDni).play();

		} else {
			txtDni.setStyle(null);
		}

		// nombre
		if (txtNombre.getText().isEmpty()) {
			resultado = false;
			txtNombre.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtNombre).play();

		} else {
			txtNombre.setStyle(null);
		}

		// Apellido 1
		if (txtApellido1.getText().isEmpty()) {
			resultado = false;
			txtApellido1.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtApellido1).play();

		} else {
			txtApellido1.setStyle(null);
		}

		// Departamento

		if (comboDepartamento.getValue() == null) {
			resultado = false;
			comboDepartamento.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(comboDepartamento).play();

		} else {
			comboDepartamento.setStyle(null);
		}

		// Rol

		if (comboRol.getValue() == null) {
			resultado = false;
			comboRol.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(comboRol).play();

		} else {
			comboRol.setStyle(null);
		}

		return resultado;
	}

	/**
	 * Metodo que controla caracteres máximos permitidos en un text field
	 * 
	 * @param i
	 * @return
	 */
	private EventHandler<KeyEvent> maxLength(Integer i) {
		return new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {

				JFXTextField tx = (JFXTextField) arg0.getSource();
				if (tx.getText().length() >= i) {
					arg0.consume();
				}

			}

		};

	}
}
