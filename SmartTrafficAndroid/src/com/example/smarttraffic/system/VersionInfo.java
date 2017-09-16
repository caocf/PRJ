package com.example.smarttraffic.system;

public class VersionInfo 
{
	private int versionID;
	private String versionName;
	private String versionDescribe;
	private String versionUpdateLog;
	private String versionDebugLog;
	private String versionUpdateTime;
	private String versionDownUrl;
	private int versionKind;
	
	public int getVersionID() {
		return versionID;
	}
	public void setVersionID(int versionID) {
		this.versionID = versionID;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionDescribe() {
		return versionDescribe;
	}
	public void setVersionDescribe(String versionDescribe) {
		this.versionDescribe = versionDescribe;
	}
	public String getVersionUpdateLog() {
		return versionUpdateLog;
	}
	public void setVersionUpdateLog(String versionUpdateLog) {
		this.versionUpdateLog = versionUpdateLog;
	}
	public String getVersionDebugLog() {
		return versionDebugLog;
	}
	public void setVersionDebugLog(String versionDebugLog) {
		this.versionDebugLog = versionDebugLog;
	}
	public String getVersionUpdateTime() {
		return versionUpdateTime;
	}
	public void setVersionUpdateTime(String versionUpdateTime) {
		this.versionUpdateTime = versionUpdateTime;
	}
	public String getVersionDownUrl() {
		return versionDownUrl;
	}
	public void setVersionDownUrl(String versionDownUrl) {
		this.versionDownUrl = versionDownUrl;
	}
	public int getVersionKind() {
		return versionKind;
	}
	public void setVersionKind(int versionKind) {
		this.versionKind = versionKind;
	}
	
	@Override
	public String toString()
	{
		return versionID+" "+versionName+" "+versionUpdateLog+" "+versionDebugLog+" "+versionUpdateTime;
	}
}
