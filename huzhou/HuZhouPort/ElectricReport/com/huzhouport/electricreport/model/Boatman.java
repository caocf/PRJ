package com.huzhouport.electricreport.model;

public class Boatman implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int boatmanID;// 船员信息编号
	private int boatmanKind;// 船员信息kind
	private String boatmanName; //船员名字
	private String boatmanShip; //船名号
	private String boatCardID; //船员证件ID
	private String boatCardNum; //船员证件编号
	private String kindList; 
	public int getBoatmanID() {
		return boatmanID;
	}
	public void setBoatmanID(int boatmanID) {
		this.boatmanID = boatmanID;
	}
	public int getBoatmanKind() {
		return boatmanKind;
	}
	public void setBoatmanKind(int boatmanKind) {
		this.boatmanKind = boatmanKind;
	}
	public String getBoatmanName() {
		return boatmanName;
	}
	public void setBoatmanName(String boatmanName) {
		this.boatmanName = boatmanName;
	}
	public String getBoatmanShip() {
		return boatmanShip;
	}
	public void setBoatmanShip(String boatmanShip) {
		this.boatmanShip = boatmanShip;
	}
	public String getBoatCardNum() {
		return boatCardNum;
	}
	public void setBoatCardNum(String boatCardNum) {
		this.boatCardNum = boatCardNum;
	}
	public String getKindList() {
		return kindList;
	}
	public void setKindList(String kindList) {
		this.kindList = kindList;
	}
	public String getBoatCardID() {
		return boatCardID;
	}
	public void setBoatCardID(String boatCardID) {
		this.boatCardID = boatCardID;
	}
	
}
