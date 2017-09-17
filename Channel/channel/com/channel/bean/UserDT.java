package com.channel.bean;


public class UserDT {
	private Object[] arruser;
	private String strdpt;

	public Object[] getArruser() {
		return arruser;
	}

	public void setArruser(Object[] arruser) {
		this.arruser = arruser;
	}

	public String getStrdpt() {
		return strdpt;
	}

	public void setStrdpt(String strdpt) {
		this.strdpt = strdpt;
	}

	public UserDT() {
		super();
	}

	public UserDT(Object[] arruser, String strdpt) {
		super();
		this.arruser = arruser;
		this.strdpt = strdpt;
	}

}
