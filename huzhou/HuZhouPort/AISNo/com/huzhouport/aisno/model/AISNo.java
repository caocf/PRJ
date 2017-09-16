package com.huzhouport.aisno.model;

public class AISNo {

	private String aisnum;
	private String shipname;
	private String type;
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getAisnum() {
		return aisnum;
	}
	public void setAisnum(String aisnum) {
		this.aisnum = aisnum;
	}
	public String getShipname() {
		return shipname;
	}
	public void setShipname(String shipname) {
		this.shipname = shipname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
