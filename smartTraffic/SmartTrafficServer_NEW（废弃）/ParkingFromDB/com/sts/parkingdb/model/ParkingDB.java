package com.sts.parkingdb.model;

public class ParkingDB 
{
	private String parktype;
	private String parkpointid;
	private String pointname;
	private String roadname;
	private double gpslo;
	private double gpsla;
	private int parklotcount;
	private String chargemode;
	private int freecount;
	
	public void setFreecount(int freecount) {
		this.freecount = freecount;
	}
	public int getFreecount() {
		return freecount;
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
	public String getParktype() {
		return parktype;
	}
	public void setParktype(String parktype) {
		this.parktype = parktype;
	}
	public String getChargemode() {
		return chargemode;
	}
	public void setChargemode(String chargemode) {
		this.chargemode = chargemode;
	}
	
}
