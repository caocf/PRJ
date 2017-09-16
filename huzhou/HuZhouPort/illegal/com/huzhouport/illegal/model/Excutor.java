package com.huzhouport.illegal.model;


public class Excutor implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String pot;
	private String state;
	private String potid;
	
	public String getPotid()
	{
		return potid;
	}
	public void setPotid(String potid) 
	{
		this.potid = potid;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getPot()
	{
		return pot;
	}
	public void setPot(String pot) 
	{
		this.pot = pot;
	}
	
	public int getId()
	{
		return id;
		
	}
	public void setId(int id)
	{
		this.id=id;
	}
}
