package com.huzhouport.cruiselog.model;

public class CruiseLogLink implements java.io.Serializable {
    private int cruiseLogID;
    private int illegalID;
	public int getCruiseLogID() {
		return cruiseLogID;
	}
	public void setCruiseLogID(int cruiseLogID) {
		this.cruiseLogID = cruiseLogID;
	}
	public int getIllegalID() {
		return illegalID;
	}
	public void setIllegalID(int illegalID) {
		this.illegalID = illegalID;
	}
    
}
