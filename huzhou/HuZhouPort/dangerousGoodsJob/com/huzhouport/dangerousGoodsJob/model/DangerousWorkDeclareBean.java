package com.huzhouport.dangerousGoodsJob.model;

public class DangerousWorkDeclareBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int declareID; //申报记录编号
	private String shipName; //船舶名称
	private String declareTime;//申报记录时间
	private int startingPort; //起运港
	private String startingPortName; //起运港
	private int arrivalPort; //目的港
	private String arrivalPortName; //目的港
	private String cargoTypes; //货物名称
	private String dangerousLevel; //危险品等级
	private String  wharfID; //作业码头名称
	private String workTIme; //申请作业时间
	private int carrying; //载重
	private int reviewUser; //审核用户
	private String reviewUserName; //审核用户
	private int reviewResult;//审核结果
	private String reviewOpinion;//审核意见
	private String reviewTime;
	
	public int getDeclareID() {
		return declareID;
	}
	public void setDeclareID(int declareID) {
		this.declareID = declareID;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getDeclareTime() {
		return declareTime;
	}
	public void setDeclareTime(String declareTime) {
		this.declareTime = declareTime;
	}
	public int getStartingPort() {
		return startingPort;
	}
	public void setStartingPort(int startingPort) {
		this.startingPort = startingPort;
	}
	public String getStartingPortName() {
		return startingPortName;
	}
	public void setStartingPortName(String startingPortName) {
		this.startingPortName = startingPortName;
	}
	public int getArrivalPort() {
		return arrivalPort;
	}
	public void setArrivalPort(int arrivalPort) {
		this.arrivalPort = arrivalPort;
	}
	public String getArrivalPortName() {
		return arrivalPortName;
	}
	public void setArrivalPortName(String arrivalPortName) {
		this.arrivalPortName = arrivalPortName;
	}
	public String getCargoTypes() {
		return cargoTypes;
	}
	public void setCargoTypes(String cargoTypes) {
		this.cargoTypes = cargoTypes;
	}
	public String getDangerousLevel() {
		return dangerousLevel;
	}
	public void setDangerousLevel(String dangerousLevel) {
		this.dangerousLevel = dangerousLevel;
	}


	public String getWharfID() {
		return wharfID;
	}
	public void setWharfID(String wharfID) {
		this.wharfID = wharfID;
	}
	public String getWorkTIme() {
		return workTIme;
	}
	public void setWorkTIme(String workTIme) {
		this.workTIme = workTIme;
	}
	public int getCarrying() {
		return carrying;
	}
	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}
	public int getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(int reviewUser) {
		this.reviewUser = reviewUser;
	}
	public String getReviewUserName() {
		return reviewUserName;
	}
	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public String getReviewOpinion() {
		return reviewOpinion;
	}
	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	

	
}
