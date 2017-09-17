package com.highwaycenter.glz.model;

import java.sql.Timestamp;

import javax.persistence.Transient;

/**
 * HGlzGlzJbxx entity. @author MyEclipse Persistence Tools
 */

public class HGlzGlzJbxx implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -2629436557253714690L;
	private String id;
	private String orgId;
	private String code;
	private String name;
	private String remark;
	private String address;
	private String telephone;
	private String fax;
	private String supervisor;
	private String email;
	private Timestamp buildDate;
	private String picId;
	
	@Transient
    private String gljgmc;

	// Constructors

	/** default constructor */
	public HGlzGlzJbxx() {
	}

	/** minimal constructor */
	public HGlzGlzJbxx(String id) {
		this.id = id;
	}

	/** full constructor */
	public HGlzGlzJbxx(String id, String orgId, String code, String name,
			String remark, String address, String telephone, String fax,
			String supervisor, String email, Timestamp buildDate, String picId) {
		this.id = id;
		this.orgId = orgId;
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.supervisor = supervisor;
		this.email = email;
		this.buildDate = buildDate;
		this.picId = picId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getBuildDate() {
		return this.buildDate;
	}

	public void setBuildDate(Timestamp buildDate) {
		this.buildDate = buildDate;
	}

	public String getPicId() {
		return this.picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getGljgmc() {
		return gljgmc;
	}

	public void setGljgmc(String gljgmc) {
		this.gljgmc = gljgmc;
	}

}