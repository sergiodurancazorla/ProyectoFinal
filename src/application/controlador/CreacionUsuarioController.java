package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import orm.dao.DaoDepartamento;
import orm.dao.DaoProfesor;
import orm.dao.DaoRol;
import orm.pojos.Departamento;
import orm.pojos.Profesor;
import orm.pojos.Rol;
import utiles.excepciones.BusinessException;

public class CreacionUsuarioController implements Initializable {

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

		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

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

	@FXML
	void btnCancelar(ActionEvent event) {
	}

	@FXML
	void guardarCambios(ActionEvent event) {

		if (validar()) {

			// Datos
			String dni = txtDni.getText();
			String nombre = txtNombre.getText();
			String apellido1 = txtApellido1.getText();
			Departamento departamento = comboDepartamento.getValue();
			Rol rol = comboRol.getValue();

			// Cuando se registra un usuario se le pone de pass su dni y en el primer
			// acceso la tiene que cambiar

			Profesor nuevoProfesor = new Profesor(dni, departamento, rol, nombre, apellido1);
			nuevoProfesor.setPassword(dni);

			// DAO
			orm.dao.DaoProfesor daoProfesor = new DaoProfesor();

			try {
				// Grabar
				daoProfesor.grabar(nuevoProfesor);

				UserController.dialogo.close();

			} catch (BusinessException e) {
				e.printStackTrace();
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

}
