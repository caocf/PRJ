package com.elc.transfer.modeling;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class OracleSessionFactoryManager {
	
	private static SessionFactory sessionFactory =null;
	
	private static String username="ZhjtAdmin";
	private static String password="Zhjt1234";

	private static String DATABASE_ADDRESS="jdbc:oracle:thin:@192.168.1.252:1521:orcl";

	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	public static SessionFactory getSessionFactory() {
		if (username == null || username.equals("") || password == null
				|| password.equals("")) {
			return null;
		}

		if (sessionFactory == null) {
			try {
				Configuration config = new Configuration();
				config.setProperty("hibernate.connection.driver_class",
						"oracle.jdbc.driver.OracleDriver");
				config.setProperty("hibernate.dialect",
						"org.hibernate.dialect.OracleDialect");
				config.setProperty("hibernate.connection.url",
						DATABASE_ADDRESS);
				config.setProperty("hibernate.connection.username", username);
				config.setProperty("hibernate.connection.password", password);
				SessionFactory newSessionFactory = config.buildSessionFactory();
				if (newSessionFactory != null) {
					sessionFactory = newSessionFactory;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
	
	public static  List<?> querySQL(String hql) {
		List<?> result = null;
		
		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
				
		try {
			result = session.createSQLQuery(hql).list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		} finally {
			session.clear();
			session.close();
		}
		return result;
	}
}
