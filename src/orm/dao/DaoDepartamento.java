package orm.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Departamento;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoDepartamento extends DaoGenericoHibernate<Departamento, Integer> {
	/**
	 * Metodo que devuelve una lista con los diferentes departamentos
	 * 
	 * @return
	 */
	public ArrayList<Departamento> listadoDepartamentos() {

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Objeto a devolver
		ArrayList lista = null;

		try {

			String hql = " from Departamento d";

			Query q = s.createQuery(hql);

			lista = (ArrayList) q.list();

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return lista;
	}

}