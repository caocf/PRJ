package com.huzhouport.electric.model;

import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.dangerousgoodsportln.model.Port;

public class ElectricVisa implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int visaID;       //签证编号
	private String shipName;  //船舶名称
	private int startingPort; //起运港
	private int arrivalPort;  //目的港
//	private int mark;         //进出港标记
	private String mark;         //进出港标记
	private String time;        //时间
	private String cargoTypes;//载货种类
	private int carrying;         //载重
	private String visaStatus;         //状态
	private Integer visaUser;         //操作者
	private String visaUserName;
	private String visaContent;
	private List<Port> listp=new ArrayList<Port>();
	private QueryCondition queryCondition = new QueryCondition();
	private String condition;
	public int getVisaID() {
		return visaID;
	}
	public void setVisaID(int visaID) {
		this.visaID = visaID;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	public int getStartingPort() {
		return startingPort;
	}
	public void setStartingPort(int startingPort) {
		this.startingPort = startingPort;
	}
	public int getArrivalPort() {
		return arrivalPort;
	}
	public void setArrivalPort(int arrivalPort) {
		this.arrivalPort = arrivalPort;
	}
	
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public QueryCondition getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}
	public List<Port> getListp() {
		return listp;
	}
	public void setListp(List<Port> listp) {
		this.listp = listp;
	}
	public String getVisaStatus() {
		return visaStatus;
	}
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}
	public Integer getVisaUser() {
		return visaUser;
	}
	public void setVisaUser(Integer visaUser) {
		this.visaUser = visaUser;
	}
	public String getVisaUserName() {
		return visaUserName;
	}
	public void setVisaUserName(String visaUserName) {
		this.visaUserName = visaUserName;
	}
	public String getVisaContent() {
		return visaContent;
	}
	public void setVisaContent(String visaContent) {
		this.visaContent = visaContent;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

}
