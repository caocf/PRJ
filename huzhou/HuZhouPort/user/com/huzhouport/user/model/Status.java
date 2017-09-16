package com.huzhouport.user.model;

public class Status implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int statusId;
	private String statusName;
	private String statusType;//1:在职，0：离职
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	

}
