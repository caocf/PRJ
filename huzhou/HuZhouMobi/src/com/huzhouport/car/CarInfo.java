package com.huzhouport.car;

import java.io.Serializable;



public class CarInfo implements Serializable
{
	int id;
	String name;
	String driver;
	String tel;
	int capcity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getCapcity() {
		return capcity;
	}
	public void setCapcity(int capcity) {
		this.capcity = capcity;
	}

}
