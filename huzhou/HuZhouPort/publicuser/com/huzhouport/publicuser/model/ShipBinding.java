package com.huzhouport.publicuser.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipBinding implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int shipId;
	private String shipNum;
	private int shipUser;
	private String bindingTime;
	private String status;
	private List<ShipFile> listf=new ArrayList<ShipFile>();
	public String getShipNum() {
		return shipNum;
	}
	public void setShipNum(String shipNum) {
		this.shipNum = shipNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<ShipFile> getListf() {
		return listf;
	}
	public void setListf(List<ShipFile> listf) {
		this.listf = listf;
	}

}
