package com.huzhouport.wharfWork.model;

public class Wharf implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	 
	private int wharfID; //码头编号
	private String wharfName; //码头名称
	private String name; //负责人
	private String tel; //负责电话
	private int locationID; //位置信息
	private int portID; //所属港口
	private int wharfWorkNorm; //码头作业定量
	private int wharfWorkSurplus; //码头剩余定量
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public int getPortID() {
		return portID;
	}
	public void setPortID(int portID) {
		this.portID = portID;
	}
	public int getWharfWorkNorm() {
		return wharfWorkNorm;
	}
	public void setWharfWorkNorm(int wharfWorkNorm) {
		this.wharfWorkNorm = wharfWorkNorm;
	}
	public int getWharfWorkSurplus() {
		return wharfWorkSurplus;
	}
	public void setWharfWorkSurplus(int wharfWorkSurplus) {
		this.wharfWorkSurplus = wharfWorkSurplus;
	}

    

}
