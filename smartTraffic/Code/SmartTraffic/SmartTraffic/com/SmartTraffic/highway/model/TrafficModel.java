package com.SmartTraffic.highway.model;

import java.util.List;

public class TrafficModel {

	private String LXBH;// 路线编号
	private String QDZH;// 起点桩号
	private String ZDZH;// 终点桩号
	private String YJD_S;// 拥挤度上行
	private String YJD_X;// 拥挤度下行
	private String YJD_A;// 拥挤度断面
	private List<StakePoint> sxList;
	private int sxLineCode;
	private List<StakePoint> xxList;
	private int xxLineCode;

	private String lxjc; // 路线简称
	
	
	public List<StakePoint> getSxList() {
		return sxList;
	}
	public void setSxList(List<StakePoint> sxList) {
		this.sxList = sxList;
	}
	public int getSxLineCode() {
		return sxLineCode;
	}
	public void setSxLineCode(int sxLineCode) {
		this.sxLineCode = sxLineCode;
	}
	public List<StakePoint> getXxList() {
		return xxList;
	}
	public void setXxList(List<StakePoint> xxList) {
		this.xxList = xxList;
	}
	public int getXxLineCode() {
		return xxLineCode;
	}
	public void setXxLineCode(int xxLineCode) {
		this.xxLineCode = xxLineCode;
	}
	public String getLXBH() {
		return LXBH;
	}
	public void setLXBH(String lXBH) {
		LXBH = lXBH;
	}
	public String getQDZH() {
		return QDZH;
	}
	public void setQDZH(String qDZH) {
		QDZH = qDZH;
	}
	public String getZDZH() {
		return ZDZH;
	}
	public void setZDZH(String zDZH) {
		ZDZH = zDZH;
	}
	public String getYJD_S() {
		return YJD_S;
	}
	public void setYJD_S(String yJD_S) {
		YJD_S = yJD_S;
	}
	public String getYJD_X() {
		return YJD_X;
	}
	public void setYJD_X(String yJD_X) {
		YJD_X = yJD_X;
	}
	public String getYJD_A() {
		return YJD_A;
	}
	public void setYJD_A(String yJD_A) {
		YJD_A = yJD_A;
	}
	public String getLxjc() {
		return lxjc;
	}
	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}
	
	
	
	
}
