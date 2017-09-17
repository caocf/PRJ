package com.highwaycenter.gljg.model;

import javax.persistence.Transient;

public class HJcRybm implements java.io.Serializable {

	private static final long serialVersionUID = -2216744055395752145L;
	private Integer rybmbh;//人员部门编号
	private Integer rybh;//人员编号
	private String bmbh;//部门编号
	private Integer ryordercolumn;//排序字段
	private String zwbh;//多职位编号
	private String bgsdh;//
	
	
	@Transient
	private String zwmc;  //职位名称
	@Transient
	private String bmmc;  //部门名称
	@Transient
	private String ssjgbh; //所属机构编号
	@Transient
	private String ssjgmc;  //所属机构名称
	
	public HJcRybm(){
		
	}
	
	public HJcRybm(Integer rybh, String bmbh,
			Integer ryordercolumn) {
		super();
		
		this.rybh = rybh;
		this.bmbh = bmbh;
		this.ryordercolumn = ryordercolumn;
	}
	public HJcRybm(Integer rybmbh, Integer rybh, String bmbh,
			Integer ryordercolumn) {
		super();
		this.rybmbh = rybmbh;
		this.rybh = rybh;
		this.bmbh = bmbh;
		this.ryordercolumn = ryordercolumn;
	}
	
	
	public HJcRybm(Integer rybmbh, Integer rybh, String bmbh,
			Integer ryordercolumn, String zwbh, String bgsdh) {
		super();
		this.rybmbh = rybmbh;
		this.rybh = rybh;
		this.bmbh = bmbh;
		this.ryordercolumn = ryordercolumn;
		this.zwbh = zwbh;
		this.bgsdh = bgsdh;
	}

	public Integer getRybmbh() {
		return rybmbh;
	}
	public void setRybmbh(Integer rybmbh) {
		this.rybmbh = rybmbh;
	}
	public Integer getRybh() {
		return rybh;
	}
	public void setRybh(Integer rybh) {
		this.rybh = rybh;
	}
	public String getBmbh() {
		return bmbh;
	}
	public void setBmbh(String bmbh) {
		this.bmbh = bmbh;
	}
	public Integer getRyordercolumn() {
		return ryordercolumn;
	}
	public void setRyordercolumn(Integer ryordercolumn) {
		this.ryordercolumn = ryordercolumn;
	}

	public String getZwbh() {
		return zwbh;
	}

	public void setZwbh(String zwbh) {
		this.zwbh = zwbh;
	}

	public String getBgsdh() {
		return bgsdh;
	}

	public void setBgsdh(String bgsdh) {
		this.bgsdh = bgsdh;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getSsjgbh() {
		return ssjgbh;
	}

	public void setSsjgbh(String ssjgbh) {
		this.ssjgbh = ssjgbh;
	}

	public String getSsjgmc() {
		return ssjgmc;
	}

	public void setSsjgmc(String ssjgmc) {
		this.ssjgmc = ssjgmc;
	}
	
	

}
