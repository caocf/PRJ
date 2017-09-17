package com.highwaycenter.bean;

public class Glzgk implements java.io.Serializable {

	private static final long serialVersionUID = 7457938291175132935L;
	private String id;            //id
	private String stationId;    //公路站id
	private Integer year;        //年份
	private Double maintainTotal; //共养护里程
	private Double bridgeCount;  //桥梁座数
	private Double tunnelCount;  //隧道座数
	private Double greenLong;    //绿化里程
	private Double countryRoad;  //国道
	private Double provinceRoad; //省道
	private Double cityRoad;     //县乡道
	
	public Glzgk(){
		
	}
	
	
	
	public Glzgk(String id, String stationId, Integer year,
			Double maintainTotal, Double bridgeCount, Double tunnelCount,
			Double greenLong, Double countryRoad, Double provinceRoad,
			Double cityRoad) {
		super();
		this.id = id;
		this.stationId = stationId;
		this.year = year;
		this.maintainTotal = maintainTotal;
		this.bridgeCount = bridgeCount;
		this.tunnelCount = tunnelCount;
		this.greenLong = greenLong;
		this.countryRoad = countryRoad;
		this.provinceRoad = provinceRoad;
		this.cityRoad = cityRoad;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Double getMaintainTotal() {
		return maintainTotal;
	}
	public void setMaintainTotal(Double maintainTotal) {
		this.maintainTotal = maintainTotal;
	}
	public Double getBridgeCount() {
		return bridgeCount;
	}
	public void setBridgeCount(Double bridgeCount) {
		this.bridgeCount = bridgeCount;
	}
	public Double getTunnelCount() {
		return tunnelCount;
	}
	public void setTunnelCount(Double tunnelCount) {
		this.tunnelCount = tunnelCount;
	}
	public Double getGreenLong() {
		return greenLong;
	}
	public void setGreenLong(Double greenLong) {
		this.greenLong = greenLong;
	}
	public Double getCountryRoad() {
		return countryRoad;
	}
	public void setCountryRoad(Double countryRoad) {
		this.countryRoad = countryRoad;
	}
	public Double getProvinceRoad() {
		return provinceRoad;
	}
	public void setProvinceRoad(Double provinceRoad) {
		this.provinceRoad = provinceRoad;
	}
	public Double getCityRoad() {
		return cityRoad;
	}
	public void setCityRoad(Double cityRoad) {
		this.cityRoad = cityRoad;
	}
	

}
