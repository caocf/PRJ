package com.sts.parking.model;

public class Parking implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String parkType;	//否	停车场类型
	private String regionCode;	//	是	所属行政区划
	private String chargeWay;	//	否	收费方式
	private String Pno;	//	是	开始位置
	private String coId;	//	否	所在坐标系列，默认为天地图坐标
	private String pageSize;	//	是	每页显示的条数
	public String getParkType() {
		return parkType;
	}
	public void setParkType(String parkType) {
		this.parkType = parkType;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getChargeWay() {
		return chargeWay;
	}
	public void setChargeWay(String chargeWay) {
		this.chargeWay = chargeWay;
	}
	public String getPno() {
		return Pno;
	}
	public void setPno(String pno) {
		Pno = pno;
	}
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}


}
