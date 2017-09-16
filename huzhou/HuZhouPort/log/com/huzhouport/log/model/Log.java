package com.huzhouport.log.model;



public class Log implements java.io.Serializable {

	   private static final long serialVersionUID = 1L;

	private int logId;
	private String logUser;
	private String logTime;
	private String logContent;
	private int partOfStyle;
	private int isApp;
	
	private String bz;
	
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getLogUser() {
		return logUser;
	}
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public int getPartOfStyle() {
		return partOfStyle;
	}
	public void setPartOfStyle(int partOfStyle) {
		this.partOfStyle = partOfStyle;
	}
	public int getIsApp() {
		return isApp;
	}
	public void setIsApp(int isApp) {
		this.isApp = isApp;
	}

	

}