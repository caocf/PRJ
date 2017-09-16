package com.huzhouport.dangerousGoodsJob.model;

public class WharfJobNum implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int wharfID;
	private String wharfName;
	private int wharfLocation;
    private int num;
	public int getWharfID() {
		return wharfID;
	}
	public void setWharfID(int wharfID) {
		this.wharfID = wharfID;
	}
	public String getWharfName() {
		return wharfName;
	}
	public void setWharfName(String wharfName) {
		this.wharfName = wharfName;
	}
	public int getWharfLocation() {
		return wharfLocation;
	}
	public void setWharfLocation(int wharfLocation) {
		this.wharfLocation = wharfLocation;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	
}
