package com.sts.parking.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sts.common.util.GlobalVar;
import com.sts.parking.model.BusInfor;
import com.sts.parking.model.Parking;
import com.sts.parking.model.ParkingCG;
import com.sts.parking.model.ParkingCGReal;
import com.sts.parking.model.SearchPoint;
import com.sts.parking.service.ParkingService;


public class ParkingAction {
	private String recodeString;
	private List<?> recodelist;
	private Parking parking=new Parking();//停车场
	private SearchPoint sp=new SearchPoint();//查询周边、热点、关键字等
	private BusInfor busInfor=new BusInfor();
	private PostDateFromSIWEI pdfs=new PostDateFromSIWEI();
	
	public Parking getParking() {
		return parking;
	}
	public void setParking(Parking parking) {
		this.parking = parking;
	}
	
	public String getRecodeString() {
		return recodeString;
	}
	public void setRecodeString(String recodeString) {
		this.recodeString = recodeString;
	}
	
	public List<?> getRecodelist() {
		return recodelist;
	}
	public void setRecodelist(List<?> recodelist) {
		this.recodelist = recodelist;
	}
	
	public SearchPoint getSp() {
		return sp;
	}
	public void setSp(SearchPoint sp) {
		this.sp = sp;
	}
	
	public BusInfor getBusInfor() {
		return busInfor;
	}
	public void setBusInfor(BusInfor busInfor) {
		this.busInfor = busInfor;
	}
	//停车场查询
	public String queryparkingplacecoordinate(){
		try {
			String url=GlobalVar.PARKING_ACTION+"?parkType="+parking.getParkType()+"&regionCode="+parking.getRegionCode()+
			"&chargeWay="+parking.getChargeWay()+"&pno="+parking.getPno()+"&pageSize="+parking.getPageSize()+"&coId="+parking.getCoId();
			recodelist=pdfs.ParkingData(url);
			System.out.println("recodelist:"+recodelist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//周边搜索
	//http://115.231.73.253:8091/zhjtapi/ajax/searchsurround?loc=120.77534,30.74598&m=2000&key=酒店 &startPosition=1&pageNumber=10&json=0 
	public String searchsurround(){
		try {
			String url=GlobalVar.BACKGROUND_ACTION+"?loc="+sp.getLoc()+"&m="+sp.getRange()+
			"&key="+sp.getKey()+"&startPosition="+sp.getStartPosition()+"&pageNumber="+sp.getPageNumber()+"&json="+sp.getJson();
			recodelist=pdfs.ParkingData(url);
			System.out.println("recodelist:"+recodelist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//关键字搜索地址 
	//http://115.231.73.253:8091/zhjtapi/ajax/searchpoibykey?key=酒店&pageNumber=2&startPosition=1&json=0 
	public String searchpoibykey(){
		try {
			String url=GlobalVar.POI_KEY_ACTION+"?key="+sp.getKey()+"&pageNumber="+sp.getPageNumber()+
			"&startPosition="+sp.getStartPosition()+"&json="+sp.getJson();
			recodeString = pdfs.GetData(url);
			System.out.println("recodeString:"+recodeString);System.out.println("url:"+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//POI分类搜索地址 
	//http://115.231.73.253:8091/zhjtapi/ajax/searchpoibycat?poiCat=0202&pageNumber=10&startPosition=1&json=0 
	public String searchpoibycat(){
		try 
		{
			String url=GlobalVar.POI_CAT_ACTION+"?poiCat="+sp.getPoiCat()+"&pageNumber="+sp.getPageNumber()+"&startPosition="+sp.getStartPosition()+"&json="+sp.getJson();
			//recodelist=pdfs.ParkingData(url);
			recodeString = pdfs.GetData(url);
			System.out.println("recodeString:"+recodeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//POI分类名称的模糊查询
	public String searchpoibycatByName(){
		try {
			String key=sp.getKey();
			List<HashMap<String,String>> recodelist2=new ArrayList<HashMap<String,String>>();
			for(int i=0;i<GlobalVar.POT_CODE_LIST.length;i++ ){
				if(GlobalVar.POT_NAME_LIST[i].contains(key))
				{
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("code", GlobalVar.POT_CODE_LIST[i]);
					map.put("name", GlobalVar.POT_NAME_LIST[i]);
					recodelist2.add(map);
				}	
			}	
			recodelist=recodelist2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//公交线路查询接口
	//http://115.231.73.253:8091/zhjtapi/bus/queryBusRoute?routeName=88路
	//http://115.231.73.253:8091/zhjtapi/bus/queryBusRoute?routeId=100001
	public String queryBusRoute(){
		try {
			String url=null;
			if(busInfor.getRouteName()!=null){
				url=GlobalVar.QUERY_BUS_ROUTE_ACTION+"?routeName="+busInfor.getRouteName();
			}
			else{
				url=GlobalVar.QUERY_BUS_ROUTE_ACTION+"?&routeId="+busInfor.getRouteId();
			}
			recodeString = pdfs.GetData(url);
			System.out.println("recodeString:"+recodeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//公交站点查询接口-name
	//http://115.231.73.253:8091/zhjtapi/bus/queryBusStation?busStationName=银行
	public String queryBusStation(){
		try {
			String url=null;
			if(busInfor.getBusStationName()!=null){
			 url=GlobalVar.QUERY_BUS_STATION_ACTION+"?busStationName="+busInfor.getBusStationName();
			}
			/*else if(busInfor.getLongitude()!=null){
				 url=GlobalVar.QUERY_BUS_STATION_ACTION+"?longitude="+busInfor.getLongitude()+"&latitude="+busInfor.getLatitude();
			}else if(busInfor.getBusStationId()!=null){
				 url=GlobalVar.QUERY_BUS_STATION_ACTION+"?busStationId="+busInfor.getBusStationId();
			}*/
			recodelist=pdfs.ParkingData(url);
			System.out.println("recodelist:"+recodelist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//周边公交站点查询
	//http://115.231.73.253:8091/zhjtapi/bus/querySurroundBusStation?longitude=120.76231083703&latitude=30.764884281589
	public String querySurroundBusStation(){
		try {
			String url=GlobalVar.QUERY_SURROUND_BUS_STATION_ACTION+"?longitude="+busInfor.getLongitude()+"&latitude="+busInfor.getLatitude();
			recodeString = pdfs.GetData(url);
			System.out.println("recodeString:"+recodeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//公交站点查询接口-ID
	//http://115.231.73.253:8091/zhjtapi/bus/queryBusRouteByStationId?busStationId=1040
	public String queryBusStationId(){
		try {
			String url=GlobalVar.QUERY_BUS_STATION_ID_ACTION+"?busStationId="+busInfor.getBusStationId();
			recodeString = pdfs.GetData(url);
			System.out.println("recodeString:"+recodeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//公交换乘接口
	//http://115.231.73.253:8091/zhjtapi/bus/queryBusTransfer?startPointLon=120.76231083703&startPointLat=30.764884281589&endPointLon=120.74941934672&endPointLat=30.761704564682 
	public String queryBusTransfer(){
		try {
			String url=GlobalVar.QUERY_BUS_TRANSFER_ACTION+"?startPointLon="+busInfor.getStartpointlon()+"&startPointLat="+busInfor.getStartpointlat()
			+"&endPointLon="+busInfor.getEndpointlon()+"&endPointLat="+busInfor.getEndpointlat()+"&order="+busInfor.getOrder();
			recodeString = pdfs.GetData(url);
			System.out.println("recodeString:"+recodeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	//自驾道线路路查询
	//http://115.231.73.253:8091/zhjtapi/ajax/queryroad?orig=120.77534,30.74598&dest=120.77534,30.74598&style=3&json=2
	public String queryroad(){
		try {
			String url=GlobalVar.QUERY_ROAD_ACTION+"?orig="+busInfor.getStartpointlon()+"&dest="+busInfor.getEndpointlon()
			+"&mid="+busInfor.getRouteName()+"&style="+busInfor.getOrder();
			recodeString = pdfs.GetData(url);System.out.println("url:"+url);
			System.out.println("recodeString:"+recodeString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 以下数据接口从城管直接调用
	 */
	
	
	ParkingService parkingService;
	public void setParkingService(ParkingService parkingService) {
		this.parkingService = parkingService;
	}
	
	List<ParkingCG> parkingCGList;
	List<ParkingCGReal> parkingCGRealList;
	public List<ParkingCG> getParkingCGList() {
		return parkingCGList;
	}
	public List<ParkingCGReal> getParkingCGRealList() {
		return parkingCGRealList;
	}
	
	/**
	 * 查询所有停车场信息
	 * @return
	 */
	public String queryParkingFromCG()
	{
		parkingCGList=this.parkingService.queryParkingFromCG();
	
		total=parkingCGList.size()>0?parkingCGList.size():0;
		
		if(page>0&&num>0)
			parkingCGList=this.parkingService.queryCount(parkingCGList, page, num);
		
		return "success";
	}
	
	/**
	 * 查询所有停车场实时信息
	 * @return
	 */
	public String queryParkingCountFromCG()
	{
		parkingCGRealList=this.parkingService.queryParkingRealCountFromCG();
		
		total=parkingCGRealList.size()>0?parkingCGRealList.size():0;
		
		if(page>0&&num>0)
			parkingCGRealList=this.parkingService.queryRealCount(parkingCGRealList, page, num);

		return "success";
	}
	
	
	String parkingName;
	int page;
	int num;
	int total;
	String parkingID;
	int parkType;
	boolean fullInfo;
	int showReal;			//是否显示实时信息，1显示，-1不 显示 0默认显示
	
	public void setShowReal(int showReal) {
		this.showReal = showReal;
	}
	public void setFullInfo(boolean fullInfo) {
		this.fullInfo = fullInfo;
	}
	public void setParkingID(String parkingID) {
		this.parkingID = parkingID;
	}
	public void setParkType(int parkType) {
		this.parkType = parkType;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	/**
	 * 通过名称查询
	 * @return
	 */
	public String queryParkingFormCGByName()
	{
		parkingCGList=this.parkingService.queryByName(parkingName);
		total=parkingCGList.size()>0?parkingCGList.size():0;
		
		if(page>0&&num>0)
			parkingCGList=this.parkingService.queryCount(parkingCGList, page, num);
		
		return "success";
	}
	
	/**
	 * 通过名称和类型同时查询   0不区分类型
	 * @return
	 */
	public String queryParkingFromCGByNameAndType()
	{
		boolean isShow=false;
		if(showReal==0 || showReal==1)
			isShow=true;
		
		parkingCGList=this.parkingService.queryByNameAndType(parkType, parkingName,isShow);
		
		total=parkingCGList.size()>0?parkingCGList.size():0;
		
		if(page>0&&num>0)
			parkingCGList=this.parkingService.queryCount(parkingCGList, page, num);
		
		return "success";
		
	}
	
	
	/**
	 * 通过类型查询
	 * @return
	 */
	public String queryParkingFromCGByType()
	{
		if(parkType==0)
		{
			parkingCGList=this.parkingService.queryParkingFromCG();
			
			total=parkingCGList.size()>0?parkingCGList.size():0;
			
			if(page>0&&num>0)
				parkingCGList=this.parkingService.queryCount(parkingCGList, page, num);
			return "success";
		}
		
		parkingCGList=this.parkingService.queryByType(parkType);
		
		total=parkingCGList.size()>0?parkingCGList.size():0;
		
		if(page>0&&num>0)
			parkingCGList=this.parkingService.queryCount(parkingCGList, page, num);
		
		return "success";
		
	}
	
	ParkingCG parkingCG;
	ParkingCGReal parkingCGReal;
	public ParkingCG getParkingCG() {
		return parkingCG;
	}
	public ParkingCGReal getParkingCGReal() {
		return parkingCGReal;
	}
	
	/**
	 * 通过id查询
	 * @return
	 */
	public String queryParkingFromCGByID()
	{
		if(fullInfo)
			parkingCG=this.parkingService.queryFullByID(parkingID);
		else
			parkingCGReal=this.parkingService.queryRealByID(parkingID);
			
		return "success";
		
	}
}
