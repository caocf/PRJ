package com.zjport.supervise.model;

import java.sql.Timestamp;

public class TWater
{

	private String id;
	private String name;
	private Double jd;
	private Double wd;
	private String dqsw;
	private String jjsw;
	private String bzsw;
	private String area;
	private Timestamp gxsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getJd() {
		return jd;
	}

	public void setJd(Double jd) {
		this.jd = jd;
	}

	public Double getWd() {
		return wd;
	}

	public void setWd(Double wd) {
		this.wd = wd;
	}

	public String getDqsw() {
		return dqsw;
	}

	public void setDqsw(String dqsw) {
		this.dqsw = dqsw;
	}

	public String getJjsw() {
		return jjsw;
	}

	public void setJjsw(String jjsw) {
		this.jjsw = jjsw;
	}

	public String getBzsw() {
		return bzsw;
	}

	public void setBzsw(String bzsw) {
		this.bzsw = bzsw;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Timestamp getGxsj() {
		return gxsj;
	}

	public void setGxsj(Timestamp gxsj) {
		this.gxsj = gxsj;
	}
}