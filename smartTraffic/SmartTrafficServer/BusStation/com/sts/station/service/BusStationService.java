package com.sts.station.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sts.station.model.Agent;
import com.sts.station.model.BusCode;
import com.sts.station.model.BusOrder;
import com.sts.station.model.BusSchedule;
import com.sts.station.model.BusStation;
import com.sts.station.model.BusStationIntroduce;
import com.sts.station.model.BusStationRealInfo;

public class BusStationService
{
	public List<BusStation> GetBusStationPositionInfo()
	{
		return new ArrayList<BusStation>();
	}
	
	public List<BusSchedule> GetBusTickets(String startCity,String endCity,Date date)
	{
		List<BusSchedule> result=new ArrayList<BusSchedule>();
		
		for(int i=0;i<10;i++)
		{
			BusSchedule temp=new BusSchedule();
			
			temp.setCarNumber("c"+i);
			temp.setCarKind(1);
			temp.setCompany("comany"+i);
			temp.setCostTime("14:40");
			temp.setEndStation(endCity);
			temp.setStartStation(startCity);
			temp.setLeaveTime("14:20:20");
			temp.setLeaveTicketNumber(i);
			temp.setLength(100);
			temp.setPrice(20);
			temp.setReachTime("16:20:20");
			
			result.add(temp);
		}
		return result;
	}
	
	public List<BusSchedule> GetBusTickets(String stationName)
	{
		List<BusSchedule> result=new ArrayList<BusSchedule>();
		
		for(int i=0;i<10;i++)
		{
			BusSchedule temp=new BusSchedule();
			
			temp.setCarNumber("c"+i);
			temp.setCarKind(1);
			temp.setCompany("comany"+i);
			temp.setCostTime("14:40");
			temp.setEndStation("上海");
			temp.setStartStation("杭州");
			temp.setLeaveTime("14:20:20");
			temp.setLeaveTicketNumber(i);
			temp.setLength(100);
			temp.setPrice(20);
			temp.setReachTime("16:20:20");
			
			result.add(temp);
		}
		return result;
	}
	
	public List<BusStationRealInfo> SearchBusCodesByStation(String stationName)
	{
		List<BusStationRealInfo> result=new ArrayList<BusStationRealInfo>();
		
		for(int i=0;i<10;i++)
		{
			BusStationRealInfo temp=new BusStationRealInfo();
			
			temp.setCarKind(1);
			temp.setCarNumber("1001");
			temp.setCompany("嘉兴长途客运公司");
			temp.setEndStation("杭州");
			temp.setId(i);
			temp.setPlanEndTime(new Date());
			temp.setPlanStartTime(new Date());
			temp.setRealEndTime(new Date());
			temp.setRealStartTime(new Date());
			temp.setStartStation(stationName);
			temp.setStatus(1);
			
			result.add(temp);
		}
		
		return result;
		
	}
	
	public BusCode SearchCodesByBusCode(String busCode)
	{
		BusCode busCodes = new BusCode();
		
		busCodes.setBusNo(busCode);
		busCodes.setCompany("company");
		busCodes.setStartCity("上海");
		busCodes.setEndCity("嘉兴");
		busCodes.setEndStation("上海虹桥汽车西站");
		busCodes.setStartStation("嘉兴客运中心站");
		
		List<BusOrder> busOrders=new ArrayList<BusOrder>();
		for(int i=0;i<10;i++)
		{
			BusOrder temp=new BusOrder();

			temp.setOrder(i);
			temp.setPlanEndTime(new Date());
			temp.setPlanStartTime(new Date());
			temp.setRealEndTime(new Date());
			temp.setRealStartTime(new Date());
			temp.setStatus(i>5?2:1);
			busOrders.add(temp);
		}
		busCodes.setOrders(busOrders);
		
		return busCodes;
	}
	
	public List<BusStationIntroduce> GetBusStaionByBusNo(String no)
	{
		List<BusStationIntroduce> result=new ArrayList<BusStationIntroduce>();
		
		BusStationIntroduce temp=new BusStationIntroduce();
		temp.setStationName("上海浦东机场");
		temp.setUrl("http://baike.baidu.com/view/176714.htm");
		result.add(temp);
		
		BusStationIntroduce temp1=new BusStationIntroduce();
		temp1.setStationName("北京首都机场");
		temp1.setUrl("http://baike.baidu.com/view/376985.htm");
		result.add(temp1);
		
		BusStationIntroduce temp2=new BusStationIntroduce();
		temp2.setStationName("上海虹桥机场");
		temp2.setUrl("http://baike.baidu.com/view/956435.htm");
		result.add(temp2);
		
		
		return result;
	}
	
	public String GetBusStationDetailUrl(String name)
	{
		String[] tempStrings=new String[]{"http://baike.baidu.com/view/956435.htm","http://baike.baidu.com/view/376985.htm"};
		return tempStrings[new Random().nextInt(2)];
	}
	
	public List<Agent> GetAgentList(int page,int num)
	{
		List<Agent> result=new ArrayList<Agent>();
		for(int i=0;i<(num==0?10:num);i++)
		{
			Agent temp=new Agent();
			temp.setId(i);
			temp.setAgentAddress("嘉兴市南湖区xxx街道");
			temp.setAgentIntroduce("只销售十日内车票");
			temp.setAgentName("xxx代销点");
			temp.setAgentPhone("1234567890");
			
			result.add(temp);
			
		}
		
		return result;
	}
}
