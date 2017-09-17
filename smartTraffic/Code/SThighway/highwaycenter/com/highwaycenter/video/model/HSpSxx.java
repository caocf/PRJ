package com.highwaycenter.video.model;

import javax.persistence.Transient;

public class HSpSxx implements java.io.Serializable {

	private static final long serialVersionUID = -5455245272251237686L;

	private Integer sxxId;//上下行编号
	private String lxdm; //路线代码
	private String fxm;//方向名
	private Integer sxx;//上下行 代码 0：上行，1：下行
	
	@Transient
	private String sxxname;//上下行名称
	public HSpSxx(){
		
	}
	public HSpSxx(Integer sxxId, String lxdm, String fxm, Integer sxx) {
		super();
		this.sxxId = sxxId;
		this.lxdm = lxdm;
		this.fxm = fxm;
		this.sxx = sxx;
	}
	public Integer getSxxId() {
		return sxxId;
	}
	public void setSxxId(Integer sxxId) {
		this.sxxId = sxxId;
	}
	public String getLxdm() {
		return lxdm;
	}
	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}
	public String getFxm() {
		return fxm;
	}
	public void setFxm(String fxm) {
		this.fxm = fxm;
	}
	public Integer getSxx() {
		return sxx;
	}
	public void setSxx(Integer sxx) {
		this.sxx = sxx;
	}
	public String getSxxname() {
		return sxxname;
	}
	public void setSxxname(String sxxname) {
		this.sxxname = sxxname;
	}
	
	

}
