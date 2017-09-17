package com.channel.model.yh;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * CYhYbb entity. @author MyEclipse Persistence Tools
 */

public class CYhYdjdqk implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ydjdqkid;
	private CYhZxgc CYhZxgc;
	private String dwmc;
	private Date ny;
	private Double bywcje;
	private Integer xmzt;
	private String xmjdqk;
	private String bz;
	private Date createtime;
	private Date updatetime;

	public CYhYdjdqk() {
	}

	public CYhYdjdqk(com.channel.model.yh.CYhZxgc cYhZxgc, String dwmc,
			Date ny, Double bywcje, Integer xmzt, String xmjdqk, String bz,
			Date createtime, Date updatetime) {
		super();
		CYhZxgc = cYhZxgc;
		this.dwmc = dwmc;
		this.ny = ny;
		this.bywcje = bywcje;
		this.xmzt = xmzt;
		this.xmjdqk = xmjdqk;
		this.bz = bz;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public CYhYdjdqk(Integer ydjdqkid, com.channel.model.yh.CYhZxgc cYhZxgc,
			String dwmc, Date ny, Double bywcje, Integer xmzt, String xmjdqk,
			String bz, Date createtime, Date updatetime) {
		super();
		this.ydjdqkid = ydjdqkid;
		CYhZxgc = cYhZxgc;
		this.dwmc = dwmc;
		this.ny = ny;
		this.bywcje = bywcje;
		this.xmzt = xmzt;
		this.xmjdqk = xmjdqk;
		this.bz = bz;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public Integer getYdjdqkid() {
		return ydjdqkid;
	}

	public void setYdjdqkid(Integer ydjdqkid) {
		this.ydjdqkid = ydjdqkid;
	}

	public CYhZxgc getCYhZxgc() {
		return CYhZxgc;
	}

	public void setCYhZxgc(CYhZxgc cYhZxgc) {
		CYhZxgc = cYhZxgc;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public Double getBywcje() {
		return bywcje;
	}

	public void setBywcje(Double bywcje) {
		this.bywcje = bywcje;
	}

	public Integer getXmzt() {
		return xmzt;
	}

	public void setXmzt(Integer xmzt) {
		this.xmzt = xmzt;
	}

	public String getXmjdqk() {
		return xmjdqk;
	}

	public void setXmjdqk(String xmjdqk) {
		this.xmjdqk = xmjdqk;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@JSON(format = "yyyy-MM")
	public Date getNy() {
		return ny;
	}

	public void setNy(Date ny) {
		this.ny = ny;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}