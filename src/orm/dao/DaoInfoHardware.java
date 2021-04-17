package orm.dao;

import java.util.logging.Logger;

import orm.pojos.InfoHardware;
import utiles.dao.DaoGenericoHibernate;

public class DaoInfoHardware extends DaoGenericoHibernate<InfoHardware, Integer> {
	private final static Logger LOGGER = Logger.getLogger(InfoHardware.class.getName());

}