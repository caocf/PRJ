package com.huzhouport.electric.model;

import java.util.Date;

import com.huzhouport.common.model.QueryCondition;

public class RegularVisa implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int regularvisaID;         //定期签证提交内容ID
	private int fixedID;  //定期签证记录外键
	private String mark;    //进出港标记
	private String ingTime;    //进出港时间
	private Date reportTime;    //上报时间
	private String cargoTypes;    //载货种类
	private int carrying;         //载重
	private String uniti;
	
	
	private String regularvisaUserName;//
	
	
	public String getRegularvisaUserName() {
		return regularvisaUserName;
	}
	public void setRegularvisaUserName(String regularvisaUserName) {
		this.regularvisaUserName = regularvisaUserName;
	}
	private QueryCondition queryCondition = new QueryCondition();
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public int getRegularvisaID() {
		return regularvisaID;
	}
	public void setRegularvisaID(int regularvisaID) {
		this.regularvisaID = regularvisaID;
	}
	public int getFixedID() {
		return fixedID;
	}
	public void setFixedID(int fixedID) {
		this.fixedID = fixedID;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getIngTime() {
		return ingTime;
	}
	public void setIngTime(String ingTime) {
		this.ingTime = ingTime;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getCargoTypes() {
		return cargoTypes;
	}
	public void setCargoTypes(String cargoTypes) {
		this.cargoTypes = cargoTypes;
	}
	public int getCarrying() {
		return carrying;
	}
	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}
	public String getUniti() {
		return uniti;
	}
	public void setUniti(String uniti) {
		this.uniti = uniti;
	}
	
	
	
}
