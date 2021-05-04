package application.controlador;

import java.io.IOException;

import application.modelo.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utiles.hibernate.UtilesHibernate;

public class MainController {
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

		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Add.fxml"));

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
		// PONER ALERTA DE ¿SEGURO QUE QUIERES CERRAR SESION?
		limpiarEfectos();

		UtilesHibernate.closeSession();

		Stage cerrar = (Stage) borderPane.getScene().getWindow();
		cerrar.close();

		Login login = new Login();
		Stage stage = new Stage();

		login.start(stage);

	}

	@FXML
	void clickDocumentos(MouseEvent event) {
		limpiarEfectos();
		btnDocumentos.setEffect(new DropShadow(30, Color.BLACK));

	}

	@FXML
	void clickGraficos(MouseEvent event) {
		limpiarEfectos();
		btnGraficos.setEffect(new DropShadow(30, Color.BLACK));

	}

	@FXML
	void clickUsuario(MouseEvent event) {
		limpiarEfectos();
		btnUsuario.setEffect(new DropShadow(30, Color.BLACK));

	}

	@FXML
	void clickHome(MouseEvent event) throws IOException {
		limpiarEfectos();
		btnHome.setEffect(new DropShadow(30, Color.BLACK));

//		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/PRUEBA.fxml"));
//		borderPane.setCenter(pane);
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

}
