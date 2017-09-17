package com.highwaycenter.log.model;

import java.util.HashSet;
import java.util.Set;

/**
 * HJcRzlx entity. @author MyEclipse Persistence Tools
 */

public class HJcRzlx implements java.io.Serializable {

	// Fields

	private Integer rzlxbh;
	private String rzlxmc;

	// Constructors

	/** default constructor */
	public HJcRzlx() {
	}

	/** full constructor */
	public HJcRzlx(String rzlxmc) {
		this.rzlxmc = rzlxmc;
		
	}

	// Property accessors

	public Integer getRzlxbh() {
		return this.rzlxbh;
	}

	public void setRzlxbh(Integer rzlxbh) {
		this.rzlxbh = rzlxbh;
	}

	public String getRzlxmc() {
		return this.rzlxmc;
	}

	public void setRzlxmc(String rzlxmc) {
		this.rzlxmc = rzlxmc;
	}



}