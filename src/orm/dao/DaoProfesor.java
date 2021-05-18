package orm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import orm.pojos.Profesor;
import utiles.dao.DaoGenericoHibernate;
import utiles.hibernate.UtilesHibernate;

public class DaoProfesor extends DaoGenericoHibernate<Profesor, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Profesor.class.getName());

	@SuppressWarnings("unchecked")
	public Boolean login(String usuario, String password) {

		Boolean correcto = false;

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();
		// Obtener usuarios
		String hql = "SELECT p.dni, p.password FROM Profesor p";
		Query query = s.createQuery(hql);
		List<Object[]> lista;

		lista = query.list();

		// Comprobar que coincide
		for (int i = 0; i < lista.size() && !correcto; i++) {
			Object[] comprobar = lista.get(i);
			if (comprobar[0].equals(usuario) && comprobar[1].equals(password)) {
				correcto = true;
			}
		}

		return correcto;

	}

	public Profesor obtenerProfesor(String dni) {
		Profesor resultado = new Profesor();

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Obtener usuarios
		String hql = "SELECT p FROM Profesor p where dni=:dni";
		Query query = s.createQuery(hql);
		query.setParameter("dni", dni);
		List<Profesor> lista;

		lista = query.list();
		resultado = lista.get(0);

		return resultado;

	}

	public ArrayList<Profesor> listadoProfesores() {

		// comenzamos sesion
		Session s = UtilesHibernate.getSessionFactory().getCurrentSession();

		// Objeto a devolver
		ArrayList lista = null;

		try {

			String hql = " from Profesor p";

			Query q = s.createQuery(hql);

			lista = new ArrayList(q.list());

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		return lista;
	}

}