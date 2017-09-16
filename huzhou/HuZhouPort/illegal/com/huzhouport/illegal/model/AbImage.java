package com.huzhouport.illegal.model;


public class AbImage implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private int abid;
	private String dir;
	
	
	public String getDir()
	{
		return dir;
	}
	public void setDir(String dir) 
	{
		this.dir = dir;
	}
	public int getAbid()
	{
		return abid;
	}
	public void setAbid(int abid) 
	{
		this.abid = abid;
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
