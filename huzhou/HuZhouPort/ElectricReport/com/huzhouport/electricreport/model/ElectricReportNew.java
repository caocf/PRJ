package com.huzhouport.electricreport.model;

public class ElectricReportNew implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int reportID;// 报告编号
	private String shipName;// 船舶名称
	private String draft;// 油耗
	private int shipUser;// 船舶用户ID
	private String shipUserName;// 用户名称
	private String estimatedTimeOfArrival;// 预计进出港时间
	private String toBeDockedAtThePier;// 本次作业码头
	private String cargoType;// 载货种类
	private String cargoNumber;// 载货数量
	private String uniti;// 单位
	private String reportTime;// 申报时间
	private int reportKind;// 报告种类 1=重船航行 /2=空船航行
	private String startingPort;// 起始港
	private String arrivalPort;// 目的港
	private String draftTime;// 最近加油时间
	private int outOrIn;// 进出港 1：进港2：出港
	private String 	shipInfo;//船员信息 信息：职位，姓名，证书；
	private String 	prompt;//提示
	private int abnormal;//0：异常，1正常
	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getDraft() {
		return draft;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	public int getShipUser() {
		return shipUser;
	}

	public void setShipUser(int shipUser) {
		this.shipUser = shipUser;
	}

	public String getShipUserName() {
		return shipUserName;
	}

	public void setShipUserName(String shipUserName) {
		this.shipUserName = shipUserName;
	}

	public String getEstimatedTimeOfArrival() {
		return estimatedTimeOfArrival;
	}

	public void setEstimatedTimeOfArrival(String estimatedTimeOfArrival) {
		this.estimatedTimeOfArrival = estimatedTimeOfArrival;
	}

	public String getToBeDockedAtThePier() {
		return toBeDockedAtThePier;
	}

	public void setToBeDockedAtThePier(String toBeDockedAtThePier) {
		this.toBeDockedAtThePier = toBeDockedAtThePier;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoNumber() {
		return cargoNumber;
	}

	public void setCargoNumber(String cargoNumber) {
		this.cargoNumber = cargoNumber;
	}

	public String getUniti() {
		return uniti;
	}

	public void setUniti(String uniti) {
		this.uniti = uniti;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public int getReportKind() {
		return reportKind;
	}

	public void setReportKind(int reportKind) {
		this.reportKind = reportKind;
	}

	public String getStartingPort() {
		return startingPort;
	}

	public void setStartingPort(String startingPort) {
		this.startingPort = startingPort;
	}

	public String getArrivalPort() {
		return arrivalPort;
	}

	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

	public String getDraftTime() {
		return draftTime;
	}

	public void setDraftTime(String draftTime) {
		this.draftTime = draftTime;
	}

	public int getOutOrIn() {
		return outOrIn;
	}

	public void setOutOrIn(int outOrIn) {
		this.outOrIn = outOrIn;
	}

	public String getShipInfo() {
		return shipInfo;
	}

	public void setShipInfo(String shipInfo) {
		this.shipInfo = shipInfo;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public int getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(int abnormal) {
		this.abnormal = abnormal;
	}

}
