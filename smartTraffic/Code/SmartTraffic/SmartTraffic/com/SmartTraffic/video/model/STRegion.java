package com.SmartTraffic.video.model;

public class STRegion implements java.io.Serializable{

	private static final long serialVersionUID = -5424865817527900202L;
	
	private int regionId;//区域ID
	private String regionName;//区域名称
	private int regionHigh;//上级区域ID
	private int regionLevel ;//区域等级
	private int regionCellLsh;//区域所属控制中心ID
	
	public STRegion(){
		super();
	}
	
	public STRegion(int regionId, String regionName, int regionHigh,
			int regionLevel, int regionCellLsh) {
		super();
		this.regionId = regionId;
		this.regionName = regionName;
		this.regionHigh = regionHigh;
		this.regionLevel = regionLevel;
		this.regionCellLsh = regionCellLsh;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getRegionHigh() {
		return regionHigh;
	}
	public void setRegionHigh(int regionHigh) {
		this.regionHigh = regionHigh;
	}
	public int getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(int regionLevel) {
		this.regionLevel = regionLevel;
	}
	public int getRegionCellLsh() {
		return regionCellLsh;
	}
	public void setRegionCellLsh(int regionCellLsh) {
		this.regionCellLsh = regionCellLsh;
	}
	
	
	

}
