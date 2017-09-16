package com.zjport.supervise.model;

import java.sql.Timestamp;

public class TWarnSet
{

	private Integer id;
	private Integer type;
	private String name;
	private String advice;
	private Integer status;
	private Timestamp updatetime;

	public TWarnSet()
	{
	}

	public TWarnSet(Integer type, String name, String advice, Integer status,
			Timestamp updatetime)
	{
		this.type = type;
		this.name = name;
		this.advice = advice;
		this.status = status;
		this.updatetime = updatetime;
	}

	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getType()
	{
		return this.type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAdvice()
	{
		return this.advice;
	}

	public void setAdvice(String advice)
	{
		this.advice = advice;
	}

	public Integer getStatus()
	{
		return this.status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Timestamp getUpdatetime()
	{
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime)
	{
		this.updatetime = updatetime;
	}

}