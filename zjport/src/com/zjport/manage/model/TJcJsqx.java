package com.zjport.manage.model;

/**
 * TJcJsqx entity.
 */
public class TJcJsqx
{

	private Integer bh;
	private Integer jsbh;
	private Integer qxbh;

	public TJcJsqx()
	{
		super();
	}

	public TJcJsqx(Integer jsbh, Integer qxbh)
	{
		this.jsbh = jsbh;
		this.qxbh = qxbh;
	}

	public Integer getBh()
	{
		return this.bh;
	}

	public void setBh(Integer bh)
	{
		this.bh = bh;
	}

	public Integer getJsbh()
	{
		return this.jsbh;
	}

	public void setJsbh(Integer jsbh)
	{
		this.jsbh = jsbh;
	}

	public Integer getQxbh()
	{
		return this.qxbh;
	}

	public void setQxbh(Integer qxbh)
	{
		this.qxbh = qxbh;
	}

}