package com.example.smarttraffic.smartBus.bean;

/**
 * 站点
 * @author Administrator
 *
 */
public class StationInfo
{
	private int id;						//站点id
	private String name;				//站点名称
	private double lantitude;			//站点经度
	private double longtitude;			//站点纬度
	private String[] lines;				//站点线路
	
	boolean isSelect;					//是否选择（配合adapter选择）
	
	int favorID;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String[] getLines()
	{
		return lines;
	}
	public void setLines(String[] lines)
	{
		this.lines = lines;
	}
	public double getLantitude()
	{
		return lantitude;
	}
	public void setLantitude(double lantitude)
	{
		this.lantitude = lantitude;
	}
	public double getLongtitude()
	{
		return longtitude;
	}
	public void setLongtitude(double longtitude)
	{
		this.longtitude = longtitude;
	}
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	public int getFavorID()
	{
		return favorID;
	}
	public void setFavorID(int favorID)
	{
		this.favorID = favorID;
	}
	
}
