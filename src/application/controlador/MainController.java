package application.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.VariablesEstaticas;
import application.modelo.Alerta;
import application.modelo.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
	void clickAjustes(MouseEvent event) {
		limpiarEfectos();
		btnAjustes.setEffect(new DropShadow(30, Color.BLACK));

	}

	@FXML
	void clickCerrar(MouseEvent event) {
		limpiarEfectos();

		UtilesHibernate.closeSession();

		Stage cerrar = (Stage) borderPane.getScene().getWindow();
		cerrar.close();

		Login login = new Login();
		Stage stage = new Stage();

		login.start(stage);

	}

	@FXML
	void clickDocumentos(MouseEvent event) throws IOException {
		limpiarEfectos();
		btnDocumentos.setEffect(new DropShadow(30, Color.BLACK));
		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Tabla.fxml"));
		borderPane.setCenter(pane);

	}

	@FXML
	void clickGraficos(MouseEvent event) {
		limpiarEfectos();
		btnGraficos.setEffect(new DropShadow(30, Color.BLACK));

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
					"No tienes permisos suficientes para acceder a la secci�n");
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
	 * M�todo que quita los efectos de los botones
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
	}

}
