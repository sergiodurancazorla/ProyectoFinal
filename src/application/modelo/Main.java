package application.modelo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import orm.pojos.Profesor;

public class Main extends Application {
	private double xOffset, yOffset;
	/**
	 * Variable que guarda el profesor que ha inciado sesión
	 */
	public static Profesor profesor;

	public Main(Profesor profesor) {
		Main.profesor = profesor;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		StackPane root = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Main.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

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

	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

}
