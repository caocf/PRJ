package com.gh.modol;

import com.amap.api.services.core.LatLonPoint;

public class SearchListItem {
	/*
	 * 用于周边搜索的搜索结果列表显示
	 * */
	private String point;//搜索名称
	private String distance;//搜索距离
	private String citycode;//
	private LatLonPoint llp;
	private String phone;
	private double distance2;
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public LatLonPoint getLlp() {
		return llp;
	}
	public void setLlp(LatLonPoint llp) {
		this.llp = llp;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getDistance2() {
		return distance2;
	}
	public void setDistance2(double distance2) {
		this.distance2 = distance2;
	}
}
