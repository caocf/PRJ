package com.huzhouport.cruiselog.model;

import java.util.List;

public class CruiseIssue implements java.io.Serializable
{
	int id;
	int type;
	int leftorright;
	int channelid;
	int recordid;
	String kz;
	String mark;
	String uptime;
	List filenames;	
	
	
	
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public List getFilenames() {
		return filenames;
	}
	public void setFilenames(List filenames) {
		this.filenames = filenames;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLeftorright() {
		return leftorright;
	}
	public void setLeftorright(int leftorright) {
		this.leftorright = leftorright;
	}
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	public int getRecordid() {
		return recordid;
	}
	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}
	public String getKz() {
		return kz;
	}
	public void setKz(String kz) {
		this.kz = kz;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
}
