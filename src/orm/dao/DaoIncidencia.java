package orm.dao;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Departamento;
import orm.pojos.Estado;
import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoIncidencia extends DaoGenericoHibernate<Incidencia, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Incidencia.class.getName());

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

	@SuppressWarnings("rawtypes")
	public int segundosTiempoMedioIncidencias() {
		int segundos = 0;

		// obtenemos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener media tiempo resolucion
		String hql = "SELECT avg(i.tiempoResolucion) FROM Incidencia i";
		Query query = s.createQuery(hql);

		if (query.list() != null) {
			// segundos = (int) query.list().get(0);
		}

		return segundos;
	}

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
}