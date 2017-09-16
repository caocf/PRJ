package com.example.smarttraffic.smartBus.bean;

import java.util.List;

/**
 * 线路实时数据
 * @author Administrator zhou
 *
 */
public class StationForReal
{
	int stationID;						//站点id
	int lineID;							//线路id
	String stationName;					//站点名称
	String lineName;					//线路名称
	int orient;							//方向
	
	List<RealInfo> realInfos;			//实时信息列表

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}

	public List<RealInfo> getRealInfos() {
		return realInfos;
	}

	public void setRealInfos(List<RealInfo> realInfos) {
		this.realInfos = realInfos;
	}

	public int getStationID()
	{
		return stationID;
	}

	public void setStationID(int stationID)
	{
		this.stationID = stationID;
	}

	public int getLineID()
	{
		return lineID;
	}

	public void setLineID(int lineID)
	{
		this.lineID = lineID;
	}
	
}


