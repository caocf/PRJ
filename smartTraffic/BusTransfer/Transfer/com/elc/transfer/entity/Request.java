package com.elc.transfer.entity;

import java.util.Date;

/**
 * 用户请求参数
 * @author Administrator
 *
 */
public class Request {
	
	public static final int FOOTER_FIRST=1;					//少步行
	public static final int TIME_FIRST=2;					//少时间
	public static final int DISTANCE_FIRST=3;				//距离最短
	public static final int CHANGE_FIRST=4;					//少换乘
	
	private double lan1;											
	private double lan2;
	private double lon1;
	private double lon2;
	
	private int mode;
	private Date date;
	private int containsBike;
	
	public int getContainsBike() {
		return containsBike;
	}
	public void setContainsBike(int containsBike) {
		this.containsBike = containsBike;
	}
	public double getLan1() {
		return lan1;
	}
	public void setLan1(double lan1) {
		this.lan1 = lan1;
	}
	public double getLan2() {
		return lan2;
	}
	public void setLan2(double lan2) {
		this.lan2 = lan2;
	}
	public double getLon1() {
		return lon1;
	}
	public void setLon1(double lon1) {
		this.lon1 = lon1;
	}
	public double getLon2() {
		return lon2;
	}
	public void setLon2(double lon2) {
		this.lon2 = lon2;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
