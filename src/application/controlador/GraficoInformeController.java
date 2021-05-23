package application.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.VariablesEstaticas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import orm.dao.DaoDepartamento;
import orm.dao.DaoEstado;
import orm.dao.DaoIncidencia;
import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import utiles.excepciones.BusinessException;

public class GraficoInformeController implements Initializable {

	@FXML
	private AnchorPane idAnchorPane;

	@FXML
	private Label txtTotalIncideas;

	@FXML
	private Label txtTiempoMedio;

	@FXML
	private Label txtMisIncidencias;

	@FXML
	private Label txtSinResolver;

	@FXML
	private PieChart chartEstado;

	@FXML
	private PieChart chartDepartamento;

	@FXML
	private Label txtCantidadGrafico;

	private ObservableList<Incidencia> data;
	private Profesor profesor;
	private ObservableList<PieChart.Data> dataEstado;
	private ObservableList<PieChart.Data> dataDepartamento;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			this.data = VariablesEstaticas.data;
			this.profesor = VariablesEstaticas.profesor;
			dataEstado = FXCollections.observableArrayList();
			dataDepartamento = FXCollections.observableArrayList();
			cargarTextos();
			cargarPieChart();
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	private void cargarPieChart() throws BusinessException {
		// ESTADOS*********************************

		// ¿Cuantos estados hay?
		DaoEstado daoEstado = new DaoEstado();
		DaoIncidencia daoIncidencia = new DaoIncidencia();
		List<Estado> listaEstados = daoEstado.buscarTodos();

		// Para cada estado¿cuantas incidencias hay?

		for (int i = 0; i < listaEstados.size(); i++) {
			String estado = listaEstados.get(i).getNombre();
			int cantidad = daoIncidencia.cantidadIncidenciasPorEstado(listaEstados.get(i));
			dataEstado.add(new PieChart.Data(estado, cantidad));
		}

		// caracteristicas chart estado
		chartEstado.setData(dataEstado);
		chartEstado.setTitle("Incidencias por estado");

		// Al pulsar sobre el grafico se muestra la cantidad
		for (PieChart.Data data : chartEstado.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
					new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							// ajustar donde mostrar label
							txtCantidadGrafico.setTranslateX(event.getX() + 100);
							txtCantidadGrafico.setTranslateY(event.getY() + 15);
							String cantidad = String.valueOf(data.getPieValue());
							txtCantidadGrafico.setText(cantidad.substring(0, cantidad.length() - 2));
						}

					});

		}
		// DEPARTAMENTOS*************************

		// ¿Cuantos departamentos hay?
		DaoDepartamento daoDepartamento = new DaoDepartamento();
		List<Departamento> listaDepartamentos = daoDepartamento.buscarTodos();

		// Para cada departamento, ¿cuantas incidencias?
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			String nombre = listaDepartamentos.get(i).getNombre();
			int cantidad = daoIncidencia.cantidadIncidenciaPorDepartamento(listaDepartamentos.get(i));
			dataDepartamento.add(new PieChart.Data(nombre, cantidad));
		}

		// caracteristicas chart departamento
		chartDepartamento.setData(dataDepartamento);
		chartDepartamento.setTitle("Incidencias por departamento");

		// Al pulsar sobre el grafico se muestra la cantidad
		for (PieChart.Data data : chartDepartamento.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
					new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							// ajustar donde mostrar label
							txtCantidadGrafico.setTranslateX(event.getX() + 520);
							txtCantidadGrafico.setTranslateY(event.getY() + 15);
							String cantidad = String.valueOf(data.getPieValue());
							txtCantidadGrafico.setText(cantidad.substring(0, cantidad.length() - 2));

						}

					});

		}
	}

	private void cargarTextos() {
		DaoIncidencia daoIncidencia = new DaoIncidencia();

		// Total incidencias
		txtTotalIncideas.setText(String.valueOf(data.size()));

		// Tiempo medio
		String tiempoMedio = transformarTiempoMedio(daoIncidencia.segundosTiempoMedioIncidencias());
		txtTiempoMedio.setText(tiempoMedio);

		// Mis incidencias
		txtMisIncidencias.setText(String.valueOf(daoIncidencia.getIncidenciasProfesor(profesor).size()));

		// Mis incidencias sin resolver
		txtSinResolver.setText(String.valueOf(daoIncidencia.getIncidenciasProfesorSinResolver(profesor).size()));

	}

	private String transformarTiempoMedio(int segundosTiempoMedioIncidencias) {
		String texto = "";

		if (segundosTiempoMedioIncidencias == 0) {
			texto = "Incidencias\nsin\nresolver";
			txtTiempoMedio.autosize();

		} else {
			// Calcular string en funcion de los segundos***********************
			int resto = 0;
			if (segundosTiempoMedioIncidencias % 60 != 0) {

			}

		}
		return texto;
	}

}
