package net.hxkg.channel;

public class CruiseRecord implements java.io.Serializable
{
	int id;
	String tool;	
	int meters;
	String startTime;
	String endTime;	
	int status;
	String userArray;
	String trackArray;
	int issues;
	
	
	public int getIssues() {
		return issues;
	}
	public void setIssues(int issues) {
		this.issues = issues;
	}
	
	public String getUserArray() {
		return userArray;
	}
	public void setUserArray(String userArray) {
		this.userArray = userArray;
	}
	public String getTrackArray() {
		return trackArray;
	}
	public void setTrackArray(String trackArray) {
		this.trackArray = trackArray;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
	
	
}
