package com.sts.train.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.ksoap2.serialization.SoapObject;

import com.sts.air.model.AirStation;
import com.sts.train.model.TrainSchedule;
import com.sts.train.model.TrainStation;
import com.sts.train.model.TrainTickets;

public class TrainScheduleService 
{
	
	public static final String[] TRAIN_KIND={"G","D","T","K"};
	public static final int defaultIndex=4;
	public static final int kind_size=5;
	/**
	 * 按火车类别筛选
	 * @param data 所有数据
	 * @param type 类型
	 * @return
	 */
	public static List<TrainSchedule> filterByType(List<TrainSchedule> data,int type)
	{
		List<TrainSchedule> result=new ArrayList<TrainSchedule>();
		
		boolean[] GDTKP=filterType(type,kind_size);
		String num;
		int numIndex;
		
		for(int i=0;i<data.size();i++)
		{
			num=data.get(i).getTrainNumber();
			num=spliteStringFirst(num);
			
			numIndex=typeMatch(num, TRAIN_KIND, defaultIndex);
			
			if(GDTKP[numIndex])
				result.add(data.get(i));			
		}
			
		return result;
	}
	public static int typeMatch(String str,String[] strs,int defaultIndex)
	{
		for(int i=0;i<strs.length;i++)
		{
			if(str.equals(strs[i]))
				return i;
		}
		
		return defaultIndex;
	}
	public static boolean[] filterType(int type,int size)
	{
		boolean[] GDTKP=new boolean[size];
		
		for(int i=0;i<size;i++)
		{
			if(type%10==1)
			{
				GDTKP[size-1-i]=true;
			}
			else 
			{
				GDTKP[size-1-i]=false;
			}
			
			type/=10;
		}
		
		return GDTKP;
	}
	
	public static final int[] TRAIN_TIME={6,12,14,18};
	public static final int time_kind=4;
	public static List<TrainSchedule> fileterByTime(List<TrainSchedule> data,int timeType,int which)
	{
		List<TrainSchedule> result=new ArrayList<TrainSchedule>();
		
		boolean[] selected=filterType(timeType, time_kind);
				
		for(int i=0;i<data.size();i++)
		{
			int id;
			if(which==1)
			{
				id=GetTimeZone(data.get(i).getLeaveTime());
			}
			else
			{
				id=GetTimeZone(data.get(i).getReachTime());
			}
			
			if(selected[id])
				result.add(data.get(i));
		}
		
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public static int GetTimeZone(Date date)
	{
		for(int i=0;i<TRAIN_TIME.length-1;i++)
		{
			if(isBetween(TRAIN_TIME[i], TRAIN_TIME[i+1], date.getHours()))
			{
				return i;
			}
		}
		return TRAIN_TIME.length-1;
	}
	public static boolean isBetween(int low,int high,int target)
	{
		if( target<high &&target>=low)
			return true;
		return false;
	}
	
	public static List<TrainSchedule> orderByTime(List<TrainSchedule> data,boolean des)
	{
		return data;
	}
	
	public static List<TrainSchedule> fileterByCostTime(List<TrainSchedule> data,boolean des)
	{
		return data;
	}
	
	public static List<TrainSchedule> filterByNum(List<TrainSchedule> data,int page,int num)
	{
		List<TrainSchedule> result=new ArrayList<TrainSchedule>();
		
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
	
	public static List<TrainSchedule> parse(SoapObject object)
	{	
		List<TrainSchedule> result=new ArrayList<TrainSchedule>();
		
		SoapObject parse=(SoapObject)object;
		SoapObject data;

		TrainSchedule temp;
		
		parse=(SoapObject)parse.getProperty(1);
		parse=(SoapObject)parse.getProperty(0);
			
		for(int i=0;i<parse.getPropertyCount() && parse.getPropertyCount()>1;i++)
		{
			data=(SoapObject)parse.getProperty(i);
					
			temp=new TrainSchedule();
			
			String trainNoString=splitSpecial(data.getProperty(0).toString(),'\\');
			
			temp.setTrainNumber(trainNoString);
			temp.setStartCity(data.getProperty(3).toString());
			temp.setEndCity(data.getProperty(5).toString());
			temp.setLeaveTime(DateFromString(data.getProperty(4).toString()));
			temp.setReachTime(DateFromString(data.getProperty(6).toString()));
			temp.setLength(Double.valueOf(data.getProperty(7).toString()));
//			temp.setCostTime(DateFromString(data.getProperty(8).toString(),"hh:mm"));
			
			temp.setCostTime(data.getProperty(8).toString());
			
			result.add(temp);
		}

		return result;
	}
	
	public static String spliteStringFirst(String str)
	{
		return str.substring(0, 1);
	}
	
	public static String splitSpecial(String data,char c)
	{
		char[] cData=data.toCharArray();
		for(int i=0;i<cData.length;i++)
		{
			if(cData[i]==c)
				return data.substring(0, i);
		}
		
		return data;
	}
	
	public static Date DateFromString(String dateString)
	{
		return DateFromString(dateString,"hh:mm:ss");	
		
	}
	
	public static Date DateFromString(String dateString,String format)
	{
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		try 
		{
			Date result  = dateFormat.parse(dateString);
			return result;
		} catch (ParseException e) 
		{
			return null;
		}
	}
	
	public TrainTickets getTicketsByTrainNoAndName(String trainNo,String start,String end)
	{
		TrainTickets result=new TrainTickets();
		result.setTrainNo(trainNo);
		result.setStartCity(start);
		result.setEndCity(end);
		
		result.setBusLeftNum(4);
		result.setBusPrice(100);
		result.setCostTime("1:00");
		result.setEndTime("16:00");
		result.setFirLeftNum(15);
		result.setFirPrice(300);
		result.setFirstCity("上海");
		result.setLastCity("北京");
		result.setSecLeftNum(40);
		result.setSecPrice(700);
		result.setStartTime("15:00");
		
		return result;
		
	}
	
	public List<TrainStation> GetTrainStaionByTrainNo(String no)
	{
		List<TrainStation> result=new ArrayList<TrainStation>();
		
		TrainStation temp=new TrainStation();
		temp.setStationName("嘉兴南站");
		temp.setUrl("http://baike.baidu.com/view/2769118.htm");
		result.add(temp);
		
		TrainStation temp1=new TrainStation();
		temp1.setStationName("上海虹桥站");
		temp1.setUrl("http://baike.baidu.com/view/1413614.htm");
		result.add(temp1);
		
		TrainStation temp2=new TrainStation();
		temp2.setStationName("杭州东站");
		temp2.setUrl("http://baike.baidu.com/view/2311178.htm");
		result.add(temp2);
		
		
		return result;
	}
	
	public String GetTrainStationDetailUrl(String name)
	{
		String[] tempStrings=new String[]{"http://baike.baidu.com/view/956435.htm","http://baike.baidu.com/view/376985.htm"};
		return tempStrings[new Random().nextInt(2)];
	}
	
}
