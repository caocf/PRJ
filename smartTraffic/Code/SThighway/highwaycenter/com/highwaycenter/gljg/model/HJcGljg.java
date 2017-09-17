package com.highwaycenter.gljg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

/**
 * HJcGljg entity. @author MyEclipse Persistence Tools
 */


public class HJcGljg implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -6818715402219059603L;

	private String gljgdm;
	private String gljgmc;           //管理机构名称
	private String sjgljgdm;         //上级管理机构代码
	private String lxdh;             //联系电话
	private String lxdz;             //联系地址
	private String jd;               //经度
	private String wd;               //纬度
	private String jgdesc;           //机构描述
	private Integer sfxzcfjg;            //是否行政处罚机构 0：不是，1 是
	private String orgcode;          //执法部门组织编码
	private String orgname;  //执法部门官方名称
	// Constructors

	/** default constructor */
	public HJcGljg() {
	}

	/** minimal constructor */
	public HJcGljg(String gljgdm) {
		this.gljgdm = gljgdm;
	}

	/** full constructor */
	public HJcGljg(String gljgdm, String gljgmc, String sjgljgdm, String lxdh,
			String lxdz, String jd, String wd) {
		this.gljgdm = gljgdm;
		this.gljgmc = gljgmc;
		this.sjgljgdm = sjgljgdm;
		this.lxdh = lxdh;
		this.lxdz = lxdz;
		this.jd = jd;
		this.wd = wd;
	}
	
	

	public HJcGljg(String gljgdm, String gljgmc, String sjgljgdm, String lxdh,
			String lxdz, String jd, String wd, String jgdesc, int sfxzcfjg) {
		super();
		this.gljgdm = gljgdm;
		this.gljgmc = gljgmc;
		this.sjgljgdm = sjgljgdm;
		this.lxdh = lxdh;
		this.lxdz = lxdz;
		this.jd = jd;
		this.wd = wd;
		this.jgdesc = jgdesc;
		this.sfxzcfjg = sfxzcfjg;
	}

	public String getGljgdm() {
		return this.gljgdm;
	}

	public void setGljgdm(String gljgdm) {
		this.gljgdm = gljgdm;
	}

	public String getGljgmc() {
		return this.gljgmc;
	}

	public void setGljgmc(String gljgmc) {
		this.gljgmc = gljgmc;
	}

	public String getSjgljgdm() {
		return this.sjgljgdm;
	}

	public void setSjgljgdm(String sjgljgdm) {
		this.sjgljgdm = sjgljgdm;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getLxdz() {
		return this.lxdz;
	}

	public void setLxdz(String lxdz) {
		this.lxdz = lxdz;
	}

	public String getJd() {
		return this.jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return this.wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getJgdesc() {
		return jgdesc;
	}

	public void setJgdesc(String jgdesc) {
		this.jgdesc = jgdesc;
	}

	public Integer getSfxzcfjg() {
		return sfxzcfjg;
	}

	public void setSfxzcfjg(Integer sfxzcfjg) {
		this.sfxzcfjg = sfxzcfjg;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

  

}