package com.zjport.supervise.model;

public class Facility
{

	private String name;

	// 船舶相关属性
	private int peccancy; //违章
	private int overdue; //证书过期
	private int arrearage; //缴费

	public Facility() {
	}

	public Facility(String name) {
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPeccancy()
	{
		return peccancy;
	}

	public void setPeccancy(int peccancy)
	{
		this.peccancy = peccancy;
	}

	public int getOverdue()
	{
		return overdue;
	}

	public void setOverdue(int overdue)
	{
		this.overdue = overdue;
	}

	public int getArrearage()
	{
		return arrearage;
	}

	public void setArrearage(int arrearage)
	{
		this.arrearage = arrearage;
	}

}
