package com.channel.model.yh;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * CYhZxgc entity. @author MyEclipse Persistence Tools
 */

public class CYhZxgc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer zxgcid;
	private String gcmc;
	private Date starttime;
	private Date endtime;
	private String jsdw;
	private String jldw;
	private String sjdw;
	private String sgdw;
	private Integer gldw;
	private Double tz;
	private Integer status;
	private String bz;
	private Date createtime;
	private Date updatetime;
	private Set CYhFjs = new HashSet(0);
	private Set CYhYdjdqks = new HashSet(0);

	// Constructors
	public CYhZxgc() {
	}

	public CYhZxgc(String gcmc, Date starttime, Date endtime, String jsdw, String jldw, String sjdw, String sgdw, Integer gldw, Double tz, Integer status, String bz, Date createtime, Date updatetime) {
		this.gcmc = gcmc;
		this.starttime = starttime;
		this.endtime = endtime;
		this.jsdw = jsdw;
		this.jldw = jldw;
		this.sjdw = sjdw;
		this.sgdw = sgdw;
		this.gldw = gldw;
		this.tz = tz;
		this.status = status;
		this.bz = bz;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public CYhZxgc(String gcmc, Date starttime, Date endtime, String jsdw, String jldw, String sjdw, String sgdw, Integer gldw, Double tz, Integer status, String bz, Date createtime, Date updatetime, Set CYhFjs, Set CYhYdjdqks) {
		this.gcmc = gcmc;
		this.starttime = starttime;
		this.endtime = endtime;
		this.jsdw = jsdw;
		this.jldw = jldw;
		this.sjdw = sjdw;
		this.sgdw = sgdw;
		this.gldw = gldw;
		this.tz = tz;
		this.status = status;
		this.bz = bz;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.CYhFjs = CYhFjs;
		this.CYhYdjdqks = CYhYdjdqks;
	}

	public CYhZxgc(Integer zxgcid, String gcmc, Date starttime, Date endtime, String jsdw, String jldw, String sjdw, String sgdw, Integer gldw, Double tz, Integer status, String bz, Date createtime, Date updatetime) {
		this.zxgcid = zxgcid;
		this.gcmc = gcmc;
		this.starttime = starttime;
		this.endtime = endtime;
		this.jsdw = jsdw;
		this.jldw = jldw;
		this.sjdw = sjdw;
		this.sgdw = sgdw;
		this.gldw = gldw;
		this.tz = tz;
		this.status = status;
		this.bz = bz;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public CYhZxgc(Integer zxgcid, String gcmc, Date starttime, Date endtime, String jsdw, String jldw, String sjdw, String sgdw, Integer gldw, Double tz, Integer status, String bz, Date createtime, Date updatetime, Set CYhFjs, Set CYhYdjdqks) {
		this.zxgcid = zxgcid;
		this.gcmc = gcmc;
		this.starttime = starttime;
		this.endtime = endtime;
		this.jsdw = jsdw;
		this.jldw = jldw;
		this.sjdw = sjdw;
		this.sgdw = sgdw;
		this.gldw = gldw;
		this.tz = tz;
		this.status = status;
		this.bz = bz;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.CYhFjs = CYhFjs;
		this.CYhYdjdqks = CYhYdjdqks;
	}

	public String getBz() {
		return bz;
	}



	public void setBz(String bz) {
		this.bz = bz;
	}


	public Integer getGldw() {
		return gldw;
	}

	public void setGldw(Integer gldw) {
		this.gldw = gldw;
	}

	public String getSjdw() {
		return sjdw;
	}

	public void setSjdw(String sjdw) {
		this.sjdw = sjdw;
	}

	public String getSgdw() {
		return sgdw;
	}

	public void setSgdw(String sgdw) {
		this.sgdw = sgdw;
	}

	public Integer getZxgcid() {
		return this.zxgcid;
	}

	public void setZxgcid(Integer zxgcid) {
		this.zxgcid = zxgcid;
	}

	public String getGcmc() {
		return this.gcmc;
	}

	public void setGcmc(String gcmc) {
		this.gcmc = gcmc;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getJldw() {
		return this.jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public String getJsdw() {
		return jsdw;
	}

	public void setJsdw(String jsdw) {
		this.jsdw = jsdw;
	}

	public Double getTz() {
		return this.tz;
	}

	public void setTz(Double tz) {
		this.tz = tz;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Set getCYhFjs() {
		return this.CYhFjs;
	}

	public void setCYhFjs(Set CYhFjs) {
		this.CYhFjs = CYhFjs;
	}

	public Set getCYhYdjdqks() {
		return CYhYdjdqks;
	}

	public void setCYhYdjdqks(Set cYhYdjdqks) {
		CYhYdjdqks = cYhYdjdqks;
	}

}