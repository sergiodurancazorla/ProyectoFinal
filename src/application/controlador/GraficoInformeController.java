package application.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

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

/**
 * Controlador de la vista Graficos. En esta clase se implementa el código para
 * que se muestren los graficos.
 * 
 * @author Sergio Duran
 *
 */
public class GraficoInformeController implements Initializable {

	// FXML
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

	// ATRIBUTOS PRIVADOS
	private ObservableList<Incidencia> data;
	private Profesor profesor;
	private ObservableList<PieChart.Data> dataEstado;
	private ObservableList<PieChart.Data> dataDepartamento;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			// Se obtienen todos las incidencias de la app
			DaoIncidencia daoIncidencia = new DaoIncidencia();
			this.data = FXCollections.observableArrayList(daoIncidencia.buscarTodos());

			// Se obtienne informacion del profesor logueado
			this.profesor = VariablesEstaticas.profesor;
			cargarTextos();
			cargarPieChart();
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que genera los graficos de la incidencias por Estado y Departamento
	 * 
	 * @throws BusinessException
	 */
	private void cargarPieChart() throws BusinessException {
		// ******************ESTADOS*********************************
		dataEstado = FXCollections.observableArrayList();

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
		// ***************** DEPARTAMENTOS*************************
		dataDepartamento = FXCollections.observableArrayList();

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

	/**
	 * Metodo que rellena los textos de las diferentes pestañas relativas a
	 * incidencias totales, mis incidencias, resueltas, tiempo medio.
	 */
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

	/**
	 * Metodo que dado un tiempo medio en segundos calcula los dias, horas, minutos
	 * y segundos.
	 * 
	 * @param segundosTiempoMedioIncidencias
	 * @return
	 */
	private String transformarTiempoMedio(int segundosTiempoMedioIncidencias) {
		String texto = " ";

		// Si no hay ninguna incidencia resuelta
		if (segundosTiempoMedioIncidencias == 0) {
			texto = "Incidencias\nsin\nresolver";
			txtTiempoMedio.autosize();

		} else {

			// Formato del periodo. dia, horas , minutos y segundos.
			PeriodFormatter dhm = new PeriodFormatterBuilder().appendDays().appendSuffix(" dia", " dias")
					.appendSeparator("\n").appendHours().appendSuffix(" hora", " horas").appendSeparator("\n")
					.appendMinutes().appendSuffix(" minuto", " minutos").appendSeparator("\n").appendSeconds()
					.appendSuffix(" segundo", " segundos").toFormatter();

			// Calculamos el periodo en funcion de los segundos
			Seconds segundos = Seconds.seconds(segundosTiempoMedioIncidencias);
			Period p1 = new Period(segundos);

			// Guardamos en variable el string con el formato
			texto = dhm.print(p1.normalizedStandard());

		}
		return texto;
	}

}
