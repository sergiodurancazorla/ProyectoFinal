package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Estado;
import utiles.dao.DaoGenericoHibernate;

public class DaoEstado extends DaoGenericoHibernate<Estado, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Estado.class.getName());

}