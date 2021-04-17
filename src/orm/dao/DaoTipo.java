package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Tipo;
import utiles.dao.DaoGenericoHibernate;

public class DaoTipo extends DaoGenericoHibernate<Tipo, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Tipo.class.getName());

}
