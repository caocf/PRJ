package com.highwaycenter.bean;

import java.sql.Timestamp;

public class HdjcxjcBean {
	private String id;
	private String stationId;
	private String partId; 
	private String sectionName;   //路段名称
	private String culverId;
	private String culverCode;    //涵洞名称
	private Timestamp checkDate;
	private String maintenanceOrg;
	private Timestamp nextCheckDate;
	private String judgeType;
	private String weather;
	private String culverPeg;
	private String culverType;
	
	public HdjcxjcBean(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getCulverId() {
		return culverId;
	}
	public void setCulverId(String culverId) {
		this.culverId = culverId;
	}
	public String getCulverCode() {
		return culverCode;
	}
	public void setCulverCode(String culverCode) {
		this.culverCode = culverCode;
	}
	public Timestamp getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}
	public String getMaintenanceOrg() {
		return maintenanceOrg;
	}
	public void setMaintenanceOrg(String maintenanceOrg) {
		this.maintenanceOrg = maintenanceOrg;
	}
	public Timestamp getNextCheckDate() {
		return nextCheckDate;
	}
	public void setNextCheckDate(Timestamp nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}
	public String getJudgeType() {
		return judgeType;
	}
	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getCulverPeg() {
		return culverPeg;
	}
	public void setCulverPeg(String culverPeg) {
		this.culverPeg = culverPeg;
	}
	public String getCulverType() {
		return culverType;
	}
	public void setCulverType(String culverType) {
		this.culverType = culverType;
	}

	

}
