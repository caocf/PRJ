package com.sts.air.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.sts.air.model.AirCity;
import com.sts.air.model.AirNo;
import com.sts.air.model.AirNoReal;
import com.sts.air.model.AirRealTimeSchedule;
import com.sts.air.model.AirSchedule;
import com.sts.air.model.AirStation;
import com.sts.air.service.AirScheduleService;
import com.sts.util.Webservice;

public class AirScheduleAction 
{	
	List<AirSchedule> schedules;
	List<AirCity> airCities;
	List<AirRealTimeSchedule> airRealTimeSchedules;
	AirScheduleService airScheduleService;
	AirNo airNo;
	String no;
	
	public void setAirScheduleService(AirScheduleService airScheduleService) {
		this.airScheduleService = airScheduleService;
	}

	public List<AirSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<AirSchedule> schedules) {
		this.schedules = schedules;
	}

	public List<AirCity> getAirCities() {
		return airCities;
	}

	private String startCity;
	private String arriveCity;
	private Date startDate;
	private int page;
	private int num;
	private int totalNum;
	
	private String airportName;		//机场名
	private int status;				//0:全部 1：进港 2：出港
	
		
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<AirRealTimeSchedule> getAirRealTimeSchedules() {
		return airRealTimeSchedules;
	}

	public void setAirRealTimeSchedules(
			List<AirRealTimeSchedule> airRealTimeSchedules) {
		this.airRealTimeSchedules = airRealTimeSchedules;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	private String cityName;
		
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getArriveCity() {
		return arriveCity;
	}

	public void setArriveCity(String arriveCity) {
		this.arriveCity = arriveCity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	

	public AirNo getAirNo() {
		return airNo;
	}

	public void setAirNo(AirNo airNo) {
		this.airNo = airNo;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	private final String WEBSERVICE_URL="http://webservice.webxml.com.cn/webservices/DomesticAirline.asmx";
	private final String WEBSERVICE_NAMESPACE="http://WebXml.com.cn/";
	private final String WEBSERVICE_METHOD="getDomesticAirlinesTime";
	
	
	private final String[] WEBSERVICE_PARAM_NAME=new String[]{"startCity","lastCity","theDate","UserID"};

	public String GetAirSchedule()
	{	
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(WEBSERVICE_PARAM_NAME[0],startCity);
		params.put(WEBSERVICE_PARAM_NAME[1], arriveCity);
		String tempDate="";
		if(startDate!=null)
		{
			tempDate=new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		}
		params.put(WEBSERVICE_PARAM_NAME[2],tempDate);

		params.put(WEBSERVICE_PARAM_NAME[3], "");
				
		Object object=Webservice.CallWebService(WEBSERVICE_URL, WEBSERVICE_NAMESPACE, WEBSERVICE_METHOD, params);
		
		if(object!=null && object instanceof SoapObject)
		{
			schedules=airScheduleService.parse((SoapObject)object,startDate);
		}
		
		if(schedules!=null)
		{
			totalNum=schedules.size();
		}
		
		if(page!=0&&num!=0)
		{
			schedules=airScheduleService.filterByNum(schedules, num, page);
		}
		
		return "success";
	}
	
	private final String WEBSERVICE_METHOD_OF_CITY="getDomesticCity";
	
	public String UpdateCity()
	{
		Object object=Webservice.CallWebService(WEBSERVICE_URL, WEBSERVICE_NAMESPACE, WEBSERVICE_METHOD_OF_CITY, null);
		
		if(object!=null && object instanceof SoapObject)
		{
			airCities=airScheduleService.GetCityList((SoapObject)object);
		
		}
		
		return "success";
	}
	
	public String GetAirCity()
	{
		if(cityName!=null && !cityName.equals(""))
		{
			airCities=airScheduleService.findCityByName(cityName);
		}
		else
		{
			airCities= airScheduleService.findAll();
		}
		
		return "success";
	}
	
	public String GetRealTimeAir()
	{
		airRealTimeSchedules=airScheduleService.GetRealTimeAir(airportName,status);
		return "success";
	}
	
	public String GetAirByAirno()
	{
		airNo=airScheduleService.GetAirByAirNo(no);
		return "success";
	}
	
	
	
	
	
	private AirNoReal airNoReal;	
	
	public AirNoReal getAirNoReal() {
		return airNoReal;
	}

	public void setAirNoReal(AirNoReal airNoReal) {
		this.airNoReal = airNoReal;
	}

	public String GetRealAirByAirno()
	{
		airNoReal=airScheduleService.GetAirRealByAirNo(no);
		return "success";
	}
	
	private List<AirStation> airStations;
	
	

	public List<AirStation> getAirStations() {
		return airStations;
	}

	public void setAirStations(List<AirStation> airStations) {
		this.airStations = airStations;
	}

	public String GetStationInfoByAirno()
	{
		airStations=airScheduleService.GetAiStaionByAirNo(no);
		return "success";
	}
	
	String stationUrl;
	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public String GetStationDetailUrl()
	{
		stationUrl=airScheduleService.GetAirStationDetailUrl(airportName);
		return "success";
	}
}
