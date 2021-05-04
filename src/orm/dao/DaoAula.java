package orm.dao;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Aula;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoAula extends DaoGenericoHibernate<Aula, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Aula.class.getName());

	public ArrayList<Aula> listadoAulas() {

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Objeto a devolver
		ArrayList lista = null;

		try {

			String hql = " from Aula a";

			Query q = s.createQuery(hql);

			lista = new ArrayList(q.list());

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return lista;
	}

}
