package com.huzhouport.wharfWork.model;

public class WharfWork implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id; //编号 
    private int wharfWorkID; //码头编号
    private String shipName; //船舶名称
    private int startingPort; //起运港
    private int arrivalPort;  //目的港
    private int portMart;     //进出港标志
    private String cargoTypes; //货物名称
    private int carrying;  //载重
    private String uniti; //载重单位
    private String workTime; //作业时间
    private int locationID; //作业位置
    private String workPhoto; //作业照片
    private String declareTime;//申报时间
    private String name; //责任人
	public int getWharfWorkID() {
		return wharfWorkID;
	}
	public void setWharfWorkID(int wharfWorkID) {
		this.wharfWorkID = wharfWorkID;
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
	public int getPortMart() {
		return portMart;
	}
	public void setPortMart(int portMart) {
		this.portMart = portMart;
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
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getWorkPhoto() {
		return workPhoto;
	}
	public void setWorkPhoto(String workPhoto) {
		this.workPhoto = workPhoto;
	}
	public String getDeclareTime() {
		return declareTime;
	}
	public void setDeclareTime(String declareTime) {
		this.declareTime = declareTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUniti() {
		return uniti;
	}
	public void setUniti(String uniti) {
		this.uniti = uniti;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    
    

}
