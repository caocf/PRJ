package com.sts.smartbus.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.sts.common.util.GlobalVar;
import com.sts.smartbus.model.BikeStation;
import com.sts.smartbus.model.BikeStationTemp;
import com.sts.smartbus.model.BusArrive;
import com.sts.smartbus.model.BusLine;
import com.sts.smartbus.model.BusLineDetail;
import com.sts.smartbus.model.BusLineForDetail;
import com.sts.smartbus.model.BusStationDetail;
import com.sts.smartbus.model.BusStationForDetail;
import com.sts.smartbus.model.BusStationForQueryByName;
import com.sts.smartbus.model.BusLineForQueryByName;
import com.sts.smartbus.model.RealStation;
import com.sts.smartbus.model.Station;
import com.sts.smartbus.service.SmartBusService;
import com.sts.smartbus.utl.Average;

public class SmartBusAction 
{
	private SmartBusService smartBusService;
	private BusLineForQueryByName lines;
	private String lineName;
	
	public void setSmartBusService(SmartBusService smartBusService) {
		this.smartBusService = smartBusService;
	}
	public BusLineForQueryByName getLines() {
		return lines;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	private String resultString;
	public String getResultString() {
		return resultString;
	}
	
	
	/**
	 * 通过线路名称获取线路信息
	 * @return
	 */
	public String QueryLineByLineName()
	{
		lines=this.smartBusService.RequestLineHelpWords(lineName);
		
		return "success";
	}
	
	
	/**
	 * 通过站点id和方向获取站点信息
	 */
	Station station;
	int stationID;
	int orient;
	
	public Station getStation() {
		return station;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	
	public void setOrient(int orient) {
		this.orient = orient;
	}
	public String GetStationInfoByID()
	{
		station=this.smartBusService.GetStationByID(stationID,orient);
		return "success";
	}
	
	int lineID;
	RealStation realStation;
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}
	public RealStation getRealStation() {
		return realStation;
	}
	
	public String GetRealStationInfo()
	{
		realStation=this.smartBusService.GetRealStationInfo(stationID, lineID, orient);
		return "success";
	}
	
	
	private double lantitude;
	private double longtitude;
	private int distance;
	private int count;

	public void setLantitude(double lantitude) {
		this.lantitude = lantitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * 获取周边自行车站点
	 */
	private List<BikeStation> bikeStationLists;
	
	
	public List<BikeStation> getBikeStationLists() {
		return bikeStationLists;
	}
	public String QueryNearbyBikeStation()
	{
		bikeStationLists=this.smartBusService.QueryNearbyBikeStation(lantitude, longtitude, distance, count);
		return "success";
	}
	
	/**
	 * 周边公交站点
	 */
	private List<BusStationForQueryByName> busStationLists;

	public List<BusStationForQueryByName> getBusStationLists() {
		return busStationLists;
	}
	
	public String QueryNearbyBusStation()
	{
		busStationLists=this.smartBusService.QueryNearbyBusStation(lantitude, longtitude, distance);
		return "success";
	}
	
	/**
	 *通过id查询自行车站点
	 */
	private int bikeID;

	public void setBikeID(int bikeID) {
		this.bikeID = bikeID;
	}
	private BikeStation bikeStation;
	
	public BikeStation getBikeStation() {
		return bikeStation;
	}
	public String QueryBikeStationByID()
	{
		bikeStation=this.smartBusService.QueryBikeStationByID(bikeID);
		
		return "success";
	}
	
	/**
	 * 查询公交线路实时信息
	 */
	private int direct;

	public void setDirect(int direct) {
		this.direct = direct;
	}
	private BusArrive busArrive;
	public BusArrive getBusArrive() {
		return busArrive;
	}
	
	public String QueryBusArrive()
	{
		busArrive=this.smartBusService.QueryBusArrive(lineID, bikeID, direct, count);
		return "success";
	}
	
	/**
	 * 查询线路详情通过id
	 */
	private BusLineForDetail busLineForDetail;
	public BusLineForDetail getBusLineForDetail() {
		return busLineForDetail;
	}
	public String QueryBusLineByID()
	{
		busLineForDetail=this.smartBusService.QueryBusLineByID(lineID, direct);
		return "success";
	}
	
	/**
	 * 通过站点名称获取站点列表
	 */
	private List<BusStationForQueryByName> busStations;
	private String stationName;
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public List<BusStationForQueryByName> getBusStations() {
		return busStations;
	}
	public String queryBusStationByName()
	{
		busStations=this.smartBusService.queryBusStationByName(stationName);
		return "success";
	}
	
	/**
	 * 通过ID查询站点详情
	 */
	private boolean staionDetailShow;
	private boolean stationCrossLineShow;
	private boolean stationArriveShow;
	private BusStationForDetail busStationForDetail;
	public void setStaionDetailShow(boolean staionDetailShow) {
		this.staionDetailShow = staionDetailShow;
	}
	public void setStationArriveShow(boolean stationArriveShow) {
		this.stationArriveShow = stationArriveShow;
	}
	public void setStationCrossLineShow(boolean stationCrossLineShow) {
		this.stationCrossLineShow = stationCrossLineShow;
	}
	public BusStationForDetail getBusStationForDetail() {
		return busStationForDetail;
	}
	
	public String QueryStationByID()
	{
		busStationForDetail=this.smartBusService.QueryStationByID(stationID, stationArriveShow, staionDetailShow, stationCrossLineShow);
		return "success";
	}
	
	
	String url;
	String orginData;
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrginData() {
		return orginData;
	}
	public String GetOriginData()
	{
		orginData=this.smartBusService.GetOriginData(url);
		return "success";
	}
	
	

	
	
	/**
	 * 平均分布自行车点位
	 */
	List<BikeStation> bikeStations;
	
	public List<BikeStation> getBikeStations() {
		return bikeStations;
	}
	
	//http://115.231.73.254/SmartTraffic/QueryBikeAverage?startLantitude=30.999&startLontitude=120.00&endLantitude=30.00&endLontitude=120.990
	public String QueryBikeAverage()
	{
		//获取全部点位信息
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_NEARBY_BIKE_LOCATION_URL+"&longitude=120.756&latitude=30.764&distance=999999999&count=10000");
		BikeStationTemp result=JSON.parseObject(orginData,BikeStationTemp.class);
		
		List<BikeStation> b=result.getStationList();
		
		System.out.println("数目(前)："+b.size());
		
		if(count==0)
			count=100;
		
		System.out.println(startLantitude+"-"+startLontitude+"-"+endLantitude+"-"+endLontitude);
		
		if(startLantitude>1 && startLontitude>1 && endLantitude>1 && endLontitude>1)
			bikeStations=new Average().average(new BikeStation(startLantitude, startLontitude), new BikeStation(endLantitude, endLontitude), b, count);
		
		System.out.println("数目（后）"+bikeStations.size());
		
		return "success";
	}

	/**
	 * 以下是原始数据
	 * 
	 */
	
	
	/**
	 * 附近自行车站点
	 * http://112.11.131.238/SmartTraffic/QueryOriginNearByBikeStation?longtitude=&lantitude=&distance=&count=
	 */
	public String QueryOriginNearByBikeStation()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_NEARBY_BIKE_LOCATION_URL+"&longitude="+longtitude+"&latitude="+lantitude+"&distance="+distance+"&count="+count);
		return "success";
	}
	/**
	 * 附近公交站点
	 * http://112.11.131.238/SmartTraffic/QueryOriginNearByBusStation?longtitude=&lantitude=&distance=
	 * @return
	 */
	public String QueryOriginNearByBusStation()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_NEARBY_BUS_LOCATION_URL+"&longitude="+longtitude+"&latitude="+lantitude+"&distance="+distance);
		return "success";
	}
	/**
	 * 通过id查询自行车站点
	 *  http://112.11.131.238/SmartTraffic/QueryOriginBikeStationByID?stationID=1002;
	 * @return
	 */
	public String QueryOriginBikeStationByID()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BIKE_STATION_BY_ID_URL+stationID+"/?application=jiaotongju");
		return "success";
	}
	
	/**
	 * 通过线路id站点id查找实时信息
	 * http://112.11.131.238/SmartTraffic/QueryOriginBusArrive?lineID=&stationID=&count=&direct=
	 * @return
	 */
	public String QueryOriginBusArrive()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_ARRIVE_URL+lineID+"/"+direct+"/"+stationID+"/?count="+count+"&application=jiaotongju");
		return "success";
	}
	
	/**
	 * 通过id查询公交线路
	 * http://112.11.131.238/SmartTraffic/QueryOriginBusLineByID?lineID=22&direct=0
	 * @return
	 */
	public String QueryOriginBusLineByID()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_LINE_ID+lineID+"/"+direct+"/?linedetails=true&application=jiaotongju");
		return "success";
	}
	/**
	 * 通过id查询公交站点
	 * http://112.11.131.238/SmartTraffic/QueryOriginBusStationByID?stationID=;
	 * @return
	 */
	public String QueryOriginBusStationByID()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_STATION_NAME+stationID+"/?station=true&linelist=true&busarrive=true&application=jiaotongju");
		return "success";
	}

	public String QueryOriginLinePassStation()
	{
//		orginData=this.smartBusService.GetOriginData(GlobalVar)
		return "success";
	}
	
	private double startLantitude;
	private double startLontitude;
	private double endLantitude;
	private double endLontitude;
	private boolean isBike;
	private int order;
	
	public void setStartLantitude(double startLantitude) {
		this.startLantitude = startLantitude;
	}
	public void setStartLontitude(double startLontitude) {
		this.startLontitude = startLontitude;
	}
	public void setEndLantitude(double endLantitude) {
		this.endLantitude = endLantitude;
	}
	public void setEndLontitude(double endLontitude) {
		this.endLontitude = endLontitude;
	}
	public void setBike(boolean isBike) {
		this.isBike = isBike;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	public String QueryOriginTrafficTrasferOfBike()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_TRANSFER+"&startlongitude="+startLontitude+"&startlatitude="+startLantitude+"&endlongitude="+endLontitude+"&endlatitude="+endLantitude
				+"&order="+order+"&count="+count+"&trails=true&bike=2");
		return "success";
	}
	
	/**
	 * 交通换乘
	 * http://112.11.131.238/SmartTraffic/QueryOriginTrafficTrasfer?startlongitude=&startLantitude=&endLontitude=&endLantitude=&order=&count=&bike=
	 * @return
	 */
	public String QueryOriginTrafficTrasfer()
	{
		String bikeString=isBike?"bike=2":"";
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_TRANSFER+"&startlongitude="+startLontitude+"&startlatitude="+startLantitude+"&endlongitude="+endLontitude+"&endlatitude="+endLantitude
				+"&order="+order+"&count="+count+"&bus=1&trails=true&"+bikeString);
		return "success";
	}
	
	String bikeList;
	public void setBikeList(String bikeList) {
		this.bikeList = bikeList;
	}
	
	/**
	 * 查询实时停车数
	 * http://112.11.131.238/SmartTraffic/QueryOriginBikeParkingCount？bikeList=;
	 * @return
	 */
	public String QueryOriginBikeParkingCount()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BIKE_INFO+"?application=jiaotongju&idlist="+bikeList);
		return "success";
	}
	
	/**
	 * 通过名称获取自行车站点
	 * http://112.11.131.238/SmartTraffic/QueryOriginBikeStation?stationName=银行
	 * @return
	 */	
	public String QueryOriginBikeStation()
	{
		try 
		{
			stationName=URLEncoder.encode(stationName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BIKE_STATION_BY_ID_URL+"?application=jiaotongju&name="+stationName);
		return "success";
	}
	/**
	 * 通过名称获取线路
	 * http://112.11.131.238/SmartTraffic/QueryOriginBusLine?lineName=1路
	 * @return
	 */
	public String QueryOriginBusLine()
	{
		try 
		{
			lineName=URLEncoder.encode(lineName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_LINE+"?application=jiaotongju&name="+lineName);
		return "success";
	}
	/**
	 * 获取公交站点
	 * http://112.11.131.238/SmartTraffic/QueryOriginBusStation?stationName=银行
	 * @return
	 */
	public String QueryOriginBusStation()
	{
		try 
		{
			stationName=URLEncoder.encode(stationName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_STATION+"?application=jiaotongju&name="+stationName);
		return "success";
	}

	/**
	 * 获取线路轨迹
	 * @return
	 */
	//http://115.231.73.253:8091/zhjtapi/bus/queryBusRouteCoordinate?routeNumber=10&sxx=1
	public String QueryOriginLineTrack()
	{
		String url=GlobalVar.QUERY_BUS_LINE_TRACE_URL+"?routeNumber="+lineID+"&sxx="+direct;
		
		orginData=this.smartBusService.GetOriginData(url);
		
		return "success";
	}
	
	List<BusLineDetail> lineDetails;
	public List<BusLineDetail> getLineDetails() {
		return lineDetails;
	}
	
	/**
	 * 获取通过线路名线路详情
	 * @return
	 */
	public String QueryLineDetailInfoByName()
	{
		try 
		{
			lineName=URLEncoder.encode(lineName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_LINE+"?application=jiaotongju&name="+lineName);
		
		System.out.println(orginData);
		
		BusLineForQueryByName result=JSON.parseObject(orginData, BusLineForQueryByName.class);
		
		if(result.getLineList()!=null)
		{
			lineDetails=new ArrayList<BusLineDetail>();
			for(BusLine l:result.getLineList())
			{
				orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_LINE_ID+l.getId()+"/"+0+"/?linedetails=true&application=jiaotongju");
				
				BusLineDetail busLineDetail=JSON.parseObject(orginData,BusLineDetail.class);
				
				System.out.println(orginData);
				
				if(busLineDetail!=null)
					lineDetails.add(busLineDetail);
			}
		}
		
		return "success";
	}

	BusLineDetail busLineDetail;
	public BusLineDetail getBusLineDetail() {
		return busLineDetail;
	}
	BusStationDetail busStationDetail;
	public BusStationDetail getBusStationDetail() {
		return busStationDetail;
	}
	
	/**
	 * 通过线路id和站点ID获取信息
	 * @return
	 */
	public String QueryLineAndStationTogethor()
	{
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_LINE_ID+lineID+"/"+direct+"/?linedetails=true&application=jiaotongju");
		busLineDetail=JSON.parseObject(orginData,BusLineDetail.class);
		System.out.println(orginData);
		
		orginData=this.smartBusService.GetOriginData(GlobalVar.SMART_BUS_QUERY_BUS_STATION_BY_STATION_NAME+stationID+"/?station=true&linelist=true&busarrive=true&application=jiaotongju");
		
		System.out.println(orginData);
		busStationDetail=JSON.parseObject(orginData,BusStationDetail.class);
		
		
		
		return "success";
	}
	
	BusStationForQueryByName busStationForQueryByName;
	public BusStationForQueryByName getBusStationForQueryByName() {
		return busStationForQueryByName;
	}
	String QRCode;
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	
	//二维码
	public String QueryStationIDByQRCode()
	{
		busStationForQueryByName=new BusStationForQueryByName();
		busStationForQueryByName.setId(697);
		busStationForQueryByName.setName("国际会展中心");
		busStationForQueryByName.setLatitude(30.7413114117221);
		busStationForQueryByName.setLongitude(120.751857580017);
		busStationForQueryByName.setQrCode(QRCode);
		
		return "success";
	}
	
//	BusArrive result;
//	public BusArrive getResult() {
//		return result;
//	}
//	public String parse()
//	{
//		result=this.smartBusService.parse();
//		return "success";
//	}
	
}
