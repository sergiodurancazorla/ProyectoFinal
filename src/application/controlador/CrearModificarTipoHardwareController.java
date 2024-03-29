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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import orm.dao.DaoInfoHardware;
import orm.pojos.TipoHarware;
import utiles.dao.DaoGenericoHibernate;
import utiles.excepciones.BusinessException;

/**
 * Controlador del dialogo de crear o modificar un tipo de Hardware.
 * 
 * @author Sergio Duran
 *
 */
public class CrearModificarTipoHardwareController implements Initializable {

	// FXML

	@FXML
	private StackPane idStackPane;

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXTextField txtNombre;

	@FXML
	private JFXComboBox<TipoHarware> comboTipoHardware;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnEliminar;

	@FXML
	private ImageView btnInfo;

	// DAO
	DaoGenericoHibernate<TipoHarware, Integer> daoTipoHardware;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Rellenar Combo
		orm.dao.DaoInfoHardware daoHardware = new DaoInfoHardware();
		ObservableList<TipoHarware> listaTipos = FXCollections.observableArrayList(daoHardware.listadoTiposHw());
		comboTipoHardware.setItems(listaTipos);

		// add un tipo vacio para editar/crear nuevo
		TipoHarware hardwareVacio = new TipoHarware();
		hardwareVacio.setNombre("");
		comboTipoHardware.getItems().add(0, hardwareVacio);

		// a�adir listener al combo
		comboTipoHardware.valueProperty().addListener(new ChangeListener<TipoHarware>() {

			@Override
			public void changed(ObservableValue<? extends TipoHarware> observable, TipoHarware oldValue,
					TipoHarware newValue) {
				// si el seleccionado es el vacio
				if (newValue.getNombre().equals("")) {
					btnEliminar.setVisible(false);
					txtNombre.setText("");
					btnConfirmar.setText("Crear nuevo");

				} else {
					// se ha seleccionado un TIPO
					btnEliminar.setVisible(true);
					txtNombre.setText(newValue.getNombre());
					btnConfirmar.setText("Modificar");
				}

			}

		});

		// a�adir listener de length maximo
		txtNombre.addEventFilter(KeyEvent.KEY_TYPED, maxLength(44));

		// iniciar dao
		daoTipoHardware = new DaoGenericoHibernate<TipoHarware, Integer>();

	}

	/**
	 * Metodo que cierra el dialogo
	 * 
	 * @param event
	 */
	@FXML
	void btnCancelar(ActionEvent event) {
		AjustesController.dialogo.close();

	}

	/**
	 * Metodo que elimina un tipo de hardware (si se puede eliminar).
	 * 
	 * @param event
	 */
	@FXML
	void clickEliminar(ActionEvent event) {

		// obtener item TipoHardware para eliminar
		TipoHarware eliminar = comboTipoHardware.valueProperty().getValue();

		try {
			daoTipoHardware.borrar(eliminar);

			// Mostrar alerta
			Alerta alerta = new Alerta(idStackPane, "Eliminado ", "Se ha eliminado el tipo de hardware correctamente");
			alerta.mostrarAlerta();

			// cerrar dialogo
			AjustesController.dialogo.close();

		} catch (Exception e) {
			// Error al eliminar
			VariablesEstaticas.log.logGeneral("[ERROR ACTUALIZAR TIPO HARDWARE] No se ha podido actualizar: "
					+ eliminar.getNombre() + " \n\t" + e.getMessage());

			// Mostrar alerta
			Alerta alerta = new Alerta(idStackPane, "ERROR AL ELIMINAR",
					"Si el tipo de hardware est� asignado a alguna incidencia no se puede eliminar");
			alerta.mostrarAlerta();

		}
	}

	/**
	 * Metodo que muestra alerta al pulsar sobre icono informacion
	 * 
	 * @param event
	 */
	@FXML
	void clickInfo(MouseEvent event) {
		Alerta alerta = new Alerta(idStackPane, "Informacion",
				"Para modificar/eliminar seleccione un tipo de hardware. Si quiere crear uno nuevo no seleccione ninguna opci�n.");
		alerta.mostrarAlerta();

	}

	/**
	 * Metodo que guarda/modifica un tipo de hardware si validar()
	 * 
	 * @param event
	 */
	@FXML
	void guardarCambios(ActionEvent event) {

		if (validar()) {

			TipoHarware nuevo;

			if (comboTipoHardware.getSelectionModel().getSelectedItem() == null
					|| comboTipoHardware.getSelectionModel().getSelectedItem().getNombre().equals("")) {
				nuevo = new TipoHarware();
			} else {
				nuevo = comboTipoHardware.getSelectionModel().getSelectedItem();
			}
			nuevo.setNombre(txtNombre.getText());
			try {
				daoTipoHardware.grabarOActualizar(nuevo);

				// Mostrar alerta
				Alerta alerta = new Alerta(idStackPane, "CREACION NUEVO TIPO",
						"Se ha creado un nuevo tipo de hardware");
				alerta.mostrarAlerta();

				// cerrar dialogo
				AjustesController.dialogo.close();

			} catch (BusinessException e) {
				VariablesEstaticas.log.logGeneral("[ERROR CREAR TIPO HARDWARE] No se ha podido CREAR: "
						+ nuevo.getNombre() + " \n\t" + e.getMessage());
			}
		}

	}

	/**
	 * Metodo que valida que el txt no est� vacio.
	 * 
	 * @return true si es todo correcto
	 */
	private boolean validar() {
		boolean resultado = true;

		// dni
		if (txtNombre.getText().isEmpty()) {
			resultado = false;
			txtNombre.setStyle("-jfx-unfocus-color: red");
			new animatefx.animation.Shake(txtNombre).play();

		} else {
			txtNombre.setStyle(null);
		}
		return resultado;
	}

	/**
	 * Metodo que controla caracteres m�ximos permitidos en un text field
	 * 
	 * @param i cantidad maxima de caracteres
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
