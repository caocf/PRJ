package com.highwaycenter.glz.model;

import java.sql.Timestamp;

/**
 * HGlzXcHdjcxjc entity. @author MyEclipse Persistence Tools
 */

public class HGlzXcHdjcxjc implements java.io.Serializable {

	private static final long serialVersionUID = -6345239922972963417L;
	private String id;
	private String stationId;
	private String partId;
	private String culverId;
	private Timestamp checkDate;
	private String maintenanceOrg;
	private Timestamp nextCheckDate;
	private String judgeType;
	private String weather;
	private String culverPeg;
	private String culverType;

	// Constructors

	/** default constructor */
	public HGlzXcHdjcxjc() {
	}

	/** minimal constructor */
	public HGlzXcHdjcxjc(String id, String stationId, String partId,
			String culverId, Timestamp checkDate, String maintenanceOrg) {
		this.id = id;
		this.stationId = stationId;
		this.partId = partId;
		this.culverId = culverId;
		this.checkDate = checkDate;
		this.maintenanceOrg = maintenanceOrg;
	}

	/** full constructor */
	public HGlzXcHdjcxjc(String id, String stationId, String partId,
			String culverId, Timestamp checkDate, String maintenanceOrg,
			Timestamp nextCheckDate, String judgeType, String weather,
			String culverPeg, String culverType) {
		this.id = id;
		this.stationId = stationId;
		this.partId = partId;
		this.culverId = culverId;
		this.checkDate = checkDate;
		this.maintenanceOrg = maintenanceOrg;
		this.nextCheckDate = nextCheckDate;
		this.judgeType = judgeType;
		this.weather = weather;
		this.culverPeg = culverPeg;
		this.culverType = culverType;
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

	public String getCulverId() {
		return this.culverId;
	}

	public void setCulverId(String culverId) {
		this.culverId = culverId;
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

	public String getCulverPeg() {
		return this.culverPeg;
	}

	public void setCulverPeg(String culverPeg) {
		this.culverPeg = culverPeg;
	}

	public String getCulverType() {
		return this.culverType;
	}

	public void setCulverType(String culverType) {
		this.culverType = culverType;
	}

}