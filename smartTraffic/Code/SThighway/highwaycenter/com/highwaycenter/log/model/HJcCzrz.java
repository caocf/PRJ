package com.highwaycenter.log.model;

import java.sql.Timestamp;

import javax.persistence.Transient;

/**
 * HJcCzrz entity. @author MyEclipse Persistence Tools
 */

public class HJcCzrz implements java.io.Serializable {

	private static final long serialVersionUID = -1560449292775610645L;
	private Integer rzbh;
	private Integer rzlxbh;
	private Integer czr;
	private String rzbt;
	private String rzmc;
	private Timestamp czsj;

	@Transient
	private String ryxm;    //操作人姓名
	@Transient
	private String rzlxmc;  //日志类型名称
	// Constructors

	/** default constructor */
	public HJcCzrz() {
		
	}

	public HJcCzrz(Integer rzbh) {
		this.rzbh = rzbh;
	}


	public HJcCzrz(Integer rzbh, Integer rzlxbh,
			Integer czr, String rzbt, String rzmc, Timestamp czsj, String ryxm,
			String rzlxmc) {
		super();
		this.rzbh = rzbh;
		this.rzlxbh = rzlxbh;
		this.czr = czr;
		this.rzbt = rzbt;
		this.rzmc = rzmc;
		this.czsj = czsj;
		this.ryxm = ryxm;
		this.rzlxmc = rzlxmc;
	}

	public Integer getRzbh() {
		return rzbh;
	}

	public void setRzbh(Integer rzbh) {
		this.rzbh = rzbh;
	}

	public Integer getRzlxbh() {
		return rzlxbh;
	}

	public void setRzlxbh(Integer rzlxbh) {
		this.rzlxbh = rzlxbh;
	}

	public Integer getCzr() {
		return czr;
	}

	public void setCzr(Integer czr) {
		this.czr = czr;
	}

	public String getRzbt() {
		return rzbt;
	}

	public void setRzbt(String rzbt) {
		this.rzbt = rzbt;
	}

	public String getRzmc() {
		return rzmc;
	}

	public void setRzmc(String rzmc) {
		this.rzmc = rzmc;
	}

	public Timestamp getCzsj() {
		return czsj;
	}

	public void setCzsj(Timestamp czsj) {
		this.czsj = czsj;
	}

	public String getRyxm() {
		return ryxm;
	}

	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}

	public String getRzlxmc() {
		return rzlxmc;
	}

	public void setRzlxmc(String rzlxmc) {
		this.rzlxmc = rzlxmc;
	}




	
}