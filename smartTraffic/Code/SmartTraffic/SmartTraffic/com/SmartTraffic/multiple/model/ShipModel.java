package com.SmartTraffic.multiple.model;

import org.apache.struts2.json.annotations.JSON;

public class ShipModel {
	private String shipname;
	private String shipdirection;
	private String cruisedirection;
	private Float speed;
	private String adddate;
	private Double longitude;
	private Double latitude;
	private int shiptype;
	public String getShipname() {
		return shipname;
	}
	public void setShipname(String shipname) {
		this.shipname = shipname;
	}
	public String getShipdirection() {
		return shipdirection;
	}
	public void setShipdirection(String shipdirection) {
		this.shipdirection = shipdirection;
	}
	public String getCruisedirection() {
		return cruisedirection;
	}
	public void setCruisedirection(String cruisedirection) {
		this.cruisedirection = cruisedirection;
	}
	public Float getSpeed() {
		return speed;
	}
	public void setSpeed(Float speed) {
		this.speed = speed;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public int getShiptype() {
		return shiptype;
	}
	public void setShiptype(int shiptype) {
		this.shiptype = shiptype;
	}

}
