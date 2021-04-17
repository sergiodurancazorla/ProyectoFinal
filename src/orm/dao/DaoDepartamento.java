package orm.dao;

import java.util.logging.Logger;

import orm.pojos.Departamento;
import utiles.dao.DaoGenericoHibernate;

public class DaoDepartamento extends DaoGenericoHibernate<Departamento, Integer> {
	private final static Logger LOGGER = Logger.getLogger(Departamento.class.getName());

}