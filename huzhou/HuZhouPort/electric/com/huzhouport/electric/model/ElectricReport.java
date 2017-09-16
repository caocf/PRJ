package com.huzhouport.electric.model;



import java.util.Date;

import com.huzhouport.common.model.QueryCondition;

public class ElectricReport implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int reportID;// 报告编号
	private String shipName;// 船舶名称
	private String draft;//实际吃水
	private int shipUser;//船舶用户ID
	private String shipUserName;//用户名称
	private String estimatedTimeOfArrival;// 预计进出港时间
	private String toBeDockedAtThePier;// 本次作业码头
	//private Date reportTime;// 时间
	//private String cargoSituation;//载货情况
	private String cargoType;// 载货种类
	private String cargoNumber;// 载货数量
	private String uniti;//danwei
	private Date reportTime;// 进港时间
	private String condtion;//
	

	

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	private QueryCondition queryCondition = new QueryCondition();

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

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
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

	public QueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getCondtion() {
		return condtion;
	}

	public void setCondtion(String condtion) {
		this.condtion = condtion;
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

	/*public String getCargoSituation() {
		return cargoSituation;
	}

	public void setCargoSituation(String cargoSituation) {
		this.cargoSituation = cargoSituation;
	}*/

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

}
