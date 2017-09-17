package com.SmartTraffic.video.model;

public class STControlUnit implements java.io.Serializable{

	private static final long serialVersionUID = 3831876402430841341L;

	private int controlUnitID;
	private String controlUnitName;
	private int upControlUnitID;
	
	public STControlUnit(){
		super();
	}
	public STControlUnit(int controlUnitID, String controlUnitName,
			int upControlUnitID) {
		super();
		this.controlUnitID = controlUnitID;
		this.controlUnitName = controlUnitName;
		this.upControlUnitID = upControlUnitID;
	}
	public int getControlUnitID() {
		return controlUnitID;
	}
	public void setControlUnitID(int controlUnitID) {
		this.controlUnitID = controlUnitID;
	}
	public String getControlUnitName() {
		return controlUnitName;
	}
	public void setControlUnitName(String controlUnitName) {
		this.controlUnitName = controlUnitName;
	}
	public int getUpControlUnitID() {
		return upControlUnitID;
	}
	public void setUpControlUnitID(int upControlUnitID) {
		this.upControlUnitID = upControlUnitID;
	}
	
	
}
