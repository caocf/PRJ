package com.highwaycenter.bean;

import java.util.Date;

public class XcglgkBean implements java.io.Serializable {
  
	private static final long serialVersionUID = 5248413362283999489L;
	private String id;
	private String stationId;
	private String stationName;
	private Date inspectDate;
	private String weather;
	private String content;
	private String inspector;
	private String handlerSignature;
	private String handleDate;
	private String inspectDateString;
	
	public XcglgkBean(){
		
	}
	
	public XcglgkBean(String id, String stationId,String stationName,Date inspectDate,String inspectDateString,
			String weather, String content, String inspector,
			String handlerSignature, String handleDate) {
		super();
		this.id = id;
		this.stationId = stationId;
		this.stationName = stationName;
		this.inspectDate = inspectDate;
		this.inspectDateString = inspectDateString;
		this.weather = weather;
		this.content = content;
		this.inspector = inspector;
		this.handlerSignature = handlerSignature;
		this.handleDate = handleDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public Date getInspectDate() {
		return inspectDate;
	}
	public void setInspectDate(Date inspectDate) {
		this.inspectDate = inspectDate;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getHandlerSignature() {
		return handlerSignature;
	}
	public void setHandlerSignature(String handlerSignature) {
		this.handlerSignature = handlerSignature;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	public String getInspectDateString() {
		return inspectDateString;
	}
	public void setInspectDateString(String inspectDateString) {
		this.inspectDateString = inspectDateString;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	

}
