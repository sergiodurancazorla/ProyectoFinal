package orm.dao;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Tipo;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoTipo extends DaoGenericoHibernate<Tipo, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Tipo.class.getName());

	@SuppressWarnings("unchecked")
	public ArrayList<Tipo> tiposIncidencias() {

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Objeto a devolver
		ArrayList lista = null;

		try {

			String hql = " from Tipo t";

			Query q = s.createQuery(hql);

			lista = new ArrayList(q.list());

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return lista;
	}
}
