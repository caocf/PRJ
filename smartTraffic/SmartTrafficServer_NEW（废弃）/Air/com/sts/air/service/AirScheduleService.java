package com.sts.air.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.ksoap2.serialization.SoapObject;

import com.sts.air.dao.AirDao;
import com.sts.air.model.AirCity;
import com.sts.air.model.AirNo;
import com.sts.air.model.AirNoReal;
import com.sts.air.model.AirRealTimeSchedule;
import com.sts.air.model.AirSchedule;
import com.sts.air.model.AirStation;
import com.sts.util.DoString;

public class AirScheduleService 
{
	AirDao airDao;
	
	public AirDao getAirDao() {
		return airDao;
	}

	public void setAirDao(AirDao airDao) {
		this.airDao = airDao;
	}

	public List<AirSchedule> parse(SoapObject object,Date strDate)
	{
		SoapObject parse=object;
		SoapObject data;
		List<AirSchedule> result=new ArrayList<AirSchedule>();
		AirSchedule temp;
		
		if(strDate==null)
			strDate=new Date();
		
		parse=(SoapObject)parse.getProperty(1);
		parse=(SoapObject)parse.getProperty(0);
			
		for(int i=0;i<parse.getPropertyCount() && parse.getPropertyCount()>1;i++)
		{
			data=(SoapObject)parse.getProperty(i);
					
			temp=new AirSchedule();
			
			temp.setCompany(data.getProperty(0).toString());
			temp.setAirNumber(data.getProperty(1).toString());
			temp.setStartCity(data.getProperty(2).toString());
			temp.setEndCity(data.getProperty(3).toString());
			temp.setLeaveTime(DoString.ParseDateFromHour(data.getProperty(4).toString(),1));
			
			temp.getLeaveTime().setYear(strDate.getYear());
			temp.getLeaveTime().setMonth(strDate.getMonth());
			temp.getLeaveTime().setDate(strDate.getDate());
			
			temp.setReachTime(DoString.ParseDateFromHour(data.getProperty(5).toString(),1));
			
			temp.getReachTime().setYear(strDate.getYear());
			temp.getReachTime().setMonth(strDate.getMonth());
			temp.getReachTime().setDate(strDate.getDate());
			
			temp.setAirKind(data.getProperty(6).toString());
			
			temp.setTime(DoString.DateCompare(temp.getLeaveTime(), temp.getReachTime()));
			if(temp.getTime()<0)
			{
				temp.setTime(temp.getTime()+24*60);
			}
				
			result.add(temp);
		}

		return result;
	}
	
	public List<AirSchedule> filterByNum(List<AirSchedule> data,int num,int page)
	{
		List<AirSchedule> result=new ArrayList<AirSchedule>();
		
		if((page-1)*num < data.size())
		{
			for(int i=(page-1)*num;i<page*num;i++)
			{
				if(i < data.size())
				{
					result.add(data.get(i));
				}
			}
		}
		
		return result;
	}
	
	public List<AirCity> GetCityList(SoapObject object)
	{
		SoapObject parse=object;
		SoapObject data;
		List<AirCity> result=new ArrayList<AirCity>();
		AirCity temp;
		
		parse=(SoapObject)parse.getProperty(1);
		parse=(SoapObject)parse.getProperty(0);
			
		for(int i=0;i<parse.getPropertyCount() && parse.getPropertyCount()>1;i++)
		{
			data=(SoapObject)parse.getProperty(i);
					
			temp=new AirCity();
			
			temp.setEnglishName(data.getProperty(0).toString());
			temp.setName(data.getProperty(1).toString());
			temp.setAbbreviation(data.getProperty(2).toString());
				
			result.add(temp);
		}

		return result;
	}
	
	public void saveCity(AirCity data)
	{
		this.airDao.saveCity(data);
	}
	
	public List<AirCity> findAll()
	{
		return this.airDao.findAllCity();
	}
	
