package application.modelo;

import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.controlador.EditarIncidenciaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;

public class DialogoEditar {
	private Incidencia incidencia;
	private Profesor profesor;
	private static JFXDialog dialogo;

	/**
	 * Muestra el dialogo de editar incidencia
	 * 
	 * @param i
	 */
	public DialogoEditar(Incidencia i) {
		this.profesor = null;
		this.incidencia = i;
	}

	/**
	 * Muestra el dialogo de editar profesor
	 * 
	 * @param profesor
	 */
	public DialogoEditar(Profesor profesor) {
		this.incidencia = null;

		this.profesor = profesor;
	}

	public void mostrarDialogo(StackPane stackPane) throws IOException {

		if (incidencia != null) {

			URL fxmlLocation = getClass().getClassLoader().getResource("application/vista/EditarIncidencia.fxml");
			FXMLLoader loader = new FXMLLoader(fxmlLocation);
			EditarIncidenciaController controller = new EditarIncidenciaController();
			controller.addIncidencia(incidencia);
			loader.setController(controller);

			Parent panel = loader.load();

			JFXDialogLayout layout = new JFXDialogLayout();

			layout.setHeading(new Text("Editar incidencia"));
			layout.setBody(panel);
			dialogo = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
			dialogo.show();

		} else if (profesor != null) {

		}

	}

	static public void cerrarDialogo() {
		dialogo.close();
	}

}
