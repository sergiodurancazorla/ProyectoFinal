package application.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import orm.dao.DaoProfesor;
import orm.pojos.Profesor;
import utiles.excepciones.BusinessException;

/**
 * Controlador de la vista editar pass. En esta clase se implementa el código
 * para editar la pass del usuario.
 * 
 * @author Sergio Duran
 *
 */
public class EditarPassController {

	@FXML
	private StackPane idStackPane;

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXPasswordField txtAntiguaPass;

	@FXML
	private JFXPasswordField txtNuevaPass;

	@FXML
	private JFXPasswordField txtRepiteNuevaPass;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	/**
	 * Metodo para cerra dialogo
	 * 
	 * @param event
	 */
	@FXML
	void btnCancelar(ActionEvent event) {
		AjustesController.dialogo.close();

	}

	/**
	 * Metodo que guarda los cambios de la pass si se valida correctamente
	 * 
	 * @param event
	 * @throws BusinessException
	 */
	@FXML
	void guardarCambios(ActionEvent event) throws BusinessException {

		if (validar()) {

			// las nuevas pass coinciden?

			if (txtNuevaPass.getText().equals(txtRepiteNuevaPass.getText())) {
				// Sí coinciden,

				Profesor profesor = VariablesEstaticas.profesor;
				if (txtAntiguaPass.getText().equals(profesor.getPassword())) {
					// contraseña antigua correcto: se cambia la pass
					DaoProfesor daoProfesor = new DaoProfesor();
					profesor.setPassword(txtNuevaPass.getText());
					daoProfesor.actualizar(profesor);
					Alerta alerta = new Alerta(idStackPane, "ERROR", "No se ha podido modificar la contraseña.");
					alerta.mostrarAlerta();
					AjustesController.dialogo.close();

				} else {
					// Contraseña antigua mal: se muestra error al modificar pass
					Alerta alerta = new Alerta(idStackPane, "ERROR", "No se ha podido modificar la contraseña.");
					alerta.mostrarAlerta();
				}

			} else {

				// mostrar alerta de que las contraseñas no coinciden
				Alerta alerta = new Alerta(idStackPane, "ERROR", "Las nuevas contraseñas no coinciden.");
				alerta.mostrarAlerta();
				txtNuevaPass.setText("");
				txtRepiteNuevaPass.setText("");

			}

		}

	}

	/**
	 * Metodo que comprueba si ha rellenado todos los textField del formulario
	 *
	 * @return true si esta todo rellenado, else false
	 */
	private boolean validar() {
		boolean resultado = true;

		// que rellene todos los inputs
		if (txtAntiguaPass.getText().isEmpty()) {
			resultado = false;
			txtAntiguaPass.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtAntiguaPass).play();

		} else {
			txtAntiguaPass.setStyle(null);
		}

		if (txtNuevaPass.getText().isEmpty()) {
			resultado = false;
			txtNuevaPass.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtNuevaPass).play();

		} else {
			txtNuevaPass.setStyle(null);
		}

		if (txtRepiteNuevaPass.getText().isEmpty()) {
			resultado = false;
			txtRepiteNuevaPass.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtRepiteNuevaPass).play();

		} else {
			txtRepiteNuevaPass.setStyle(null);
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
