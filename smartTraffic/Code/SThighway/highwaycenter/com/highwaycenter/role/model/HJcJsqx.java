package com.highwaycenter.role.model;

/**
 * HJcJsqx entity. @author MyEclipse Persistence Tools
 */

public class HJcJsqx implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4628677230467450497L;
	private Integer bh;
	private HJcJbqx HJcJbqx;
	private HJcJbjsb HJcJbjsb;

	// Constructors

	/** default constructor */
	public HJcJsqx() {
	}

	/** minimal constructor */
	public HJcJsqx(Integer bh) {
		this.bh = bh;
	}

	/** full constructor */
	public HJcJsqx(Integer bh, HJcJbqx HJcJbqx, HJcJbjsb HJcJbjsb) {
		this.bh = bh;
		this.HJcJbqx = HJcJbqx;
		this.HJcJbjsb = HJcJbjsb;
	}

	// Property accessors
	public HJcJsqx(HJcJbqx HJcJbqx, HJcJbjsb HJcJbjsb) {
		this.HJcJbqx = HJcJbqx;
		this.HJcJbjsb = HJcJbjsb;
	}

	public Integer getBh() {
		return this.bh;
	}

	public void setBh(Integer bh) {
		this.bh = bh;
	}

	public HJcJbqx getHJcJbqx() {
		return this.HJcJbqx;
	}

	public void setHJcJbqx(HJcJbqx HJcJbqx) {
		this.HJcJbqx = HJcJbqx;
	}

	public HJcJbjsb getHJcJbjsb() {
		return this.HJcJbjsb;
	}

	public void setHJcJbjsb(HJcJbjsb HJcJbjsb) {
		this.HJcJbjsb = HJcJbjsb;
	}

}