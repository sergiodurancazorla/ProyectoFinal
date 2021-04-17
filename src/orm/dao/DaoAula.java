package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Aula;
import utiles.dao.DaoGenericoHibernate;

public class DaoAula extends DaoGenericoHibernate<Aula, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Aula.class.getName());

}
