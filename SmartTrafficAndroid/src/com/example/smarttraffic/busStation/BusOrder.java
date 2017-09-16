package com.example.smarttraffic.busStation;


public class BusOrder
{
	int order;
	
	String planStartTime;
	String planEndTime;
	String realStartTime;
	String realEndTime;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getPlanStartTime()
	{
		return planStartTime;
	}
	public void setPlanStartTime(String planStartTime)
	{
		this.planStartTime = planStartTime;
	}
	public String getPlanEndTime()
	{
		return planEndTime;
	}
	public void setPlanEndTime(String planEndTime)
	{
		this.planEndTime = planEndTime;
	}
	public String getRealStartTime()
	{
		return realStartTime;
	}
	public void setRealStartTime(String realStartTime)
	{
		this.realStartTime = realStartTime;
	}
	public String getRealEndTime()
	{
		return realEndTime;
	}
	public void setRealEndTime(String realEndTime)
	{
		this.realEndTime = realEndTime;
	}
	
	
}
