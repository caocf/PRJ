package com.example.smarttraffic.smartBus.bean;

import java.util.List;

/**
 * 公交站点详细信息
 * @author Administrator zhou
 *
 */
public class Station
{
	int id;							//站点id
	String name;					//站点名称
	String address;					//站点地址
	int orient;         			// 1:上行 2：下行
	
	double lantitude;				//站点纬度
	double longtitude;				//站点经度
	
	List<LineOfStation> lineForStations;		//途径站点线路列表

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}

	public double getLantitude() {
		return lantitude;
	}

	public void setLantitude(double lantitude) {
		this.lantitude = lantitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public List<LineOfStation> getLineForStations() {
		return lineForStations;
	}

	public void setLineForStations(List<LineOfStation> lineForStations) {
		this.lineForStations = lineForStations;
	}
}


