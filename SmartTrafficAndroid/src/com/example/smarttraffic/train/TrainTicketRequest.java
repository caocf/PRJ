package com.example.smarttraffic.train;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 铁路车票信息请求类
 * @author Administrator zhou
 *
 */
public class TrainTicketRequest extends BaseRequest implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String startCity;
	private String endCity;
	private int year;
	private int month;
	private int day;
	private int trainType;
	private int searchType;
	private int startTimeType;
	private int endTImeType;
	
	private boolean isFromStation;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	
	public int getTrainType()
	{
		return trainType;
	}
	public void setTrainType(int trainType)
	{
		this.trainType = trainType;
	}
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public int getStartTimeType()
	{
		return startTimeType;
	}
	public void setStartTimeType(int startTimeType)
	{
		this.startTimeType = startTimeType;
	}
	public int getEndTImeType()
	{
		return endTImeType;
	}
	public void setEndTImeType(int endTImeType)
	{
		this.endTImeType = endTImeType;
	}
	

	public boolean isFromStation()
	{
		return isFromStation;
	}
	public void setFromStation(boolean isFromStation)
	{
		this.isFromStation = isFromStation;
	}


	public final String TRAIN_TYPE="type";
	public final String START_TIME_TYPE="leaveTime";
	public final String END_TIME_TYPE="reachTime";
	
	public final String START_STATINON="startStation";
	public final String END_STATION="arriveStation";
	
	
	@Override
	public PostParams CreatePostParams()
	{
		if(isFromStation)
			return GetFromStation();
		else
			return NotFromStation();
			
	}
	
	public PostParams GetFromStation()
	{
		PostParams result=new PostParams();
		
		result.setUrl(HttpUrlRequestAddress.TRAIN_REQUEST_STATION_URL);
		Map<String, Object> params=new HashMap<String, Object>();
		
		if(trainType!=0)
			params.put(TRAIN_TYPE, trainType);
		if(!startCity.equals(""))
			params.put(START_STATINON,startCity);
		if(startTimeType!=0)
			params.put(START_TIME_TYPE, startTimeType);
		if(endTImeType!=0)
			params.put(END_TIME_TYPE, endTImeType);
		
		result.setParams(params);
		
		return result;
	}
	
	public PostParams NotFromStation()
	{
		PostParams result=new PostParams();
		
		result.setUrl(HttpUrlRequestAddress.TRAIN_REQUEST_STATION_URL);
		Map<String, Object> params=new HashMap<String, Object>();
		
		if(trainType!=0)
			params.put(TRAIN_TYPE, trainType);
		if(!startCity.equals(""))
			params.put(START_STATINON,startCity);
		if(!endCity.equals(""))
			params.put(END_STATION, endCity);
		if(startTimeType!=0)
			params.put(START_TIME_TYPE, startTimeType);
		if(endTImeType!=0)
			params.put(END_TIME_TYPE, endTImeType);
		
		result.setParams(params);
		
		return result;
	}
}
