package com.huzhouport.electric.model;

import java.util.Date;

import com.huzhouport.common.model.QueryCondition;

public class FixedTermReport implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int fixedTermID;         //定期签证ID
	private String shipName;  //船名号
	private String startingPort; //起始港
	private String arrivalPort;   //目的港
	private Date startTime;    //起始时间
	private Date endTime;    //结束时间
	
	private String visaContent;   //内容
	private QueryCondition queryCondition = new QueryCondition();
	private String condition;
	public int getFixedTermID() {
		return fixedTermID;
	}
	public void setFixedTermID(int fixedTermID) {
		this.fixedTermID = fixedTermID;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getStartingPort() {
		return startingPort;
	}
	public void setStartingPort(String startingPort) {
		this.startingPort = startingPort;
	}
	public String getArrivalPort() {
		return arrivalPort;
	}
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getVisaContent() {
		return visaContent;
	}
	public void setVisaContent(String visaContent) {
		this.visaContent = visaContent;
	}
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
