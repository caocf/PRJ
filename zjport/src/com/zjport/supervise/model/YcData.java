package com.zjport.supervise.model;

/**
 * ForecastData
 */
public class YcData
{
	private int up;
	private int down;
	private int total;
	private String sj;

	public YcData()
	{
		
	}

	public int getUp()
	{
		return up;
	}

	public void setUp(int up)
	{
		this.up = up;
	}

	public int getDown()
	{
		return down;
	}

	public void setDown(int down)
	{
		this.down = down;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public String getSj()
	{
		return sj;
	}

	public void setSj(String sj)
	{
		this.sj = sj;
	}
}