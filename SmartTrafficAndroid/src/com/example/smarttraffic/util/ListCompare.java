package com.example.smarttraffic.util;

import java.util.Comparator;

import com.example.smarttraffic.airport.AirTickets;
import com.example.smarttraffic.busStation.Ticket;
import com.example.smarttraffic.train.TrainTickets;

/**
 * 列表排序
 * 长途、民航、铁路票务排序
 * @author Administrator zhou
 *
 */
public class ListCompare 
{
	public CompareBusTicketsByPrice getCompareBusTicketsByPrice(int type)
	{
		return new CompareBusTicketsByPrice(type);
	}
	
	public CompareBusTicketByTime getCompareBusTicketsByTime(int type)
	{
		return new CompareBusTicketByTime(type);
	}
	
	public CompareAirTicketByTime getCompareAirTicketsByTime(int type)
	{
		return new CompareAirTicketByTime(type);
	}
	
	public CompareAirTicketsByPrice getCompareAirTicketsByPrice(int type)
	{
		return new CompareAirTicketsByPrice(type);
	}
	
	public CompareTrainTicketByTime getCompareTrainTicketsByTime(int type)
	{
		return new CompareTrainTicketByTime(type);
	}
	
	public CompareTrainTicketsByPrice getCompareTrainTicketsByPrice(int type)
	{
		return new CompareTrainTicketsByPrice(type);
	}
	
	
}

/**
 * 长途按票价升降排序
 * @author Administrator zhou
 *
 */
class CompareBusTicketsByPrice implements Comparator<Ticket>
{
	int type;
	public CompareBusTicketsByPrice(int type)
	{
		this.type=type;
	}
	
	@Override
	public int compare(Ticket lhs, Ticket rhs) {	
		if(type==1)
			return lhs.getPrice()<rhs.getPrice()?1:-1;
		else
			return lhs.getPrice()>rhs.getPrice()?1:-1;
	}		
}

/**
 * 长途票务按时间排序
 * @author Administrator zhou
 *
 */
class CompareBusTicketByTime implements Comparator<Ticket>
{
	int type;
	public CompareBusTicketByTime(int type)
	{
		this.type=type;
	}
	
	@Override
	public int compare(Ticket lhs, Ticket rhs) {	
		if(type==1)
			return lhs.getLeaveTime().compareTo(rhs.getLeaveTime());
		else
			return -lhs.getLeaveTime().compareTo(rhs.getLeaveTime());
	}
}

/**
 * 民航票务按价格排序
 * @author Administrator zhou 
 *
 */
class CompareAirTicketsByPrice implements Comparator<AirTickets>
{
	int type;
	public CompareAirTicketsByPrice(int type)
	{
		this.type=type;
	}
	
	@Override
	public int compare(AirTickets lhs, AirTickets rhs) {	
		if(type==1)
			return lhs.getEconomy()<rhs.getEconomy()?1:-1;
		else
			return lhs.getEconomy()>rhs.getEconomy()?1:-1;
	}		
}

/**
 * 民航票务按时间排序
 * @author Administrator zhou 
 *
 */
class CompareAirTicketByTime implements Comparator<AirTickets>
{
	int type;
	public CompareAirTicketByTime(int type)
	{
		this.type=type;
	}
	
	@Override
	public int compare(AirTickets lhs, AirTickets rhs) {	
		if(type==1)
			return lhs.getLeaveTime().compareTo(rhs.getLeaveTime());
		else
			return -lhs.getLeaveTime().compareTo(rhs.getLeaveTime());
	}
}

/**
 * 铁路票务按价格排序
 * @author Administrator zhou 
 *
 */
class CompareTrainTicketsByPrice implements Comparator<TrainTickets>
{
	int type;
	public CompareTrainTicketsByPrice(int type)
	{
		this.type=type;
	}
	
	@Override
	public int compare(TrainTickets lhs, TrainTickets rhs) {	
		if(type==1)
			return lhs.getBusiness()<rhs.getBusiness()?1:-1;
		else
			return lhs.getBusiness()>rhs.getBusiness()?1:-1;
	}		
}

/**
 * 铁路票务按时间排序
 * @author Administrator zhou 
 *
 */
class CompareTrainTicketByTime implements Comparator<TrainTickets>
{
	int type;
	public CompareTrainTicketByTime(int type)
	{
		this.type=type;
	}
	
	@Override
	public int compare(TrainTickets lhs, TrainTickets rhs) {	
		if(type==1)
			return lhs.getLeaveTime().compareTo(rhs.getLeaveTime());
		else
			return -lhs.getLeaveTime().compareTo(rhs.getLeaveTime());
	}
}
