package com.huzhouport.common.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;



public class MySql5Dialect extends MySQL5Dialect{
	public MySql5Dialect(){
		super();
		registerHibernateType(7, Hibernate.FLOAT.getName());
		registerHibernateType(-1, Hibernate.STRING.getName());
		//registerHibernateType(Types.DATE, Hibernate.DATE.getName());
		registerHibernateType(Types.TIMESTAMP, Hibernate.TIMESTAMP.getName());
		registerHibernateType(Types.DOUBLE, Hibernate.DOUBLE.getName());
		registerHibernateType(Types.BLOB, Hibernate.BLOB.getName());
	}
}
