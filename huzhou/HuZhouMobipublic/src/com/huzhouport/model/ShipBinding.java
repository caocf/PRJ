package com.huzhouport.model;

public class ShipBinding implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int shipId;
	private String shipNum;
	private int shipUser;
	private String bindingTime;
	public String getShipNum() {
		return shipNum;
	}
	public void setShipNum(String shipNum) {
		this.shipNum = shipNum;
	}
	public int getShipUser() {
		return shipUser;
	}
	public void setShipUser(int shipUser) {
		this.shipUser = shipUser;
	}
	public String getBindingTime() {
		return bindingTime;
	}
	public void setBindingTime(String bindingTime) {
		this.bindingTime = bindingTime;
	}
	public int getShipId() {
		return shipId;
	}
	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

}
