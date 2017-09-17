package com.sts.parking.model;

public class ParkingCG 
{
	private int parktype;
	private String parkpointid;
	private String pointname;
	private String roadname;
	private double gpslo;
	private double gpsla;
	private int parklotcount;
	private int chargemode;
	private int freecount;
	
	public void setFreecount(int freecount) {
		this.freecount = freecount;
	}
	public int getFreecount() {
		return freecount;
	}
	public int getParktype() {
		return parktype;
	}
	public void setParktype(int parktype) {
		this.parktype = parktype;
	}
	public String getParkpointid() {
		return parkpointid;
	}
	public void setParkpointid(String parkpointid) {
		this.parkpointid = parkpointid;
	}
	public String getPointname() {
		return pointname;
	}
	public void setPointname(String pointname) {
		this.pointname = pointname;
	}
	public String getRoadname() {
		return roadname;
	}
	public void setRoadname(String roadname) {
		this.roadname = roadname;
	}
	public double getGpslo() {
		return gpslo;
	}
	public void setGpslo(double gpslo) {
		this.gpslo = gpslo;
	}
	public double getGpsla() {
		return gpsla;
	}
	public void setGpsla(double gpsla) {
		this.gpsla = gpsla;
	}
	public int getParklotcount() {
		return parklotcount;
	}
	public void setParklotcount(int parklotcount) {
		this.parklotcount = parklotcount;
	}
	public int getChargemode() {
		return chargemode;
	}
	public void setChargemode(int chargemode) {
		this.chargemode = chargemode;
	}
}
