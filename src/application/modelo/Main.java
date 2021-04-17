package application.modelo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private double xOffset, yOffset;

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = FXMLLoader.load(getClass().getClassLoader().getResource("application/vista/Main.fxml"));

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

}
