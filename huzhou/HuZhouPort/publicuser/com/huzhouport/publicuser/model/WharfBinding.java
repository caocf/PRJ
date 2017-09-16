package com.huzhouport.publicuser.model;

import java.util.ArrayList;
import java.util.List;

public class WharfBinding implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int wharfId;
	private String wharfNum;
	private int wharfUser;
	private String bindingTime;
	private String wharfNumber;
	private String wharfWorkNorm;
	private String wharfWorkSurplus;
	private List<WharfFile> listf=new ArrayList<WharfFile>();

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

	public String getWharfWorkNorm() {
		return wharfWorkNorm;
	}

	public void setWharfWorkNorm(String wharfWorkNorm) {
		this.wharfWorkNorm = wharfWorkNorm;
	}

	public String getWharfWorkSurplus() {
		return wharfWorkSurplus;
	}

	public void setWharfWorkSurplus(String wharfWorkSurplus) {
		this.wharfWorkSurplus = wharfWorkSurplus;
	}

	public List<WharfFile> getListf() {
		return listf;
	}

	public void setListf(List<WharfFile> listf) {
		this.listf = listf;
	}

	
	
}
