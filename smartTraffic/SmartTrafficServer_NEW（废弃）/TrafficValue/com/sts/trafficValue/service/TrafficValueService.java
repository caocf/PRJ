package com.sts.trafficValue.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sts.parking.action.PostDateFromSIWEI;
import com.sts.trafficValue.model.RealRoadInfo;
import com.sts.trafficValue.model.TrafficValue;

public class TrafficValueService 
{
//	public static final String TRAFFIC_BASE_URL="http://61.153.49.246:8090/jxtpi/cservice";
	
//	public static final String TRAFFIC_BASE_URL="http://115.159.64.244:8080/jxtpi/cservice";
	
	public static final String TRAFFIC_BASE_URL="http://192.168.170.3:8090/jxtpi/cservice";
	public static final String TRAFFIC_URL=TRAFFIC_BASE_URL+"/queryAreaTPIByAreaName?plan=3&areaName=";
	public static final String TRAFFIC_ADDRESS_NAME="全路网";
	
	public static final String TRAFFIC_ROAD=TRAFFIC_BASE_URL+"/queryRoadTPIByTPIDescPager?plan=3";
	public static final String TRAFFIC_CHANNEL=TRAFFIC_BASE_URL+"/queryPassTPIByTPIDescPager?plan=3";
	public static final String TRAFFIC_HOT=TRAFFIC_BASE_URL+"/queryCBDTPIByTPIDescPager?plan=3";
	public static final String TRAFFIC_REGION=TRAFFIC_BASE_URL+"/queryAdminTPIByTPIDescPager?plan=3";
	public static final String TRAFFIC_AREA=TRAFFIC_BASE_URL+"/queryAreaTPIByTPIDescPager?plan=3";
	
	//道路
	public String GetRoadTrafficValue(int direct,int page,int num)
	{
		String url=TRAFFIC_ROAD+"&direction="+direct+"&pageNum="+page+"&pageSize="+num;
		
		String data=new PostDateFromSIWEI().GetData(url);
		
		return data;
	}
	//通道
	public String GetChannelTrafficValue(int direct,int page,int num)
	{
		String url=TRAFFIC_CHANNEL+"&direction="+direct+"&pageNum="+page+"&pageSize="+num;
		
		String data=new PostDateFromSIWEI().GetData(url);
		
		return data;
	}
	//热点
	public String GetHotTrafficValue(int page,int num)
	{
		String url=TRAFFIC_HOT+"&pageNum="+page+"&pageSize="+num;
		
		String data=new PostDateFromSIWEI().GetData(url);
		
		return data;
	}
	//区域
	public String GetRegionTrafficValue(int page,int num)
	{
		String url=TRAFFIC_REGION+"&pageNum="+page+"&pageSize="+num;
		
		String data=new PostDateFromSIWEI().GetData(url);
		
		return data;
	}
	
	//区域 二环
	public String GetAreaTrafficValue(int page,int num)
	{
		String url=TRAFFIC_AREA+"&pageNum="+page+"&pageSize="+num;
		
		String data=new PostDateFromSIWEI().GetData(url);
		
		return data;
	}
	
	public TrafficValue GetTrafficValue()
	{
		Map<String, TrafficValue> areaTPI=null;
		String url=TRAFFIC_URL;
		
		try 
		{
			url+=URLEncoder.encode(TRAFFIC_ADDRESS_NAME,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String data=new PostDateFromSIWEI().GetData(url);
		
		areaTPI=JSON.parseObject(data,new TypeReference<Map<String, TrafficValue>>(){});
		
		return areaTPI.get("areaTpi");
	}
	
	public List<RealRoadInfo> GetRealRoadInfo(int type)
	{
		List<RealRoadInfo> result=new ArrayList<RealRoadInfo>();
		
		for(int i=0;i<10;i++)
		{
			RealRoadInfo temp=new RealRoadInfo();
			
			temp.setInfoContent("某某路段车流量情况流量");
			
			temp.setInfoType(type);
			temp.setInfoDate(new Date());
			temp.setStatus(new Random().nextInt(3));
			
			switch (temp.getStatus()) {
				case 0:
					temp.setInfoContent(temp.getInfoContent()+"正常,请驾驶员放心行驶");
					break;
					
				case 1:
					temp.setInfoContent(temp.getInfoContent()+"较大,请驾驶员小心行驶");
					break;
					
				case 2:
					temp.setInfoContent(temp.getInfoContent()+"较小,请驾驶员放心行驶");
					break;

			}
			
			result.add(temp);
		}
		
		return result;
	}
}
