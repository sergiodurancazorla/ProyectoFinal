package application.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import orm.dao.DaoInfoHardware;
import orm.pojos.TipoHarware;

public class CrearModificarTipoHardwareController implements Initializable {

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

		// añadir listener al combo
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
					// se ha seleccionado un
					btnEliminar.setVisible(true);
					txtNombre.setText(newValue.getNombre());
					btnConfirmar.setText("Modificar");
				}

			}

		});

	}

	@FXML
	void btnCancelar(ActionEvent event) {

	}

	@FXML
	void clickEliminar(ActionEvent event) {

	}

	@FXML
	void clickInfo(MouseEvent event) {

	}

	@FXML
	void guardarCambios(ActionEvent event) {

	}

}
