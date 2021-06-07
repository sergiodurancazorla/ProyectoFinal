package orm.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Aula;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoAula extends DaoGenericoHibernate<Aula, Integer> {
	/**
	 * Metodo que devuelve una lista con las diferentes aulas
	 * 
	 * @return
	 */
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
