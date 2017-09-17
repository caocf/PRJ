package com.sts.smartbus.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.alibaba.fastjson.JSON;
import com.sts.common.util.GlobalVar;
import com.sts.parking.action.PostDateFromSIWEI;
import com.sts.smartbus.model.BikeStation;
import com.sts.smartbus.model.BusArrive;
import com.sts.smartbus.model.BusLineForDetail;
import com.sts.smartbus.model.BusLineForQueryByName;
import com.sts.smartbus.model.BusStationForDetail;
import com.sts.smartbus.model.BusStationForQueryByName;
import com.sts.smartbus.model.RealInfo;
import com.sts.smartbus.model.RealStation;
import com.sts.smartbus.model.Station;

public class SmartBusService 
{
	PostDateFromSIWEI postDateFromSIWEI=new PostDateFromSIWEI();
	
	public BusLineForQueryByName RequestLineHelpWords(String lineName)
	{
		if(lineName==null || lineName.equals(""))
			return null;
		
		BusLineForQueryByName result=new BusLineForQueryByName();
		
//		String str="{\"FuzzyLineList\":[{\"DownEndStationId\":2147483647,\"DownEndStationName\":\"String content\",\"DownStartStationId\":2147483647,\"DownStartStationName\":\"String content\",\"Id\":2147483647,\"LineDownEndTime\":\"String content\",\"LineDownStartTime\":\"String content\",\"LineType\":\"String content\",\"LineUpEndTime\":\"String content\",\"LineUpStartTime\":\"String content\",\"LinedownIntervalTime\":2147483647,\"LinedownLength\":2147483647,\"LineupIntervalTime\":2147483647,\"LineupLength\":2147483647,\"Name\":\"String content\",\"Price\":1.26743233E+15,\"Remark\":\"String content\",\"ShortName\":\"String content\",\"TicketType\":2147483647,\"UpEndStationId\":2147483647,\"UpEndStationName\":\"String content\",\"UpStartStationId\":2147483647,\"UpStartStationName\":\"String content\",\"IcCard\":\"String content\",\"IsRing\":true,\"NormalPrice\":1.26743233E+15,\"SeasonPrice\":1.26743233E+15}],\"LineList\":[{\"DownEndStationId\":2147483647,\"DownEndStationName\":\"String content\",\"DownStartStationId\":2147483647,\"DownStartStationName\":\"String content\",\"Id\":2147483647,\"LineDownEndTime\":\"String content\",\"LineDownStartTime\":\"String content\",\"LineType\":\"String content\",\"LineUpEndTime\":\"String content\",\"LineUpStartTime\":\"String content\",\"LinedownIntervalTime\":2147483647,\"LinedownLength\":2147483647,\"LineupIntervalTime\":2147483647,\"LineupLength\":2147483647,\"Name\":\"String content\",\"Price\":1.26743233E+15,\"Remark\":\"String content\",\"ShortName\":\"String content\",\"TicketType\":2147483647,\"UpEndStationId\":2147483647,\"UpEndStationName\":\"String content\",\"UpStartStationId\":2147483647,\"UpStartStationName\":\"String content\",\"IcCard\":\"String content\",\"IsRing\":true,\"NormalPrice\":1.26743233E+15,\"SeasonPrice\":1.26743233E+15}]}";
		
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_LINE_BY_LINE_NAME_URL+"&name="+lineName);
		System.out.println(str);
		result=JSON.parseObject(str, BusLineForQueryByName.class);
		
		return result;
		
		
	}
	
	
