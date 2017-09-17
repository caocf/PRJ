package com.sts.weather.model;

import java.io.Serializable;
import java.util.Date;

public class Weather implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String city;
	private String province;
	private Date dataUpdateDate;
	private String temperature;
	
	private Date today;
	private String weather;
	private int picID;
	private String wind;
	
	private String info;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getDataUpdateDate() {
		return dataUpdateDate;
	}

	public void setDataUpdateDate(Date dataUpdateDate) {
		this.dataUpdateDate = dataUpdateDate;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public int getPicID() {
		return picID;
	}

	public void setPicID(int picID) {
		this.picID = picID;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
