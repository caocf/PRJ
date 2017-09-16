package com.gh.modol;

public class DeductPointsModel {
	
	private String penalty;
	private String reason;
	private String time;
	
	
	//{"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
	public String getpenalty() {
		return penalty;
	}
	public void setpenalty(String penalty) {
		this.penalty = penalty;
	}
	public String getreason() {
		return reason;
	}
	public void setreason(String reason) {
		this.reason = reason;
	}
	public String gettime() {
		return time;
	}
	public void settime(String time) {
		this.time = time;
	}
	

	


}
