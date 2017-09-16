package com.example.smarttraffic.common.complain;

import java.util.Date;

public class Complain
{
	public static final String[] SUGGESTION_NAME=new String[]{"默认","出行规划","长途客运","铁路信息","民航信息","汽车维修","驾培服务","新闻快讯"};
	private int type;
	private String title;
	private String content;
	private String contact;
	private String persion;
	private Date date;
	private int id;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getPersion()
	{
		return persion;
	}
	public void setPersion(String persion)
	{
		this.persion = persion;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	

	
}
