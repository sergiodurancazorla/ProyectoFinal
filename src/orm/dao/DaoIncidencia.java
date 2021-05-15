package orm.dao;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Incidencia;
import orm.pojos.Profesor;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoIncidencia extends DaoGenericoHibernate<Incidencia, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Incidencia.class.getName());

	@SuppressWarnings("unchecked")
	public ArrayList<Incidencia> getIncidenciasProfesor(Profesor p) {
		ArrayList<Incidencia> lista = new ArrayList<Incidencia>();
		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener listado incidencia
		String hql = "SELECT i FROM Incidencia i where profesor_idprofesor=:dni";
		Query query = s.createQuery(hql);
		query.setParameter("dni", p.getDni());
		lista = (ArrayList<Incidencia>) query.list();

		return lista;
	}

}