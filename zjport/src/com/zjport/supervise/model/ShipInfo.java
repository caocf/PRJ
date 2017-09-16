package com.zjport.supervise.model;

import java.sql.Timestamp;

public class ShipInfo {
	private String shipname;// 船舶名称
	private double longitude;// 经度
	private double latitude;// 纬度
	private double speed;// 速度
	private double cruisedirection;// 航向
	private double shipdirection;// 航向
	private Timestamp shipdate;// 事件
	private String ais;
	private Timestamp adddate;

	private String area;//船舶当前所在区域
	private String shiptype;
	private String cblx;

	private int peccancy; //违章
	private int overdue; //证书过期
	private int arrearage; //缴费

	public String getShipname() {
		return shipname;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getCruisedirection() {
		return cruisedirection;
	}

	public void setCruisedirection(double cruisedirection) {
		this.cruisedirection = cruisedirection;
	}

	public double getShipdirection() {
		return shipdirection;
	}

	public void setShipdirection(double shipdirection) {
		this.shipdirection = shipdirection;
	}

	public String getAis() {
		return ais;
	}

	public void setAis(String ais) {
		this.ais = ais;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getShiptype() {
		return shiptype;
	}

	public void setShiptype(String shiptype) {
		this.shiptype = shiptype;
	}

	public String getCblx() {
		return cblx;
	}

	public void setCblx(String cblx) {
		this.cblx = cblx;
	}

	public Timestamp getShipdate() {
		return shipdate;
	}

	public void setShipdate(Timestamp shipdate) {
		this.shipdate = shipdate;
	}

	public Timestamp getAdddate() {
		return adddate;
	}

	public void setAdddate(Timestamp adddate) {
		this.adddate = adddate;
	}

	public int getPeccancy() {
		return peccancy;
	}

	public void setPeccancy(int peccancy) {
		this.peccancy = peccancy;
	}

	public int getOverdue() {
		return overdue;
	}

	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}

	public int getArrearage() {
		return arrearage;
	}

	public void setArrearage(int arrearage) {
		this.arrearage = arrearage;
	}

	public ShipInfo() {
		super();
	}

}
