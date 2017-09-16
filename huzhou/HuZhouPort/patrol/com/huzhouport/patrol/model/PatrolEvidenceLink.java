package com.huzhouport.patrol.model;

public class PatrolEvidenceLink implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int patrolId;
	private int evidenceId;
	public int getPatrolId() {
		return patrolId;
	}
	public void setPatrolId(int patrolId) {
		this.patrolId = patrolId;
	}
	public int getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(int evidenceId) {
		this.evidenceId = evidenceId;
	}
	
	

}
