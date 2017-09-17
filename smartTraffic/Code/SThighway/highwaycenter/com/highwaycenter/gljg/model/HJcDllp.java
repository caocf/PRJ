package com.highwaycenter.gljg.model;

import java.sql.Timestamp;


/**
 * HJcDllp entity. @author MyEclipse Persistence Tools
 */

public class HJcDllp implements java.io.Serializable {

	private static final long serialVersionUID = -5858312967744380164L;
	private String lp;
	private Integer rybh;
	private Timestamp dlsj;
	private String dldz;
	private Timestamp scczsj;

	// Constructors

	/** default constructor */
	public HJcDllp() {
	}

	/** minimal constructor */
	public HJcDllp(String lp) {
		this.lp = lp;
	}

	/** full constructor */
	public HJcDllp(String lp,Integer rybh, Timestamp dlsj, String dldz) {
		this.lp = lp;
		this.rybh = rybh;
		this.dlsj = dlsj;
		this.dldz = dldz;
	}
	
	
	
	public HJcDllp(String lp, Integer rybh, Timestamp dlsj, String dldz,
			Timestamp scczsj) {
		this.lp = lp;
		this.rybh = rybh;
		this.dlsj = dlsj;
		this.dldz = dldz;
		this.scczsj = scczsj;
	}

	// Property accessors

	public String getLp() {
		return this.lp;
	}

	public void setLp(String lp) {
		this.lp = lp;
	}



	public Integer getRybh() {
		return rybh;
	}

	public void setRybh(Integer rybh) {
		this.rybh = rybh;
	}

	public Timestamp getDlsj() {
		return this.dlsj;
	}

	public void setDlsj(Timestamp dlsj) {
		this.dlsj = dlsj;
	}

	public String getDldz() {
		return this.dldz;
	}

	public void setDldz(String dldz) {
		this.dldz = dldz;
	}

	public Timestamp getScczsj() {
		return this.scczsj;
	}

	public void setScczsj(Timestamp scczsj) {
		this.scczsj = scczsj;
	}

}