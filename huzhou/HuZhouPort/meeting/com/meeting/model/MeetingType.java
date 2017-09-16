package com.meeting.model;

public class MeetingType 
{
	int id;
	String typename;
	int typeid;
	int capacity;
	
	public void setId(int id)
	{
		this.id=id;
	}
	public int getId()
	{
		return id;
	}
	
	
	public void setTypename(String typename)
	{
		this.typename=typename;
	}
	public String getTypename()
	{
		return typename;
	}
	
	public void setCapacity(int capacity)
	{
		this.capacity=capacity;
	}
	public int getCapacity()
	{
		return capacity;
	}

	public void setTypeid(int typeid)
	{
		this.typeid=typeid;
	}
	public int getTypeid()
	{
		return typeid;
	}
	
}
