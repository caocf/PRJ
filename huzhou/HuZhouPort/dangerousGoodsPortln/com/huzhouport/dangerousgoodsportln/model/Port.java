package com.huzhouport.dangerousgoodsportln.model;

public class Port implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int portID;
	private String portName;
	public int getPortID() {
		return portID;
	}
	public void setPortID(int portID) {
		this.portID = portID;
	}
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}

	
}
