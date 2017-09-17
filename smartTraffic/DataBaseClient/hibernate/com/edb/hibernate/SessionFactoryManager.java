package com.edb.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate SessionFactory管理器
 * 
 * @author DongJun
 * 
 */
public class SessionFactoryManager {
	private static Map<String, SessionFactory> sessions = new HashMap<String, SessionFactory>();
	private static int MAX_SESSIONFACTORYS = 64;
	
	private static String DATABASE_ADDRESS="jdbc:oracle:thin:@(description=(address_list= (address=(host=172.20.44.4) (protocol=tcp)(port=1521))(address=(host=172.20.44.5)(protocol=tcp) (port=1521)) (load_balance=yes)(failover=yes))(connect_data=(service_name=orcl)))";
	//private static String DATABASE_ADDRESS="jdbc:oracle:thin:@192.168.1.252:1521:orcl";

	public static void closeSessionFactory(String username, String password) {
		SessionFactory sessionFactory = findSessionFactory(username, password);
		if (sessionFactory != null) {
			sessionFactory.close();
			String key = username + "__" + password;
			sessions.remove(key);
		}
	}

	
	public static SessionFactory getSessionFactory(String username,
			String password) {
		if (username == null || username.equals("") || password == null
				|| password.equals("")) {
			return null;
		}

		SessionFactory sessionFactory = findSessionFactory(username, password);
		if (sessionFactory == null) {
			if (sessions.size() > MAX_SESSIONFACTORYS) {
				System.out.println("达到SessionFactory最大数量");
				return null;
			}
			try {
				Configuration config = new Configuration();
				config.setProperty("hibernate.connection.driver_class",
						"oracle.jdbc.driver.OracleDriver");
				config.setProperty("hibernate.dialect",
						"com.edb.edit.utl.DialectOracle");
				config.setProperty("hibernate.connection.url",
						DATABASE_ADDRESS);
				config.setProperty("hibernate.connection.username", username);
				config.setProperty("hibernate.connection.password", password);
				SessionFactory newSessionFactory = config.buildSessionFactory();
				if (newSessionFactory != null) {
					String key = username + "__" + password;
					sessions.put(key, newSessionFactory);
					sessionFactory = newSessionFactory;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	private static SessionFactory findSessionFactory(String username,
			String password) {
		String key = username + "__" + password;
		return sessions.get(key);
	}
}
