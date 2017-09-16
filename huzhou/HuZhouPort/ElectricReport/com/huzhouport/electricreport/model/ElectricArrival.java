package com.huzhouport.electricreport.model;
public class ElectricArrival implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int electId;// 编号
	private int reportID;// 报告编号
	private String arrivalPort;// 船舶名称
	private String arrivalTime;//时间
	
	public int getElectId() {
		return electId;
	}
	public void setElectId(int electId) {
		this.electId = electId;
	}
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public String getArrivalPort() {
		return arrivalPort;
	}
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
}
