package com.zjport.manage.model;

import javax.persistence.Transient;

/**
 * TJcJs entity.
 */
public class TJcJs
{

	private Integer jsbh;
	private String jsmc;
	
	@Transient
	private String qx;

	public TJcJs()
	{
		super();
	}
	
	public TJcJs(String jsmc)
	{
		this.jsmc = jsmc;
	}

	public Integer getJsbh()
	{
		return this.jsbh;
	}

	public void setJsbh(Integer jsbh)
	{
		this.jsbh = jsbh;
	}

	public String getJsmc()
	{
		return this.jsmc;
	}

	public void setJsmc(String jsmc)
	{
		this.jsmc = jsmc;
	}

	public String getQx()
	{
		return qx;
	}

	public void setQx(String qx)
	{
		this.qx = qx;
	}

}