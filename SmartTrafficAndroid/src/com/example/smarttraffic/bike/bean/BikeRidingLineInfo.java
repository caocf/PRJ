package com.example.smarttraffic.bike.bean;

import java.io.Serializable;
import java.util.List;

public class BikeRidingLineInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length;
	private int time;
	private String stationListName;
	private BikeRiding bikeRiding;
	
	public BikeRiding getBikeRiding()
	{
		return bikeRiding;
	}
	public void setBikeRiding(BikeRiding bikeRiding)
	{
		this.bikeRiding = bikeRiding;
	}
	
	List<BikeRidingStationOnLine> stationInfos;
	
	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public List<BikeRidingStationOnLine> getStationInfos()
	{
		return stationInfos;
	}

	public void setStationInfos(List<BikeRidingStationOnLine> stationInfos)
	{
		this.stationInfos = stationInfos;
	}
	
	public void setStationListName(String stationList)
	{
		this.stationListName = stationList;
	}
	
	public String getStationListName()
	{
		if(stationInfos==null)
			return "";
		
		String result="";
		for(int i=0;i<stationInfos.size();i++)
		{
			if(i>4)
			{
				result+="......";
				break;
			}
			
			if(i>0)
				result+=" - ";
			result+=stationInfos.get(i).getStationName();
		}
		
		setStationListName(result);
		
		return stationListName;
	}
}
