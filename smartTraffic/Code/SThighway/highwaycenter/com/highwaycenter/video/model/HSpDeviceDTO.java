package com.highwaycenter.video.model;


public class HSpDeviceDTO   implements java.io.Serializable { ////视频请求webservice返回的对象

	private static final long serialVersionUID = -1150637145065598087L;
	private Integer deviceId;
	private String indexCode;
	private Integer typeCode;
	private String name;
	private Integer registerType;
	private String networkAddr;
	private Integer networkPort;
	private String userName;
	private String userPwd;
	private String dnsAddr;
	private Integer dnsPort;
	private String serialNo;
	private Integer talkChanCount;
	private Integer privilegeCode;
	private Integer curState;
	private Integer controlUnitId;
	private Integer pagServerId;
	

	

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRegisterType() {
		return registerType;
	}

	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}

	public String getNetworkAddr() {
		return networkAddr;
	}

	public void setNetworkAddr(String networkAddr) {
		this.networkAddr = networkAddr;
	}

	public Integer getNetworkPort() {
		return networkPort;
	}

	public void setNetworkPort(Integer networkPort) {
		this.networkPort = networkPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getDnsAddr() {
		return dnsAddr;
	}

	public void setDnsAddr(String dnsAddr) {
		this.dnsAddr = dnsAddr;
	}

	public Integer getDnsPort() {
		return dnsPort;
	}

	public void setDnsPort(Integer dnsPort) {
		this.dnsPort = dnsPort;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getTalkChanCount() {
		return talkChanCount;
	}

	public void setTalkChanCount(Integer talkChanCount) {
		this.talkChanCount = talkChanCount;
	}

	public Integer getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(Integer privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public Integer getCurState() {
		return curState;
	}

	public void setCurState(Integer curState) {
		this.curState = curState;
	}

	public Integer getControlUnitId() {
		return controlUnitId;
	}

	public void setControlUnitId(Integer controlUnitId) {
		this.controlUnitId = controlUnitId;
	}

	public Integer getPagServerId() {
		return pagServerId;
	}

	public void setPagServerId(Integer pagServerId) {
		this.pagServerId = pagServerId;
	}
	
	
	
	

}