	public List<AirCity> findCityByName(String name)
	{
		return this.airDao.findCityByName(name);
	}
	
	
	public List<AirRealTimeSchedule> GetRealTimeAir(String airportName,int status)
	{		
		return simulateGetRealTimeAir(airportName, status,20);
	}
	
	public List<AirRealTimeSchedule> simulateGetRealTimeAir(String airportName,int status,int num)
	{
		List<AirRealTimeSchedule> result= new ArrayList<AirRealTimeSchedule>();
		
		String[] airport=new String[]{"上海虹桥","北京国际","成都双流","广州白云","杭州萧山"};
		String[] company=new String[]{"南方航空","东方航空","深圳航空","吉祥航空"};
		
		
		for(int i=0;i<num;i++)
		{
			AirRealTimeSchedule temp=new AirRealTimeSchedule();
			if(status==0)
			{
				temp.setStartCity(airport[new Random().nextInt(airport.length)]);
				temp.setEndCity(airport[new Random().nextInt(airport.length)]);
			}
			else if(status==1)
			{
				temp.setEndCity(airportName);
				temp.setStartCity(airport[new Random().nextInt(airport.length)]);
			}
			else if(status==2) 
			{
				temp.setStartCity(airportName);
				temp.setEndCity(airport[new Random().nextInt(airport.length)]);
			}
			
			temp.setCompany(company[new Random().nextInt(company.length)]);
			temp.setAirNumber(String.valueOf(new Random().nextInt(2000)));
			temp.setStatus(new Random().nextInt(4));
			
			temp.setLeaveTime(new Date());
			temp.setReachTime(new Date());
			temp.setRealLeaveTime(new Date());
			temp.setRealReachTime(new Date());
						
			result.add(temp);
		}
		
		return result;
	}
	
	public AirNo GetAirByAirNo(String no)
	{
		AirNo airNo=new AirNo();
		
		airNo.setAirNo(no);
		airNo.setCompany("东方航空");
		airNo.setCostTime("1:12");
		airNo.setCusLeftNum(7);
		airNo.setCusPrice(600);
		airNo.setEcoLeftNum(3);
		airNo.setEcoPrice(300);
		airNo.setEndAirport("上海浦东");
		airNo.setEndCity("上海");
		airNo.setEndTime("16:40");
		airNo.setFirLeftNum(30);
		airNo.setFirPrice(900.0);
		airNo.setStartAirport("北京国际");
		airNo.setStartCity("北京");
		airNo.setStartTime("14:30");
		
		return airNo;
	}
	
	
	public AirNoReal GetAirRealByAirNo(String no)
	{
		AirNoReal airNo=new AirNoReal();
		
		airNo.setAirNo(no);
		airNo.setCompany("东方航空");
		airNo.setEndAirport("上海浦东");
		airNo.setEndCity("上海");
		airNo.setStartAirport("北京国际");
		airNo.setStartCity("北京");
		airNo.setAirKind("A302");
		airNo.setStartPlanTime("16:00");
		airNo.setStartRealTime("16:10");
		airNo.setEndPlanTime("18:00");
		airNo.setEndRealTime("18:30");
		
		return airNo;
	}
	
	public List<AirStation> GetAiStaionByAirNo(String no)
	{
		List<AirStation> result=new ArrayList<AirStation>();
		
		AirStation temp=new AirStation();
		temp.setStationName("上海浦东机场");
		temp.setUrl("http://baike.baidu.com/view/176714.htm");
		result.add(temp);
		
		AirStation temp1=new AirStation();
		temp1.setStationName("北京首都机场");
		temp1.setUrl("http://baike.baidu.com/view/376985.htm");
		result.add(temp1);
		
		AirStation temp2=new AirStation();
		temp2.setStationName("上海虹桥机场");
		temp2.setUrl("http://baike.baidu.com/view/956435.htm");
		result.add(temp2);
		
		
		return result;
	}
	
	public String GetAirStationDetailUrl(String name)
	{
		String[] tempStrings=new String[]{"http://baike.baidu.com/view/956435.htm","http://baike.baidu.com/view/376985.htm"};
		return tempStrings[new Random().nextInt(2)];
	}
}
