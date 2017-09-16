package com.huzhouport.attendace.model;

import java.util.Date;

import com.huzhouport.common.model.QueryCondition;



public class Sign implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int signID;
	private Date signTime;
	private int signUser;
	private int signLocation;
	private int signStatus;
	private QueryCondition queryCondition=new QueryCondition();
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public int getSignID() {
		return signID;
	}
	public void setSignID(int signID) {
		this.signID = signID;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public int getSignUser() {
		return signUser;
	}
	public void setSignUser(int signUser) {
		this.signUser = signUser;
	}
	public int getSignLocation() {
		return signLocation;
	}
	public void setSignLocation(int signLocation) {
		this.signLocation = signLocation;
	}
	public int getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(int signStatus) {
		this.signStatus = signStatus;
	}
	
	

}
