package com.huzhouport.statistic.model;

public class ReportModel {
	private float amount;//数量
	private String oneName;//一类名称
	private String twoName;//二类名称
	private String beginTime;
	private String endTime;
	private int condition;
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getOneName() {
		return oneName;
	}
	public void setOneName(String oneName) {
		this.oneName = oneName;
	}
	public String getTwoName() {
		return twoName;
	}
	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}

}
