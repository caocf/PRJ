package com.huzhouport.wharfSelect.service.webservice.model;

public class WharfWebServiceNode {
	private int id;
	private String mc; // 码头名称
	private String bh; // 码头编号
	private String fzr; // 负责人
	private String fzdh; // 负责电话
	private String jd; // 经度
	private String wd; // 纬度
	private String qy; // 片区

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getFzr() {
		return fzr;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getFzdh() {
		return fzdh;
	}

	public void setFzdh(String fzdh) {
		this.fzdh = fzdh;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getQy() {
		return qy;
	}

	public void setQy(String qy) {
		this.qy = qy;
	}
}
