package com.huzhouport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User  implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String psd;
	private String phoneNumber;
	private String imei;
	private String iccid;
	private Date loginTime;
	
	private int bindnum;//绑定了第几条信息
	private List<ShipBinding> shipBindingList=new ArrayList<ShipBinding>();
	private List<WharfBinding> wharfBindingList=new ArrayList<WharfBinding>();
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPsd() {
		return psd;
	}
	public void setPsd(String psd) {
		this.psd = psd;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<ShipBinding> getShipBindingList() {
		return shipBindingList;
	}
	public void setShipBindingList(List<ShipBinding> shipBindingList) {
		this.shipBindingList = shipBindingList;
	}
	public List<WharfBinding> getWharfBindingList() {
		return wharfBindingList;
	}
	public void setWharfBindingList(List<WharfBinding> wharfBindingList) {
		this.wharfBindingList = wharfBindingList;
	}
	public int getBindnum() {
		if(shipBindingList!=null){
			if(bindnum>=shipBindingList.size()){
				bindnum=0;
			}
		}else if(wharfBindingList!=null){
			if(bindnum>=wharfBindingList.size()){
				bindnum=0;
			}
		}
		
		
		return bindnum;
	}
	public void setBindnum(int bindnum) {
		this.bindnum = bindnum;
	}
	
}