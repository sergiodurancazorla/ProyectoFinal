package application.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.Login;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.dao.DaoIncidencia;
import orm.dao.DaoProfesor;
import utiles.excepciones.BusinessException;
import utiles.hibernate.UtilesHibernate;

public class MainController implements Initializable {

	@FXML
	private StackPane idStackPane;

	@FXML
	private BorderPane borderPane;

	@FXML
	private ImageView btnHome;

	@FXML
	private ImageView btnAdd;

	@FXML
	private ImageView btnGraficos;

	@FXML
	private ImageView btnDocumentos;

	@FXML
	private ImageView btnUsuario;

	@FXML
	private ImageView btnCerrar;

	@FXML
	private ImageView btnAjustes;

	@FXML
	void clickAdd(MouseEvent event) throws Exception {
		limpiarEfectos();

		StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Add.fxml"));

		btnAdd.setEffect(new DropShadow(30, Color.BLACK));
		borderPane.setCenter(pane);

	}

	@FXML
	void clickAjustes(MouseEvent event) throws IOException {
		limpiarEfectos();
		btnAjustes.setEffect(new DropShadow(30, Color.BLACK));
		StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Ajustes.fxml"));
		borderPane.setCenter(pane);

	}

	@FXML
	void clickCerrar(MouseEvent event) {
		limpiarEfectos();
		// Preguntar si quiere cerrar sesión
		alertaCierreSesion();

	}

	private void alertaCierreSesion() {

		JFXAlert<String> alert = new JFXAlert<>((Stage) btnAdd.getScene().getWindow());
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setOverlayClose(false);
		alert.setResultConverter(buttonType -> {
			return null;
		});

		// Create the content of the JFXAlert with JFXDialogLayout
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label("Cierre de sesión"));
		layout.setBody(new VBox(new Label("Va a cerrar sesión, ¿estás seguro?")));

		JFXButton addButton = new JFXButton("CONFIRMAR");
		addButton.setDefaultButton(true);
		addButton.setOnAction(addEvent -> {

			UtilesHibernate.closeSession();

			Stage cerrar = (Stage) borderPane.getScene().getWindow();
			cerrar.close();

			Login login = new Login();
			Stage stage = new Stage();

			login.start(stage);

		});

		JFXButton cancelButton = new JFXButton("CANCELAR");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());

		layout.setActions(addButton, cancelButton);
		alert.setContent(layout);
		alert.showAndWait();
	}

	@FXML
	void clickDocumentos(MouseEvent event) throws IOException {
		limpiarEfectos();
		btnDocumentos.setEffect(new DropShadow(30, Color.BLACK));
		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Tabla.fxml"));
		borderPane.setCenter(pane);

	}

	@FXML
	void clickGraficos(MouseEvent event) throws IOException {
		limpiarEfectos();
		btnGraficos.setEffect(new DropShadow(30, Color.BLACK));
		StackPane pane = FXMLLoader
				.load(getClass().getClassLoader().getResource("application/vista/GraficosInformes.fxml"));
		borderPane.setCenter(pane);

	}

	@FXML
	void clickUsuario(MouseEvent event) throws IOException {
		if (VariablesEstaticas.profesor.getRol().getIdrol() == 1) {
			limpiarEfectos();
			btnUsuario.setEffect(new DropShadow(30, Color.BLACK));
			StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/User.fxml"));
			borderPane.setCenter(pane);
		} else {
			// si no es admin mostramos alerta
			Alerta alerta = new Alerta(idStackPane, "No se puede acceder",
					"No tienes permisos suficientes para acceder a la sección");
			alerta.mostrarAlerta();

		}
	}

	@FXML
	void clickHome(MouseEvent event) throws IOException {
		limpiarEfectos();
		btnHome.setEffect(new DropShadow(30, Color.BLACK));
		StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Home.fxml"));
		borderPane.setCenter(pane);

	}

	/**
	 * Método que quita los efectos de los botones
	 */
	private void limpiarEfectos() {
		btnHome.setEffect(null);
		btnAdd.setEffect(null);
		btnCerrar.setEffect(null);
		btnDocumentos.setEffect(null);
		btnGraficos.setEffect(null);
		btnAjustes.setEffect(null);
		btnUsuario.setEffect(null);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnHome.setEffect(new DropShadow(30, Color.BLACK));
		StackPane pane;
		try {
			pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Home.fxml"));
			borderPane.setCenter(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Añadir texto al hacer over
		Tooltip.install(btnHome, new Tooltip("Home"));
		Tooltip.install(btnAdd, new Tooltip("Añadir incidencia"));
		Tooltip.install(btnGraficos, new Tooltip("Graficos e informes"));
		Tooltip.install(btnDocumentos, new Tooltip("Listado de incidencias"));
		Tooltip.install(btnUsuario, new Tooltip("Listado de usuarios"));
		Tooltip.install(btnAjustes, new Tooltip("Ajustes"));
		Tooltip.install(btnCerrar, new Tooltip("Cerrar sesión"));

		// Rellenar variables estaticas
		DaoIncidencia daoIncidencia = new DaoIncidencia();
		DaoProfesor daoProfesor = new DaoProfesor();
		try {

			// listado incidencias
			VariablesEstaticas.data = FXCollections.observableArrayList(daoIncidencia.buscarTodos());

			// SAI
			VariablesEstaticas.SAI = daoProfesor.obtenerProfesor("SAI");

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

}
