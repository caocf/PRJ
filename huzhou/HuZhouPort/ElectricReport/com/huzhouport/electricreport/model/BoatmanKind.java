package com.huzhouport.electricreport.model;

public class BoatmanKind implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int boatmanKindID;// 编号
	private String boatmanKindName;// 职位名称
	private String isDelete;//Y:已删除，N：未删除
	public int getBoatmanKindID() {
		return boatmanKindID;
	}
	public void setBoatmanKindID(int boatmanKindID) {
		this.boatmanKindID = boatmanKindID;
	}
	public String getBoatmanKindName() {
		return boatmanKindName;
	}
	public void setBoatmanKindName(String boatmanKindName) {
		this.boatmanKindName = boatmanKindName;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
}
