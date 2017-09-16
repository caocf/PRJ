package com.huzhouport.patrol.model;

public class PatrolSupplement implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int patrolId;
	private int userId;
	private String supplementTime;
	
	public int getPatrolId() {
		return patrolId;
	}
	public void setPatrolId(int patrolId) {
		this.patrolId = patrolId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSupplementTime() {
		return supplementTime;
	}
	public void setSupplementTime(String supplementTime) {
		this.supplementTime = supplementTime;
	}
	

}
