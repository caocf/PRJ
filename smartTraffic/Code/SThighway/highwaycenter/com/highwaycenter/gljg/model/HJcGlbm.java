package com.highwaycenter.gljg.model;

/**
 * HJcGlbm entity. @author MyEclipse Persistence Tools
 */

public class HJcGlbm implements java.io.Serializable {

	private static final long serialVersionUID = 3172569358579502014L;
	
	private String bmdm;
	private String ssgljgdm;
	private String bmmc;
	
	private String bmdesc;//部门描述
	private Integer ordercolumn;//排序字段

	// Constructors

	/** default constructor */
	public HJcGlbm() {
	}
	
	/** minimal constructor */
	public HJcGlbm(String bmdm) {
		this.bmdm = bmdm;
	}

	/** full constructor */
	public HJcGlbm(String bmdm, String ssgljgdm, String bmmc) {
		this.bmdm = bmdm;
		this.ssgljgdm = ssgljgdm;
		this.bmmc = bmmc;
	
	}

	
	// Property accessors

	public HJcGlbm(String bmdm, String ssgljgdm, String bmmc, String bmdesc,
			Integer ordercolumn) {
		super();
		this.bmdm = bmdm;
		this.ssgljgdm = ssgljgdm;
		this.bmmc = bmmc;
		this.bmdesc = bmdesc;
		this.ordercolumn = ordercolumn;
	}

	public String getBmdm() {
		return this.bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public String getSsgljgdm() {
		return ssgljgdm;
	}

	public void setSsgljgdm(String ssgljgdm) {
		this.ssgljgdm = ssgljgdm;
	}

	public String getBmmc() {
		return this.bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getBmdesc() {
		return bmdesc;
	}

	public void setBmdesc(String bmdesc) {
		this.bmdesc = bmdesc;
	}

	public Integer getOrdercolumn() {
		return ordercolumn;
	}

	public void setOrdercolumn(Integer ordercolumn) {
		this.ordercolumn = ordercolumn;
	}


}