//	public BusArrive parse()
//	{
//		String str=GetFile("F:\\4444.txt");		
//		System.out.println(str);
//		
//		str="{\"List\":[{\"Count\":2147483647,\"Stationid\":2147483647},{\"Count\":2147483647,\"Stationid\":2147483647}]}";
//		System.out.println(str);
//
//		return JSON.parseObject(str,BusArrive.class);
//	}
	
	
	
	public Station GetStationByID(int id,int orient)
	{
		Station result=new Station();
//		
//		result.setAddress("嘉兴市南湖区中山东路xxxx号");
//		result.setId(id);
//		result.setLantitude(30.123456);
//		result.setLongtitude(120.456789);
//		result.setName("交通银行站");
//		result.setOrient(orient);
//		
//		LineForStation lineForStation=new LineForStation();
//		lineForStation.setCrowed(3);
//		lineForStation.setDistance(1000);
//		lineForStation.setEnd("高照客运站");
//		lineForStation.setId(30002);
//		lineForStation.setName("k3");
//		lineForStation.setSpeed(1);
//		lineForStation.setStart("火车站");
//		
//		List<LineForStation> lineForStations= new ArrayList<LineForStation>();
//		lineForStations.add(lineForStation);
//		
//		lineForStation=new LineForStation();
//		lineForStation.setCrowed(2);
//		lineForStation.setDistance(100);
//		lineForStation.setEnd("客运站");
//		lineForStation.setId(560001);
//		lineForStation.setName("k1");
//		lineForStation.setSpeed(2);
//		lineForStation.setStart("客运站");
//		
//		lineForStations.add(lineForStation);
//		
//		result.setLineForStations(lineForStations);
		
		return result;
	}
	
	public RealStation GetRealStationInfo(int stationID,int lineID,int orient)
	{
		RealStation result=new RealStation();
		
		result.setLineName("k1");
		result.setOrient(orient);
		result.setStationName("火车站");
		
		List<RealInfo> infos=new ArrayList<RealInfo>();
		
		for(int i=0;i<6;i++)
		{
			RealInfo temp=new RealInfo();
			
			Random random=new Random();
			temp.setCrowed(random.nextInt(3)+1);
			temp.setDistance(random.nextInt(1000));
			temp.setInterStationNum(random.nextInt(6));
			temp.setNum(i+1);
			temp.setSpeed(random.nextInt(3)+1);
			
			infos.add(temp);
		}
		
		result.setRealInfos(infos);
		
		return result;
	}
	
	public List<BikeStation> QueryNearbyBikeStation(double lan,double lon,int radius,int count)
	{
		List<BikeStation> result;
		
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_NEARBY_BIKE_LOCATION_URL+"");
		
		result=JSON.parseArray(str, BikeStation.class);
		
		return result;
	}
	
	public List<BusStationForQueryByName> QueryNearbyBusStation(double lan,double lon,int radius)
	{
		List<BusStationForQueryByName> result;
		
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_NEARBY_BUS_LOCATION_URL+"");
		
		result=JSON.parseArray(str, BusStationForQueryByName.class);
		
		return result;
	}
	
	public BikeStation QueryBikeStationByID(int id)
	{
		BikeStation result;
		
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_BIKE_STATION_BY_ID_URL+id);
		
		result=JSON.parseObject(str, BikeStation.class);
		
		return result;
	}
	
	public BusArrive QueryBusArrive(int lineID,int bikeID,int direct,int count)
	{
		BusArrive result;
		
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_BUS_ARRIVE_URL);
		
		result=JSON.parseObject(str, BusArrive.class);
		
		return result;
	}
	
	public BusLineForDetail QueryBusLineByID(int id,int direct)
	{
		BusLineForDetail result;
		
//		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_LINE_ID+id+"/"+direct+"/?linedetails=true");
		
		String str=GetFile("line.txt");
		
		result=JSON.parseObject(str, BusLineForDetail.class);
		
		return result;
	}
	
	public List<BusStationForQueryByName> queryBusStationByName(String name)
	{
		if(name==null || name.equals(""))
			return null;
		
		List<BusStationForQueryByName> result;
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_STATION_NAME+"&name="+name);
		result=JSON.parseArray(str, BusStationForQueryByName.class);
		
		return result;
	}
	
	public BusStationForDetail QueryStationByID(int id,boolean arrive,boolean detail,boolean line)
	{
		BusStationForDetail result;
		
		String str=postDateFromSIWEI.GetData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_STATION_ID+id+"/?station="+detail+"&linelist="+line+"&busarrive="+arrive);
		result=JSON.parseObject(str, BusStationForDetail.class);
		
		return result;
	}
	
	public String GetOriginData(String url)
	{
		return postDateFromSIWEI.GetDataFromThreeNet(url);
	}
	
	
	public String GetFile(String path)
	{
		 StringBuffer   sbFile;   
//		 path="E:\\android\\JiaXingSmartTraffic\\SmartTrafficServer\\WebRoot\\xml\\"+path;
		 try
		 {
	         FileReader   in   =   new   FileReader(path);   
	         char[]   buffer   =   new   char[4096];   
	         int   len;   
	         sbFile   =   new   StringBuffer();   
	         while   (   (len   =   in.read(buffer))   !=   -1)   {   
	             String   s   =   new   String(buffer,   0,   len);   
	             sbFile.append(s);   
	         }
	         return   sbFile.toString();   
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		 
         return "";
	}
}
