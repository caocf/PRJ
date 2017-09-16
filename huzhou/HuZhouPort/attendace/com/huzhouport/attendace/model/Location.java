package com.huzhouport.attendace.model;

import com.huzhouport.common.model.QueryCondition;



public class Location implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int locationID;
	private String longitude;
	private String latitude;
	private String locationName;
	private QueryCondition queryCondition=new QueryCondition();
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	

}
