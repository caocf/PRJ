package com.huzhouport.download.model;

import java.util.Date;

public class Download 
{
	private int id;
	private int appId;
	private String ip;
	private Date date;
	private String appVersion;
	
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
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
