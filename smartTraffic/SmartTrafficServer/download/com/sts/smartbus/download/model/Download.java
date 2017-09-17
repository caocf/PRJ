package com.sts.smartbus.download.model;

import java.sql.Date;


public class Download 
{
	private int id;
	private String ip;
	private String date;
	private String appVersion;
	private String oldVersion;
	private String equitment;
	private String imei;
	
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOldVersion() {
		return oldVersion;
	}
	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}
	public String getEquitment() {
		return equitment;
	}
	public void setEquitment(String equitment) {
		this.equitment = equitment;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
}
