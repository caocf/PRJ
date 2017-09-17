package com.channel.model.user;

import java.util.Date;


/**
 * CZdUserjstatus entity. @author MyEclipse Persistence Tools
 */

public class CZdUserjstatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private String typedesc;
	private Date createtime;
	private Date updatetime;
	private Integer valid;

	// Constructors

	/** default constructor */
	public CZdUserjstatus() {
	}

	/** full constructor */
	public CZdUserjstatus(String type, String typedesc, Date createtime,
			Date updatetime, Integer valid) {
		this.type = type;
		this.typedesc = typedesc;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.valid = valid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypedesc() {
		return this.typedesc;
	}

	public void setTypedesc(String typedesc) {
		this.typedesc = typedesc;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getValid() {
		return valid;
	}
	
	public void setValid(Integer valid) {
		this.valid = valid;
	}

}