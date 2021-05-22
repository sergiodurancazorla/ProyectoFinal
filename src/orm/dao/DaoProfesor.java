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

	/**
	 * Si el usuario y password es correcto, da acceso a la aplicacion
	 * 
	 * @param usuario
	 * @param password
	 * @return true o false
	 */
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

	/***
	 * Metodo que devuelve un profesor segun su dni
	 * 
	 * @param dni a buscar
	 * @return el profesor
	 */
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

	/**
	 * Lista todos los profesores de la app
	 * 
	 * @return
	 */
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