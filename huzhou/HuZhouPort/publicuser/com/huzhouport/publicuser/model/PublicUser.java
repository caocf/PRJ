package com.huzhouport.publicuser.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.huzhouport.common.model.QueryCondition;

public class PublicUser implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String psd;
	private String phoneNumber;
	private String imei;
	private String iccid;
	private Date loginTime;
	private String userList;
	private String bindName;//优先选中的船舶或码头名称
	private List<ShipBinding> lists=new ArrayList<ShipBinding>();
	private List<WharfBinding> listw=new ArrayList<WharfBinding>();
	
	private QueryCondition queryCondition=new QueryCondition();
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
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<ShipBinding> getLists() {
		return lists;
	}
	public void setLists(List<ShipBinding> lists) {
		this.lists = lists;
	}
	public List<WharfBinding> getListw() {
		return listw;
	}
	public void setListw(List<WharfBinding> listw) {
		this.listw = listw;
	}
	public String getBindName() {
		return bindName;
	}
	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
	
}
