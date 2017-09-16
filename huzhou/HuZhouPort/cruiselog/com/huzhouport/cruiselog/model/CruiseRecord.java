package com.huzhouport.cruiselog.model;

import java.util.Set;

import com.huzhouport.user.model.User;

public class CruiseRecord implements java.io.Serializable
{
	int id;
	String tool;
	
	Set<User> userids;
	int meters;
	String startTime;
	String endTime;	
	Set<CruiseTrack> tacks;	
	int issues;
	int status;
	
	
	public int getIssues() {
		return issues;
	}
	public void setIssues(int issues) {
		this.issues = issues;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Set<CruiseTrack> getTacks() {
		return tacks;
	}
	public void setTacks(Set<CruiseTrack> tacks) {
		this.tacks = tacks;
	}
	public int getMeters() {
		return meters;
	}
	public void setMeters(int meters) {
		this.meters = meters;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
	}
	public Set<User> getUserids() {
		return userids;
	}
	public void setUserids(Set<User> userids) {
		this.userids = userids;
	}
	
	
}
