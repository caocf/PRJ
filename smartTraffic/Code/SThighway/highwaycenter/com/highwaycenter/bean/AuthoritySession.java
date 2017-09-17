package com.highwaycenter.bean;


public class AuthoritySession {
	private Integer rybh;//人员编号
	private Integer jsbh;  //角色编号
	private String autoStr;//权限string
	
	
	public AuthoritySession(Integer rybh, Integer jsbh, String autoStr) {
		super();
		this.rybh = rybh;
		this.jsbh = jsbh;
		this.autoStr = autoStr;
	}
	public Integer getRybh() {
		return rybh;
	}
	public void setRybh(Integer rybh) {
		this.rybh = rybh;
	}
	public Integer getJsbh() {
		return jsbh;
	}
	public void setJsbh(Integer jsbh) {
		this.jsbh = jsbh;
	}
	public String getAutoStr() {
		return autoStr;
	}
	public void setAutoStr(String autoStr) {
		this.autoStr = autoStr;
	}

	

}
