package orm.dao;

import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoIncidencia extends DaoGenericoHibernate<Incidencia, Integer> {
	/**
	 * Metodo que devuelve el listado de incidencia de un profesor
	 * 
	 * @param p profesor
	 * @return listado de incidencias
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Incidencia> getIncidenciasProfesor(Profesor p) {
		ArrayList<Incidencia> lista = new ArrayList<Incidencia>();
		// obtenemos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener listado incidencia
		String hql = "SELECT i FROM Incidencia i where profesor_idprofesor=:dni";
		Query query = s.createQuery(hql);
		query.setParameter("dni", p.getDni());
		lista = (ArrayList<Incidencia>) query.list();
		return lista;
	}

	/**
	 * Metodo que devuelve el tiempo medio(en segundos) de resolución de las
	 * incidencias.
	 * 
	 * @return int segundos
	 */
	@SuppressWarnings("rawtypes")
	public int segundosTiempoMedioIncidencias() {
		int segundos = 0;

		// obtenemos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener media tiempo resolucion
		String hql = "SELECT avg(i.tiempoResolucion) FROM Incidencia i";
		Query query = s.createQuery(hql);
		try {
			if (query.getSingleResult() != null) {
				double dSegundos = (double) query.getSingleResult();
				segundos = (int) dSegundos;
			}
		} catch (NullPointerException e) {
		}
		return segundos;
	}

	/**
	 * Metodo que devuelve el listado de incidencias sin revolver de un profesor en
	 * concreto
	 * 
	 * @param p profesor
	 * @return listado
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Incidencia> getIncidenciasProfesorSinResolver(Profesor p) {
		ArrayList<Incidencia> lista = new ArrayList<Incidencia>();
		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener listado incidencia
		String hql = "SELECT i FROM Incidencia i where profesor_idprofesor=:dni and i.estado<4";
		Query query = s.createQuery(hql);
		query.setParameter("dni", p.getDni());
		lista = (ArrayList<Incidencia>) query.list();
		return lista;
	}

	/**
	 * Metodo que devuelve un entero con la cantidad de incidencias para un estado e
	 * 
	 * @param e estado
	 * @return cantidad de incidencias con ese estado
	 */

	public int cantidadIncidenciasPorEstado(Estado e) {
		int resultado = 0;
		// obtenemos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener count de estados
		String hql = "SELECT count(*) FROM Incidencia i where estado_idestado=:estado";
		Query query = s.createQuery(hql);
		query.setParameter("estado", e.getCodigo());

		// transformar resultado a int
		String txtResultado = String.valueOf(query.list().get(0));
		resultado = Integer.valueOf(txtResultado);

		return resultado;

	}

	/**
	 * Metodo que devuelve un entero con la cantidad de incidencias para un
	 * departamento
	 * 
	 * @param departamento
	 * @return cantidad de incidencias del departamento
	 */
	public int cantidadIncidenciaPorDepartamento(Departamento departamento) {
		int resultado = 0;

		// obtenemos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// obtener cantidad de incidencias por departamento.
		String hql = "SELECT count(*) FROM Incidencia i where departamento_iddepartamento=:departamento";
		Query query = s.createQuery(hql);
		query.setParameter("departamento", departamento.getCodigo());

		// transformar resultado a int
		String txtResultado = String.valueOf(query.list().get(0));
		resultado = Integer.valueOf(txtResultado);

		return resultado;
	}

	/***
	 * Metodo que devuelve dado un mes el listado de incidencias creadas en ese mes
	 * en el anyo actual.
	 * 
	 * @param mes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Incidencia> getIncidenciasMes(int mes) {

		ArrayList<Incidencia> lista = new ArrayList<Incidencia>();

		// obtenemos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener incidencias dentro de ese mes
		String hql = "SELECT i FROM Incidencia i where month(fecha_incidencia) =:mes and year(fecha_incidencia) =:anyo";

		Query query = s.createQuery(hql);
		query.setParameter("mes", mes);
		query.setParameter("anyo", Calendar.getInstance().get(Calendar.YEAR));

		lista = (ArrayList<Incidencia>) query.list();

		return lista;
	}
}