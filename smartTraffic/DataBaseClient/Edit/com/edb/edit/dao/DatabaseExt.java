package com.edb.edit.dao;

import org.apache.commons.dbcp.BasicDataSource;

public class DatabaseExt extends BasicDataSource {
	String name;
	String pass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public DatabaseExt(String name, String pass) {
		this.name = name;
		this.pass = pass;

		setUsername(name);
		setPassword(pass);
	}
}
