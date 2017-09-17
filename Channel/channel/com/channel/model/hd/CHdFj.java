package com.channel.model.hd;

import java.util.Date;

/**
 * CHdFj entity. @author MyEclipse Persistence Tools
 */

public class CHdFj implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer appid;
	private Integer apptype;
	private String resname;
	private String ressize;
	private String filemd5;
	private String respath;
	private Integer creater;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CHdFj() {
	}

	/** minimal constructor */
	public CHdFj(Integer appid, Integer apptype, String resname,
			String ressize, String filemd5, String respath) {
		this.appid = appid;
		this.apptype = apptype;
		this.resname = resname;
		this.ressize = ressize;
		this.filemd5 = filemd5;
		this.respath = respath;
	}

	/** full constructor */
	public CHdFj(Integer appid, Integer apptype, String resname,
			String ressize, String filemd5, String respath, Integer creater,
			Date createtime, Date updatetime) {
		this.appid = appid;
		this.apptype = apptype;
		this.resname = resname;
		this.ressize = ressize;
		this.filemd5 = filemd5;
		this.respath = respath;
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

	public Integer getAppid() {
		return this.appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	public Integer getApptype() {
		return this.apptype;
	}

	public void setApptype(Integer apptype) {
		this.apptype = apptype;
	}

	public String getResname() {
		return this.resname;
	}

	public void setResname(String resname) {
		this.resname = resname;
	}

	public String getRessize() {
		return this.ressize;
	}

	public void setRessize(String ressize) {
		this.ressize = ressize;
	}

	public String getFilemd5() {
		return this.filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}

	public String getRespath() {
		return this.respath;
	}

	public void setRespath(String respath) {
		this.respath = respath;
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