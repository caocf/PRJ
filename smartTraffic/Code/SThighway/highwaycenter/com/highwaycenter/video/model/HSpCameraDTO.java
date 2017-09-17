package com.highwaycenter.video.model;

import javax.persistence.Transient;

public class HSpCameraDTO implements java.io.Serializable {

	private static final long serialVersionUID = 5587926727372690446L; ////视频请求webservice返回的对象
	private Integer cameraId;       //摄像机信息主键（ID）
	private String indexCode;       //摄像机编号
	private Integer deviceId;       //隶属于哪一台设备（ID）
	private String name;            //摄像机名称
	private Integer chanNum;        //通道号，从0开始
	private Integer cameraType;		//摄像机类型，0-枪机/1-半球/2-快球/3-云台
	private Integer	 connectType;		//监控点连接协议,0-TCP/1-UDP/2-MCAST/3-RTP
	private Integer streamType	;		//0-主码流 1-子码流
	private Integer	vrmId;	//录像管理服务器（ID）
	private Integer streamId;			// 流媒体服务器（ID）
	private String matrixCode;		//64	矩阵编号
	private Integer pixel;			//摄像头像素：1：普通、2：130万高清、3：200万高清、4：300万高清
	private Integer ptzType	;		//1：全方位云台（带转动和变焦）；2：只有变焦，不带转动3：只有转动，不带变焦4：无云台，无变焦
	private String sound	;		//音频(boolean值转换成String)
	private Integer	privilegeCode;//权限
	private Double longitude;		//经度
	private	Double	latitude;	//纬度
	private Integer	cmsCascadeId;	//下级级联配置id	
	@Transient
	private String networkAddr;//device的net
	
	public String getNetworkAddr() {
		return networkAddr;
	}
	public void setNetworkAddr(String networkAddr) {
		this.networkAddr = networkAddr;
	}
	public Integer getCameraId() {
		return cameraId;
	}
	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}
	public String getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getChanNum() {
		return chanNum;
	}
	public void setChanNum(Integer chanNum) {
		this.chanNum = chanNum;
	}
	public Integer getCameraType() {
		return cameraType;
	}
	public void setCameraType(Integer cameraType) {
		this.cameraType = cameraType;
	}
	public Integer getConnectType() {
		return connectType;
	}
	public void setConnectType(Integer connectType) {
		this.connectType = connectType;
	}
	public Integer getStreamType() {
		return streamType;
	}
	public void setStreamType(Integer streamType) {
		this.streamType = streamType;
	}
	public Integer getVrmId() {
		return vrmId;
	}
	public void setVrmId(Integer vrmId) {
		this.vrmId = vrmId;
	}
	public Integer getStreamId() {
		return streamId;
	}
	public void setStreamId(Integer streamId) {
		this.streamId = streamId;
	}
	public String getMatrixCode() {
		return matrixCode;
	}
	public void setMatrixCode(String matrixCode) {
		this.matrixCode = matrixCode;
	}
	public Integer getPixel() {
		return pixel;
	}
	public void setPixel(Integer pixel) {
		this.pixel = pixel;
	}
	public Integer getPtzType() {
		return ptzType;
	}
	public void setPtzType(Integer ptzType) {
		this.ptzType = ptzType;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public Integer getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(Integer privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Integer getCmsCascadeId() {
		return cmsCascadeId;
	}
	public void setCmsCascadeId(Integer cmsCascadeId) {
		this.cmsCascadeId = cmsCascadeId;
	}

	

}
