package com.highwaycenter.bean;

public class LoginResult implements java.io.Serializable {
	//接收视频webservice 返回的对象

	private static final long serialVersionUID = -2634062632310625738L;
    private boolean result;
    private int errorCode;
    private String sessionId;
    private int userId;
    private int controlUnitId;
    private int ipType;
    private int priority;
    private int[] privilegeCode;
    private String outNetCmsUrl;
    private String outNetUserName;
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getControlUnitId() {
		return controlUnitId;
	}
	public void setControlUnitId(int controlUnitId) {
		this.controlUnitId = controlUnitId;
	}
	public int getIpType() {
		return ipType;
	}
	public void setIpType(int ipType) {
		this.ipType = ipType;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int[] getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(int[] privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public String getOutNetCmsUrl() {
		return outNetCmsUrl;
	}
	public void setOutNetCmsUrl(String outNetCmsUrl) {
		this.outNetCmsUrl = outNetCmsUrl;
	}
	public String getOutNetUserName() {
		return outNetUserName;
	}
	public void setOutNetUserName(String outNetUserName) {
		this.outNetUserName = outNetUserName;
	}
	
	
    
    
    
}
