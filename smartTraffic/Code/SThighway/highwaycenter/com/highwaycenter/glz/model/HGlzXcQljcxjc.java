package com.highwaycenter.glz.model;

import java.sql.Timestamp;

/**
 * HGlzXcQljcxjc entity. @author MyEclipse Persistence Tools
 */

public class HGlzXcQljcxjc implements java.io.Serializable {

	private static final long serialVersionUID = 9069713090752349584L;
	private String id;
	private String stationId;
	private String partId;
	private String bridgeId;
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

	// Constructors

	/** default constructor */
	public HGlzXcQljcxjc() {
	}

	/** minimal constructor */
	public HGlzXcQljcxjc(String id, String stationId, String partId,
			String bridgeId, Timestamp checkDate, String maintenanceOrg) {
		this.id = id;
		this.stationId = stationId;
		this.partId = partId;
		this.bridgeId = bridgeId;
		this.checkDate = checkDate;
		this.maintenanceOrg = maintenanceOrg;
	}

	/** full constructor */
	public HGlzXcQljcxjc(String id, String stationId, String partId,
			String bridgeId, String bridgePey, Timestamp checkDate,
			String maintenanceOrg, String recorder, String officer,
			Timestamp nextCheckDate, String judgeType, String weather,
			String facadeFileId, String lateralFileId) {
		this.id = id;
		this.stationId = stationId;
		this.partId = partId;
		this.bridgeId = bridgeId;
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

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getPartId() {
		return this.partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getBridgeId() {
		return this.bridgeId;
	}

	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}

	public String getBridgePey() {
		return this.bridgePey;
	}

	public void setBridgePey(String bridgePey) {
		this.bridgePey = bridgePey;
	}

	public Timestamp getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}

	public String getMaintenanceOrg() {
		return this.maintenanceOrg;
	}

	public void setMaintenanceOrg(String maintenanceOrg) {
		this.maintenanceOrg = maintenanceOrg;
	}

	public String getRecorder() {
		return this.recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public String getOfficer() {
		return this.officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	public Timestamp getNextCheckDate() {
		return this.nextCheckDate;
	}

	public void setNextCheckDate(Timestamp nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}

	public String getJudgeType() {
		return this.judgeType;
	}

	public void setJudgeType(String judgeType) {
		this.judgeType = judgeType;
	}

	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getFacadeFileId() {
		return this.facadeFileId;
	}

	public void setFacadeFileId(String facadeFileId) {
		this.facadeFileId = facadeFileId;
	}

	public String getLateralFileId() {
		return this.lateralFileId;
	}

	public void setLateralFileId(String lateralFileId) {
		this.lateralFileId = lateralFileId;
	}

}