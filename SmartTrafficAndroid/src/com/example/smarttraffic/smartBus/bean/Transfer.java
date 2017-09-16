package com.example.smarttraffic.smartBus.bean;

import java.util.List;

/**
 * 换乘
 * @author Administrator zhou
 *
 */
public class Transfer
{
	private double distance;
	private List<String> lineList;
	private List<TransferLine> transferLines;
	private double price;
	private String time;
	
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public double getDistance()
	{
		return distance;
	}
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	public List<String> getLineList()
	{
		return lineList;
	}
	public void setLineList(List<String> lineList)
	{
		this.lineList = lineList;
	}
	public List<TransferLine> getTransferLines()
	{
		return transferLines;
	}
	public void setTransferLines(List<TransferLine> transferLines)
	{
		this.transferLines = transferLines;
	}
}

