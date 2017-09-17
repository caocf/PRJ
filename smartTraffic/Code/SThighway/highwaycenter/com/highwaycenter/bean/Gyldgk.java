package com.highwaycenter.bean;

public class Gyldgk implements java.io.Serializable { //管养路段概况

	private static final long serialVersionUID = 9187628552699719876L;
	
	private String id;
	private String code;
	private String type;
	private String stationId;
	private String districtId;
	private String sectionName;
	private String startStake;
	private Double startStake2;
	private String startStakeName;
	private String endStake;
	private Double endStake2;
	private String endStakeName;
	private Double manageLength;    //管养里程
	
	
	public Gyldgk(){
		super();
	}
	
	
	public Gyldgk(String id, String code, String type, String stationId,
			String districtId, String sectionName, String startStake,
			Double startStake2, String startStakeName, String endStake,
			Double endStake2, String endStakeName, Double manageLength) {
		super();
		this.id = id;
		this.code = code;
		this.type = type;
		this.stationId = stationId;
		this.districtId = districtId;
		this.sectionName = sectionName;
		this.startStake = startStake;
		this.startStake2 = startStake2;
		this.startStakeName = startStakeName;
		this.endStake = endStake;
		this.endStake2 = endStake2;
		this.endStakeName = endStakeName;
		this.manageLength = manageLength;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getStartStake() {
		return startStake;
	}
	public void setStartStake(String startStake) {
		this.startStake = startStake;
	}
	public Double getStartStake2() {
		return startStake2;
	}
	public void setStartStake2(Double startStake2) {
		this.startStake2 = startStake2;
	}
	public String getStartStakeName() {
		return startStakeName;
	}
	public void setStartStakeName(String startStakeName) {
		this.startStakeName = startStakeName;
	}
	public String getEndStake() {
		return endStake;
	}
	public void setEndStake(String endStake) {
		this.endStake = endStake;
	}
	public Double getEndStake2() {
		return endStake2;
	}
	public void setEndStake2(Double endStake2) {
		this.endStake2 = endStake2;
	}
	public String getEndStakeName() {
		return endStakeName;
	}
	public void setEndStakeName(String endStakeName) {
		this.endStakeName = endStakeName;
	}
	public Double getManageLength() {
		return manageLength;
	}
	public void setManageLength(Double manageLength) {
		this.manageLength = manageLength;
	}
	

}
