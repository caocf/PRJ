package com.highwaycenter.role.model;


/**
 * HJcJbjsb entity. @author MyEclipse Persistence Tools
 */

public class HJcJbjsb implements java.io.Serializable {

	private static final long serialVersionUID = 6342623210843437046L;
	// Fields

	private Integer jsbh;
	private String jsmc;


	// Constructors

	/** default constructor */
	public HJcJbjsb() {
	}

	/** minimal constructor */
	public HJcJbjsb(Integer jsbh) {
		this.jsbh = jsbh;
	}

	/** full constructor */
	public HJcJbjsb(Integer jsbh, String jsmc) {
		this.jsbh = jsbh;
		this.jsmc = jsmc;
		
	}

	// Property accessors

	public Integer getJsbh() {
		return this.jsbh;
	}

	public void setJsbh(Integer jsbh) {
		this.jsbh = jsbh;
	}

	public String getJsmc() {
		return this.jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}


}