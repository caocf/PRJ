package com.zjport.manage.model;

import javax.persistence.Transient;

/**
 * TJcQx entity.
 */

public class TJcQx
{

	private Integer qxbh;
	private String qxmc;
	private Integer fbh;
	
	@Transient
	private String check;

	public TJcQx()
	{
		super();
	}

	public Integer getQxbh()
	{
		return this.qxbh;
	}

	public void setQxbh(Integer qxbh)
	{
		this.qxbh = qxbh;
	}

	public String getQxmc()
	{
		return this.qxmc;
	}

	public void setQxmc(String qxmc)
	{
		this.qxmc = qxmc;
	}

	public Integer getFbh()
	{
		return this.fbh;
	}

	public void setFbh(Integer fbh)
	{
		this.fbh = fbh;
	}

	public String getCheck()
	{
		return check;
	}

	public void setCheck(String check)
	{
		this.check = check;
	}

}