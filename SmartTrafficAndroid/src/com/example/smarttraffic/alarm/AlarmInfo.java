package com.example.smarttraffic.alarm;

import java.io.Serializable;


public class AlarmInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String data;
	
	private boolean isOpen;				//是否开启
	private boolean isUp;				//是否上车提醒
	private int stationID;				//站点id
	private int lineID;					//线路id
	private String stationName;			//站点名称
	private String lineName;			//线路名称
	
	private int method;					//0、站点;1、时间;2、距离
	private int time;					//提前时间
	
	private boolean isAdvance;			//
	private boolean isCrowed;			//
	private int planUpCar;				//
	
	boolean[] weeks;					//
	String weekSelect;					//

	boolean isSelect;
	
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	public boolean isUp()
	{
		return isUp;
	}

	public void setUp(boolean isUp)
	{
		this.isUp = isUp;
	}

	public int getStationID()
	{
		return stationID;
	}

	public void setStationID(int stationID)
	{
		this.stationID = stationID;
	}

	public int getLineID()
	{
		return lineID;
	}

	public void setLineID(int lineID)
	{
		this.lineID = lineID;
	}

	public String getStationName()
	{
		return stationName;
	}

	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}

	public String getLineName()
	{
		return lineName;
	}

	public void setLineName(String lineName)
	{
		this.lineName = lineName;
	}

	public int getMethod()
	{
		return method;
	}

	public void setMethod(int method)
	{
		this.method = method;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public boolean isAdvance()
	{
		return isAdvance;
	}

	public void setAdvance(boolean isAdvance)
	{
		this.isAdvance = isAdvance;
	}

	public boolean isCrowed()
	{
		return isCrowed;
	}

	public void setCrowed(boolean isCrowed)
	{
		this.isCrowed = isCrowed;
	}

	public int getPlanUpCar()
	{
		return planUpCar;
	}

	public void setPlanUpCar(int planUpCar)
	{
		this.planUpCar = planUpCar;
	}

	public boolean[] getWeeks()
	{
		return weeks;
	}

	public void setWeeks(boolean[] weeks)
	{
		this.weeks = weeks;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	
	int defaultSize=7;
	char y='1';
	char n='0';
	
	public String getWeekSelect()
	{
		weekSelect="";
		
		
		if(weeks!=null)
			for(int i=0;i<defaultSize;i++)
			{
				if(weeks[i])
					weekSelect+=y;
				else
					weekSelect+=n;
			}
			
		return weekSelect;
	}

	public void setWeekSelect(String weekSelect)
	{
		this.weekSelect = weekSelect;
		
		this.weeks=new boolean[defaultSize];
		
		char[] temp=weekSelect.toCharArray();
		
		for(int i=0;i<defaultSize;i++)
		{
			if(temp[i]==y)
				weeks[i]=true;
			else
				weeks[i]=false;
		}
	}

	public boolean isSelect()
	{
		return isSelect;
	}

	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	
}
