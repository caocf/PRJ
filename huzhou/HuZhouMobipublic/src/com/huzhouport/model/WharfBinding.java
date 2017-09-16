package com.huzhouport.model;

public class WharfBinding implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int wharfId;
	private String wharfNum;
	private int wharfUser;
	private String bindingTime;
	private String wharfNumber;
	private String wharfWorkSurplus;

	public String getWharfNum() {
		return wharfNum;
	}

	public void setWharfNum(String wharfNum) {
		this.wharfNum = wharfNum;
	}

	public int getWharfUser() {
		return wharfUser;
	}

	public void setWharfUser(int wharfUser) {
		this.wharfUser = wharfUser;
	}

	public String getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(String bindingTime) {
		this.bindingTime = bindingTime;
	}

	public int getWharfId() {
		return wharfId;
	}

	public void setWharfId(int wharfId) {
		this.wharfId = wharfId;
	}

	public String getWharfNumber() {
		return wharfNumber;
	}

	public void setWharfNumber(String wharfNumber) {
		this.wharfNumber = wharfNumber;
	}

	public String getWharfWorkSurplus() {
		return wharfWorkSurplus;
	}

	public void setWharfWorkSurplus(String wharfWorkSurplus) {
		this.wharfWorkSurplus = wharfWorkSurplus;
	}

}

