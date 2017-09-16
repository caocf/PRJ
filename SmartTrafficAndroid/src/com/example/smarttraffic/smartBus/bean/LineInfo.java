package com.example.smarttraffic.smartBus.bean;

/**
 * 线路简短信息
 * @author Administrator zhou
 *
 */
public class LineInfo
{
	int historyID;
	int id;							//线路id
	String name;					//线路名称
	String start;					//线路起点
	String end;						//线路终点
	String startTime;				//始发时间
	String endTime;					//到站时间
	boolean isSelect;				//是否选择（用于配合adapter选择）
	int favorID;
	
	int userid;
	int direct;
	
	public int getDirect()
	{
		return direct;
	}
	public void setDirect(int direct)
	{
		this.direct = direct;
	}
	public int getUserid()
	{
		return userid;
	}
	public void setUserid(int userid)
	{
		this.userid = userid;
	}
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
	public String getStart()
	{
		return start;
	}
	public void setStart(String start)
	{
		this.start = start;
	}
	public String getEnd()
	{
		return end;
	}
	public void setEnd(String end)
	{
		this.end = end;
	}
	public String getStartTime()
	{
		return startTime;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	public int getHistoryID()
	{
		return historyID;
	}
	public void setHistoryID(int historyID)
	{
		this.historyID = historyID;
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
