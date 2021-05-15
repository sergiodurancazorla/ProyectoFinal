package application.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableView;

import application.modelo.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import orm.pojos.RolPermiso;

public class HomeController implements Initializable {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private JFXTreeTableView<Incidencia> tablaIncidencias;

	@FXML
	private Label txtNombreSuperior;

	@FXML
	private Label txtNombre;

	@FXML
	private Label txtEmail;

	@FXML
	private Label txtDepartamento;

	@FXML
	private Label txtTotalIncidencias;

	@FXML
	private JFXCheckBox checkAdd;

	@FXML
	private JFXCheckBox checkModificarIncidencia;

	@FXML
	private JFXCheckBox checkModificarTipos;

	@FXML
	private JFXCheckBox checkRoles;

	@FXML
	private JFXCheckBox checkImportacion;

	@FXML
	private JFXCheckBox checkInformes;

	// MODIFICAR************************************************************
	private int totalIncidencias = 5;
	private Profesor profesor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		profesor = Main.profesor;

		iniciarTabla();

		txtNombreSuperior.setText(profesor.toString());
		txtNombre.setText(profesor.toString());
		txtEmail.setText(profesor.getEmail());
		txtDepartamento.setText(profesor.getDepartamento().getNombre());
		txtTotalIncidencias.setText("Total de incidencias creadas: " + totalIncidencias);

		rellenarChecks();

	}

	/**
	 * Metodo que rellena los checks en funcion del rol y los permisos que tengas
	 * asignados
	 */

	private void rellenarChecks() {
		ArrayList<RolPermiso> listaPermisos = new ArrayList<RolPermiso>();
		listaPermisos.addAll(profesor.getRol().getRolPermisos());
		for (int i = 0; i < listaPermisos.size(); i++) {

			switch (listaPermisos.get(i).getPermiso().getIdpermiso()) {
			case 1:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkAdd.setSelected(true);
				break;
			case 2:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkModificarIncidencia.setSelected(true);
				break;
			case 3:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkModificarTipos.setSelected(true);
				break;
			case 4:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkRoles.setSelected(true);
				break;
			case 5:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkImportacion.setSelected(true);
				break;
			case 6:
				if (listaPermisos.get(i).getBoolean_() == 1)
					checkInformes.setSelected(true);
				break;

			default:
				break;
			}

		}

	}

	private void iniciarTabla() {
		// TODO Auto-generated method stub

	}

}
