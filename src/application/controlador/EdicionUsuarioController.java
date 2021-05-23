package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class EdicionUsuarioController implements Initializable {

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

	private Profesor editarProfesor;
	private Boolean profesorModificado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			this.editarProfesor = VariablesEstaticas.editarProfesor;
			profesorModificado = false;
			rellenarInfo();
			rellenarCombos();
			addListenerProfesor();

		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	private void addListenerProfesor() {

		// dni
		txtDni.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				profesorModificado = true;
				editarProfesor.setDni(newValue);

			}
		});

		// nombre
		txtNombre.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				profesorModificado = true;
				editarProfesor.setNombre(newValue);

			}
		});

		// apellido1
		txtApellido1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				profesorModificado = true;
				editarProfesor.setApellido1(newValue);

			}
		});

		// apellido2
		txtAoellido2.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				profesorModificado = true;
				editarProfesor.setApellido2(newValue);

			}
		});

		// Departamento
		comboDepartamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Departamento>() {

			@Override
			public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue,
					Departamento newValue) {
				editarProfesor.setDepartamento(newValue);
				profesorModificado = true;
			}

		});

		// email
		txtEmail.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				profesorModificado = true;
				editarProfesor.setEmail(newValue);

			}
		});

		// rol
		comboRol.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rol>() {

			@Override
			public void changed(ObservableValue<? extends Rol> observable, Rol oldValue, Rol newValue) {
				editarProfesor.setRol(newValue);
				profesorModificado = true;
			}

		});
	}

	private void rellenarInfo() {
		txtDni.setText(editarProfesor.getDni());
		txtApellido1.setText(editarProfesor.getApellido1());
		txtNombre.setText(editarProfesor.getNombre());
		comboDepartamento.setValue(editarProfesor.getDepartamento());
		comboRol.setValue(editarProfesor.getRol());

		if (editarProfesor.getEmail() != null) {
			txtEmail.setText(editarProfesor.getEmail());
		}

		if (editarProfesor.getApellido2() != null) {
			txtAoellido2.setText(editarProfesor.getApellido2());
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
		UserController.dialogo.close();
	}

	@FXML
	void guardarCambios(ActionEvent event) {

		if (validar() && profesorModificado) {

			orm.dao.DaoProfesor daoProfesor = new DaoProfesor();

			try {
				// Grabar
				daoProfesor.actualizar(editarProfesor);

				UserController.dialogo.close();

			} catch (BusinessException e) {
				e.printStackTrace();
			}

		} else {
			Alerta alerta = new Alerta(idStackPane, "Algo no ha salido como esperaba", "No has modificado nada!");
			alerta.mostrarAlerta();
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
