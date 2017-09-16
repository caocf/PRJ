package com.huzhouport.log.model;

import java.sql.Timestamp;

public class Logbean implements
java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private int LogId;
	private String LogUser;
	private String LogTime;
	private String LogContent;
    private int PartOfStyle;
    private int isApp;
	
    private int StyleId ;

	private String StyleName;
	
	private String bz;
	
	public String getBz() {
		return bz==null?"":bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
 //   private QueryCondition queryCondition=new QueryCondition();

	public int getLogId() {
		return LogId;
	}
	public void setLogId(int logId) {
		this.LogId = logId;
	}
	public String getLogUser() {
		return LogUser;
	}
	public void setLogUser(String logUser) {
		LogUser = logUser;
	}
	public String getLogContent() {
		return LogContent;
	}
	public void setLogContent(String logContent) {
		LogContent = logContent;
	}
	public String getStyleName() {
		return StyleName;
	}
	public void setStyleName(String styleName) {
		StyleName = styleName; 
	}
	public String getLogTime() {
		return LogTime;
	}
	public void setLogTime(String logTime) {
		LogTime = logTime;
	}
	public int getStyleId() {
		return StyleId;
	}
	public void setStyleId(int styleId) {
		StyleId = styleId;
	}
	public int getPartOfStyle() {
		return PartOfStyle;
	}
	public void setPartOfStyle(int partOfStyle) {
		PartOfStyle = partOfStyle;
	}
	public int getIsApp() {
		return isApp;
	}
	public void setIsApp(int isApp) {
		this.isApp = isApp;
	}
	
}
