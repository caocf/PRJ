package com.huzhouport.leaveandovertime;



public class LeaveOrOtKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int kindID;
	private String kindName;
	private int kindType;
	private int DefauleDate;
	
	public int getKindID() {
		return kindID;
	}
	public void setKindID(int kindID) {
		this.kindID = kindID;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public int getKindType() {
		return kindType;
	}
	public void setKindType(int kindType) {
		this.kindType = kindType;
	}
	public int getDefauleDate() {
		return DefauleDate;
	}
	public void setDefauleDate(int defauleDate) {
		DefauleDate = defauleDate;
	}
	
	
}
