package com.edb.edit.utl;

import org.hibernate.dialect.OracleDialect;

public class DialectOracle extends OracleDialect 
{
	public DialectOracle()
	{
        super.registerColumnType(-8, "string");
        super.registerHibernateType(-8, "string");  
	}
}
