package com.sts.liveness.model;

import java.util.Date;

/**
 * 用户活跃度
 * @author Administrator
 *
 */
public class Liveness 
{
	int livenessId;
	String ip;
	String imei;
	String logindate;
	String version;
	String equitment;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEquitment() {
		return equitment;
	}
	public void setEquitment(String equitment) {
		this.equitment = equitment;
	}
	public int getLivenessId() {
		return livenessId;
	}
	public void setLivenessId(int livenessId) {
		this.livenessId = livenessId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getLogindate() {
		return logindate;
	}
	public void setLogindate(String logindate) {
		this.logindate = logindate;
	}
}
