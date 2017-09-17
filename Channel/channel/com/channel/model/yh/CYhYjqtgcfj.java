package com.channel.model.yh;

import java.util.Date;


/**
 * CYhYjqtgcfj entity. @author MyEclipse Persistence Tools
 */

public class CYhYjqtgcfj implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer pid;
	private String fname;
	private String fsize;
	private String fpath;
	private Integer ftype;
	private String fmd5;
	private Integer creater;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CYhYjqtgcfj() {
	}

	/** minimal constructor */
	public CYhYjqtgcfj(Integer pid, String fname, String fsize, String fpath,
			Integer ftype, String fmd5) {
		this.pid = pid;
		this.fname = fname;
		this.fsize = fsize;
		this.fpath = fpath;
		this.ftype = ftype;
		this.fmd5 = fmd5;
	}

	/** full constructor */
	public CYhYjqtgcfj(Integer pid, String fname, String fsize, String fpath,
			Integer ftype, String fmd5, Integer creater, Date createtime,
			Date updatetime) {
		this.pid = pid;
		this.fname = fname;
		this.fsize = fsize;
		this.fpath = fpath;
		this.ftype = ftype;
		this.fmd5 = fmd5;
		this.creater = creater;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFsize() {
		return this.fsize;
	}

	public void setFsize(String fsize) {
		this.fsize = fsize;
	}

	public String getFpath() {
		return this.fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	public String getFmd5() {
		return this.fmd5;
	}

	public void setFmd5(String fmd5) {
		this.fmd5 = fmd5;
	}

	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}