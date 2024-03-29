package orm.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Incidencia;
import orm.pojos.InfoHardware;
import orm.pojos.TipoHarware;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoInfoHardware extends DaoGenericoHibernate<InfoHardware, Integer> {
	/**
	 * Metodo que devuelve listado de los diferentes tipos de Hardware
	 * 
	 * @return
	 */
	public ArrayList<TipoHarware> listadoTiposHw() {

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Objeto a devolver
		ArrayList lista = null;

		try {

			String hql = " from TipoHarware t";

			Query q = s.createQuery(hql);

			lista = new ArrayList(q.list());

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return lista;
	}
/**
 * Metodo que devuelve la información del hardware de una incidencia en concreto
 * @param i
 * @return
 */
	public InfoHardware informacionIncidencia(Incidencia i) {

		InfoHardware informacion = new InfoHardware();

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();
		String hql = "SELECT i FROM InfoHardware i where id_incidencia=:id";
		Query query = s.createQuery(hql);
		query.setParameter("id", i.getIdincidencia());

		informacion = (InfoHardware) query.uniqueResult();

		return informacion;

	}

}