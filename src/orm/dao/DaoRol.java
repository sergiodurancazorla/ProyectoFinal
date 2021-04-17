package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Rol;
import utiles.dao.DaoGenericoHibernate;

public class DaoRol extends DaoGenericoHibernate<Rol, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Rol.class.getName());

}
