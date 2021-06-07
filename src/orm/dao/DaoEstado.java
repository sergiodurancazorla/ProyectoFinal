package orm.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Estado;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoEstado extends DaoGenericoHibernate<Estado, Integer> {
	/**
	 * Metodo que devuelve una lista con los diferentes estados
	 * 
	 * @return
	 */
	public ArrayList<Estado> listadoEstados() {

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Objeto a devolver
		ArrayList lista = null;

		try {

			String hql = " from Estado e";

			Query q = s.createQuery(hql);

			lista = new ArrayList(q.list());

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return lista;
	}

}