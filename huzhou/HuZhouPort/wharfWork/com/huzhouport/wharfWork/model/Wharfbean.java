package com.huzhouport.wharfWork.model;

import java.io.File;
import java.util.List;

public class Wharfbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id; 
	private int wharfID; //码头编号
	private String wharfName; //码头名称
	private int wharfWorkNorm; //码头作业定量
	private int wharfWorkSurplus; //码头剩余定量
	private String name; //负责人
	private String shipName; //船舶名称
    private int startingPort; //起运港
    private String startingPortName;//起运港名称
    private int arrivalPort;  //目的港
    private String arrivalPortName; //目的港名称
    private int portMart;     //进出港标志
    private String cargoTypes; //货物名称
    private int carrying;  //载重
    private String uniti; //载重单位
    private String workTime; //作业时间
    private int locationID; //作业位置
    private String longitude; //经度
	private String latitude; //纬度
	private String locationName; //地址
    private String workPhoto; //作业照片
    private String declareTime;//申报时间
    
    private List<File> ef;//附件列表
	private List<String> efFileName;
	private List<String> efContentType;
    

	public int getWharfID(){
		return wharfID;
	}
	public void setWharfID(int wharfID) {
		this.wharfID = wharfID;
	}
	public String getWharfName() {
		return wharfName;
	}
	public void setWharfName(String wharfName) {
		this.wharfName = wharfName;
	}
	public int getWharfWorkNorm() {
		return wharfWorkNorm;
	}
	public void setWharfWorkNorm(int wharfWorkNorm) {
		this.wharfWorkNorm = wharfWorkNorm;
	}
	public int getWharfWorkSurplus() {
		return wharfWorkSurplus;
	}
	public void setWharfWorkSurplus(int wharfWorkSurplus) {
		this.wharfWorkSurplus = wharfWorkSurplus;
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
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeclareTime() {
		return declareTime;
	}
	public void setDeclareTime(String declareTime) {
		this.declareTime = declareTime;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUniti() {
		return uniti;
	}
	public void setUniti(String uniti) {
		this.uniti = uniti;
	}
	public List<File> getEf() {
		return ef;
	}
	public void setEf(List<File> ef) {
		this.ef = ef;
	}
	public List<String> getEfFileName() {
		return efFileName;
	}
	public void setEfFileName(List<String> efFileName) {
		this.efFileName = efFileName;
	}
	public List<String> getEfContentType() {
		return efContentType;
	}
	public void setEfContentType(List<String> efContentType) {
		this.efContentType = efContentType;
	}
   
    
    
}
