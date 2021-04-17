package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Permiso;
import utiles.dao.DaoGenericoHibernate;

public class DaoPermiso extends DaoGenericoHibernate<Permiso, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Permiso.class.getName());

}
