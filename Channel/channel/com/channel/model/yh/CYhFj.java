package com.channel.model.yh;

import java.beans.Transient;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


/**
 * CYhFj entity. @author MyEclipse Persistence Tools
 */

public class CYhFj implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer fjid;
	private CYhZxgc CYhZxgc;
	private String resname;
	private String ressize;
	private String respath;
	private String filemd5;
	private Integer creater;
	private Date createtime;
	@Deprecated
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CYhFj() {
	}

	public CYhFj(Integer fjid, com.channel.model.yh.CYhZxgc cYhZxgc,
			String resname, String ressize, String respath, String filemd5,
			Integer creater, Date createtime, Date updatetime) {
		super();
		this.fjid = fjid;
		CYhZxgc = cYhZxgc;
		this.resname = resname;
		this.ressize = ressize;
		this.respath = respath;
		this.filemd5 = filemd5;
		this.creater = creater;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}



	public Integer getFjid() {
		return fjid;
	}

	public void setFjid(Integer fjid) {
		this.fjid = fjid;
	}



	public CYhZxgc getCYhZxgc() {
		return this.CYhZxgc;
	}

	public void setCYhZxgc(CYhZxgc CYhZxgc) {
		this.CYhZxgc = CYhZxgc;
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
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}