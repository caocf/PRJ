package com.example.smarttraffic.common.suggestion;

import java.util.Date;

public class Suggestion
{
	private int id;
	private int type;
	private String title;
	private String content;
	private String contact;
	private String persion;
	private Date date;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
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
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	public String getPersion()
	{
		return persion;
	}
	public void setPersion(String persion)
	{
		this.persion = persion;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	
}
