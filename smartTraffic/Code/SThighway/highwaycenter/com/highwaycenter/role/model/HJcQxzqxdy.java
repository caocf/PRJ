package com.highwaycenter.role.model;

/**
 * HJcQxzqxdy entity. @author MyEclipse Persistence Tools
 */

public class HJcQxzqxdy implements java.io.Serializable {

	private static final long serialVersionUID = -9212260073611905870L;
	private Integer qxbh;
	private Integer qxzbh;
	

	// Constructors

	/** default constructor */
	public HJcQxzqxdy() {
	}


	public HJcQxzqxdy(Integer qxbh, Integer qxzbh) {
		super();
		this.qxbh = qxbh;
		this.qxzbh = qxzbh;
	}


	public Integer getQxbh() {
		return qxbh;
	}


	public void setQxbh(Integer qxbh) {
		this.qxbh = qxbh;
	}


	public Integer getQxzbh() {
		return qxzbh;
	}


	public void setQxzbh(Integer qxzbh) {
		this.qxzbh = qxzbh;
	}


	// Property accessors


}