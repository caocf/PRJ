package com.sts.trafficValue.model;

import java.util.Date;

public class RealRoadInfo 
{
	private int infoType;								//0:南湖;1秀洲:;2二环:;3:内环
	private String infoContent;
	private Date infoDate;
	private int status;									//0:正常；1：高峰；2：低峰
	
	public int getInfoType() {
		return infoType;
	}
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public Date getInfoDate() {
		return infoDate;
	}
	public void setInfoDate(Date infoDate) {
		this.infoDate = infoDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
