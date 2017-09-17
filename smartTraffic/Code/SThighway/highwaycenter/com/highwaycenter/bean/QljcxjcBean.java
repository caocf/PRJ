package com.highwaycenter.bean;

import java.sql.Timestamp;

public class QljcxjcBean implements java.io.Serializable {

	private static final long serialVersionUID = 6938497255842797907L;
	private String id;
	private String stationId;
	private String partId;
	private String sectionName;        //管养路段名称
	private String bridgeId;
	private String bridgeName;         //桥梁名称
	private String bridgePey;
	private Timestamp checkDate;
	private String maintenanceOrg;
	private String recorder;
	private String officer;
	private Timestamp nextCheckDate;
	private String judgeType;
	private String weather;
	private String facadeFileId;
	private String lateralFileId;
	
	
	
	
	
	
	public QljcxjcBean() {
		super();
	}
	
	public QljcxjcBean(String id,String sectionName, String bridgeId, String bridgeName,
			String bridgePey, Timestamp checkDate, String maintenanceOrg,
			String recorder, String officer, Timestamp nextCheckDate,
			String judgeType, String weather, String facadeFileId,
			String lateralFileId) {
		super();
		this.id = id;
		this.sectionName = sectionName;
		this.bridgeId = bridgeId;
		this.bridgeName = bridgeName;
		this.bridgePey = bridgePey;
		this.checkDate = checkDate;
		this.maintenanceOrg = maintenanceOrg;
		this.recorder = recorder;
		this.officer = officer;
		this.nextCheckDate = nextCheckDate;
		this.judgeType = judgeType;
		this.weather = weather;
		this.facadeFileId = facadeFileId;
		this.lateralFileId = lateralFileId;
	}
	
	public QljcxjcBean(String id, String stationId, String partId,
			String sectionName, String bridgeId, String bridgeName,
			String bridgePey, Timestamp checkDate, String maintenanceOrg,
			String recorder, String officer, Timestamp nextCheckDate,
			String judgeType, String weather, String facadeFileId,
			String lateralFileId) {
		super();
		this.id = id;
		this.stationId = stationId;
		this.partId = partId;
		this.sectionName = sectionName;
		this.bridgeId = bridgeId;
		this.bridgeName = bridgeName;
		this.bridgePey = bridgePey;
		this.checkDate = checkDate;
		this.maintenanceOrg = maintenanceOrg;
		this.recorder = recorder;
		this.officer = officer;
		this.nextCheckDate = nextCheckDate;
		this.judgeType = judgeType;
		this.weather = weather;
		this.facadeFileId = facadeFileId;
		this.lateralFileId = lateralFileId;
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
	public String getBridgeId() {
		return bridgeId;
	}
	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}
	public String getBridgeName() {
		return bridgeName;
	}
	public void setBridgeName(String bridgeName) {
		this.bridgeName = bridgeName;
	}
	public String getBridgePey() {
		return bridgePey;
	}
	public void setBridgePey(String bridgePey) {
		this.bridgePey = bridgePey;
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
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public String getOfficer() {
		return officer;
	}
	public void setOfficer(String officer) {
		this.officer = officer;
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
	public String getFacadeFileId() {
		return facadeFileId;
	}
	public void setFacadeFileId(String facadeFileId) {
		this.facadeFileId = facadeFileId;
	}
	public String getLateralFileId() {
		return lateralFileId;
	}
	public void setLateralFileId(String lateralFileId) {
		this.lateralFileId = lateralFileId;
	}
	
	
	
 
	
}
