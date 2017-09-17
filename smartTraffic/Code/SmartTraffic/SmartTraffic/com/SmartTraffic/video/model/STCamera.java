package com.SmartTraffic.video.model;

import javax.persistence.Transient;

public class STCamera implements java.io.Serializable{
	private static final long serialVersionUID = 5089932740884336212L;
	
	private int cameraId;  //监控点ID
	private String deviceName; //摄像头名称
	
	private int deviceId;//设备ID(无用，全是1)
	private int controlunitId;//所属控制中心ID
	private int regionId;//所属区域ID
	private int parentDeviceId;//父设备ID，即编码器ID
	private int categoryId;//类别ID（0-枪机/1-半球/2-快球/3-云台）
	private int connectType;    // 连接类型（0-TCP/1-UDP/2-MCAST/3-RTP）
	private int deviceType;    // 设备类型，摄像头固定为0x3
	private String inport;     // 通道号
	private String longitude;  //标注经度
	private String latitude;   //标注纬度
	@Transient
	private String regionName;
	@Transient
	private String unitName;
	
	
	public STCamera(){
		super();
	}
	public STCamera(int cameraId, String deviceName, int deviceId,
			int controlunitId, int regionId, int parentDeviceId,
			int categoryId, int connectType, int deviceType, String inport) {
		super();
		this.cameraId = cameraId;
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.controlunitId = controlunitId;
		this.regionId = regionId;
		this.parentDeviceId = parentDeviceId;
		this.categoryId = categoryId;
		this.connectType = connectType;
		this.deviceType = deviceType;
		this.inport = inport;
	}
	
	 public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Override
	 public int hashCode() {// 重写hashCode方法
	  return this.cameraId;
	 }
	 
	 @Override
	 public boolean equals(Object obj) {// 重写equals方法
	  if (this == obj) {
	   return true;
	  }
	  if (null != obj && obj instanceof STCamera) {
		  STCamera p = (STCamera) obj;
	   if (this.cameraId == p.getCameraId()) {// 判断name是否相同
	        return true;
	   }
	  }
	  return false;
	 }
	 
	public int getCameraId() {
		return cameraId;
	}
	public void setCameraId(int cameraId) {
		this.cameraId = cameraId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getControlunitId() {
		return controlunitId;
	}
	public void setControlunitId(int controlunitId) {
		this.controlunitId = controlunitId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getParentDeviceId() {
		return parentDeviceId;
	}
	public void setParentDeviceId(int parentDeviceId) {
		this.parentDeviceId = parentDeviceId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getConnectType() {
		return connectType;
	}
	public void setConnectType(int connectType) {
		this.connectType = connectType;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public String getInport() {
		return inport;
	}
	public void setInport(String inport) {
		this.inport = inport;
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
	
	

}
