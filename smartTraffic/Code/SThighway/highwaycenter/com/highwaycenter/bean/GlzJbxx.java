package com.highwaycenter.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GlzJbxx  implements java.io.Serializable {

	private static final long serialVersionUID = -1902186642572339691L;
	
	
	//基本信息
	private String stationId;               //公路站id                
	private String orgId;
	private String code;
	private String name;
	private String remark;
	private String address;
	private String telephone;
	private String fax;
	private String supervisor;
	private String email;
	private Timestamp buildDate;
	private String picId;
	private String gljgdm;               //管理机构代码
	private String gljgmc;               //管理机构名称
    //详细信息
	private String xxxxId;
	private Integer year;             //年份
	private BigDecimal bridgeCount;       //桥梁座数
	private BigDecimal tunnelCount;       //隧道座数
	private BigDecimal countryRoad;       //国道
	private BigDecimal provinceRoad;      //省道
	private BigDecimal cityRoad;          //县乡道
	
	
	public GlzJbxx(){
		
	}

	public GlzJbxx(String stationId, String orgId, String code, String name,
			String remark, String address, String telephone, String fax,
			String supervisor, String email, Timestamp buildDate, String picId,
			String xxxxId, Integer year, BigDecimal bridgeCount,
			BigDecimal tunnelCount, BigDecimal countryRoad, BigDecimal provinceRoad,
			BigDecimal cityRoad) {
		super();
		this.stationId = stationId;
		this.orgId = orgId;
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.supervisor = supervisor;
		this.email = email;
		this.buildDate = buildDate;
		this.picId = picId;
		this.xxxxId = xxxxId;
		this.year = year;
		this.bridgeCount = bridgeCount;
		this.tunnelCount = tunnelCount;
		this.countryRoad = countryRoad;
		this.provinceRoad = provinceRoad;
		this.cityRoad = cityRoad;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Timestamp buildDate) {
		this.buildDate = buildDate;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getXxxxId() {
		return xxxxId;
	}
	public void setXxxxId(String xxxxId) {
		this.xxxxId = xxxxId;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public BigDecimal getBridgeCount() {
		return bridgeCount;
	}
	public void setBridgeCount(BigDecimal bridgeCount) {
		this.bridgeCount = bridgeCount;
	}
	public BigDecimal getTunnelCount() {
		return tunnelCount;
	}
	public void setTunnelCount(BigDecimal tunnelCount) {
		this.tunnelCount = tunnelCount;
	}
	public BigDecimal getCountryRoad() {
		return countryRoad;
	}
	public void setCountryRoad(BigDecimal countryRoad) {
		this.countryRoad = countryRoad;
	}
	public BigDecimal getProvinceRoad() {
		return provinceRoad;
	}
	public void setProvinceRoad(BigDecimal provinceRoad) {
		this.provinceRoad = provinceRoad;
	}
	public BigDecimal getCityRoad() {
		return cityRoad;
	}
	public void setCityRoad(BigDecimal cityRoad) {
		this.cityRoad = cityRoad;
	}

	public String getGljgdm() {
		return gljgdm;
	}

	public void setGljgdm(String gljgdm) {
		this.gljgdm = gljgdm;
	}

	public String getGljgmc() {
		return gljgmc;
	}

	public void setGljgmc(String gljgmc) {
		this.gljgmc = gljgmc;
	}
	
	
	

}
