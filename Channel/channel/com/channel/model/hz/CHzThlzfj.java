package com.channel.model.hz;

import java.util.Date;

/**
 * CHzThlzfj entity. @author MyEclipse Persistence Tools
 */

public class CHzThlzfj implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer pid;
	private String resname;
	private String ressize;
	private String respath;
	private String filemd5;
	private Integer creater;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CHzThlzfj() {
	}

	/** minimal constructor */
	public CHzThlzfj(Integer id, Integer pid, String resname, String ressize,
			String respath, String filemd5) {
		this.id = id;
		this.pid = pid;
		this.resname = resname;
		this.ressize = ressize;
		this.respath = respath;
		this.filemd5 = filemd5;
	}

	/** full constructor */
	public CHzThlzfj(Integer id, Integer pid, String resname, String ressize,
			String respath, String filemd5, Integer creater,
			Date createtime, Date updatetime) {
		this.id = id;
		this.pid = pid;
		this.resname = resname;
		this.ressize = ressize;
		this.respath = respath;
		this.filemd5 = filemd5;
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

	public String getRespath() {
		return this.respath;
	}

	public void setRespath(String respath) {
		this.respath = respath;
	}

	public String getFilemd5() {
		return this.filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
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