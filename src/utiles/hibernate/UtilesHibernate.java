package utiles.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;

public class UtilesHibernate {
	private static final SessionFactory sessionFactory;
	static {
		try {

			sessionFactory = new Configuration().configure().buildSessionFactory();

		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void openSession() {
		Session session = sessionFactory.openSession();
		ThreadLocalSessionContext.bind(session);
	}

	public static void closeSession() {
		Session session = ThreadLocalSessionContext.unbind(sessionFactory);
		if (session != null) {
			session.close();
		}

	}

	public static void closeSessionFactory() {
		if ((sessionFactory != null) && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}

}