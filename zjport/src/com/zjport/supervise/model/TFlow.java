package com.zjport.supervise.model;

import javax.persistence.Transient;

public class TFlow
{

	private String id;
	private String name;
	private Double jd;
	private Double wd;
	private String area;

	@Transient
	private int zll;

	@Transient
	private int up;

	@Transient
	private int down;

	@Transient
	private String gxsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Double getJd()
	{
		return this.jd;
	}

	public void setJd(Double jd)
	{
		this.jd = jd;
	}

	public Double getWd()
	{
		return this.wd;
	}

	public void setWd(Double wd)
	{
		this.wd = wd;
	}

	public int getZll() {
		return zll;
	}

	public void setZll(int zll) {
		this.zll = zll;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}
}