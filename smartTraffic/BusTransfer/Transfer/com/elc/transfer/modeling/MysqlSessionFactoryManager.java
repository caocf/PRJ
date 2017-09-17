package com.elc.transfer.modeling;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MysqlSessionFactoryManager {
	
	private static SessionFactory sessionFactory =null;
	
	private static String username="root";
	private static String password="123456";

	private static String DATABASE_ADDRESS="jdbc:mysql://192.168.1.200:3306/bustransfer";

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
						"com.mysql.jdbc.Driver");
				config.setProperty("hibernate.dialect",
						"org.hibernate.dialect.MySQLDialect");
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
		
		try {
			result = session.createSQLQuery(hql).list();
		} catch (Exception e) {
			System.out.println(e);
		} 
		return result;
	}
	
	public static Session session;
	public static Transaction tx;
	
	public static void openSession()
	{
		session = getSessionFactory().openSession();
		tx = session.beginTransaction();
	}
	public static void closeSession()
	{
		try
		{
			tx.commit();
		}
		catch(Exception e)
		{
			tx.rollback();
		}
		session.clear();
		session.close();
	}
	
	public static void updateSQL(String sql) {

		try {
			session.createSQLQuery(sql).executeUpdate();
			
		} catch (Exception e) {
			
			System.out.println(e);
		}
	}
}
