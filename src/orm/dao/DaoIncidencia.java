package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Incidencia;
import utiles.dao.DaoGenericoHibernate;

public class DaoIncidencia extends DaoGenericoHibernate<Incidencia, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Incidencia.class.getName());

}