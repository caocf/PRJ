package com.huzhouport.patrol.model;

import com.huzhouport.common.model.QueryCondition;

public class Patrol implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int patrolId;
	private int patrolUser;
	private String patrolObject;
	private String patrolContent;
	private String patrolTime;
	private int patrolLocation;
	private int patCategoriese;//1:港政2：运政3：航政4：海事
	public int getPatCategoriese() {
		return patCategoriese;
	}
	public void setPatCategoriese(int patCategoriese) {
		this.patCategoriese = patCategoriese;
	}
	private QueryCondition queryCondition=new QueryCondition();
	
	public int getPatrolId() {
		return patrolId;
	}
	public void setPatrolId(int patrolId) {
		this.patrolId = patrolId;
	}
	public int getPatrolUser() {
		return patrolUser;
	}
	public void setPatrolUser(int patrolUser) {
		this.patrolUser = patrolUser;
	}
	public String getPatrolObject() {
		return patrolObject;
	}
	public void setPatrolObject(String patrolObject) {
		this.patrolObject = patrolObject;
	}
	public String getPatrolContent() {
		return patrolContent;
	}
	public void setPatrolContent(String patrolContent) {
		this.patrolContent = patrolContent;
	}
	public String getPatrolTime() {
		return patrolTime;
	}
	public void setPatrolTime(String patrolTime) {
		this.patrolTime = patrolTime;
	}
	public int getPatrolLocation() {
		return patrolLocation;
	}
	public void setPatrolLocation(int patrolLocation) {
		this.patrolLocation = patrolLocation;
	}
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	
}
