package application.modelo;

import org.hibernate.Session;

import application.VariablesEstaticas;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utiles.hibernate.UtilesHibernate;

/**
 * Clase de partida de la aplicacion. Es la primera clase que se lanza. Desde
 * aqui se crea la pantalla login. Incluye un metodo que controla el movimiento
 * de la ventana al mantener pulsado.
 * 
 * @author Sergio Duran
 *
 */
public class Login extends Application {
	private Parent root;
	private double xOffset, yOffset;

	@Override
	public void start(Stage primaryStage) {
		try {
			// al inicio de todo, iniciar el log.
			VariablesEstaticas.log = new LogIncidencia();

			// Abrir sesion
			UtilesHibernate.openSession();
			// comenzamos sesion ;
			Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

			root = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);

			primaryStage.show();
			scene.setFill(Color.TRANSPARENT);

			// Desplazamiento de la aplicacion al mantener pulsado
			root.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				}
			});
			root.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					primaryStage.setX(event.getScreenX() - xOffset);
					primaryStage.setY(event.getScreenY() - yOffset);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
