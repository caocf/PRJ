package com.sts.train.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.sts.train.model.TrainStation;
import com.sts.train.model.TrainSchedule;
import com.sts.train.model.TrainTickets;
import com.sts.train.service.TrainScheduleService;
import com.sts.util.Webservice;

public class TrainScheduleAction 
{	
	List<TrainSchedule> schedules;
	
	public List<TrainSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<TrainSchedule> schedules) {
		this.schedules = schedules;
	}
	
	private String startStation;
	private String arriveStation;
	
	private TrainScheduleService trainScheduleService;
	
	
	public void setTrainScheduleService(TrainScheduleService trainScheduleService) {
		this.trainScheduleService = trainScheduleService;
	}

	private int page;
	private int num;
	private int type;	
	private int leaveTime;
	private int reachTime;
	
	public int getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime;
	}

	public int getReachTime() {
		return reachTime;
	}

	public void setReachTime(int reachTime) {
		this.reachTime = reachTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getArriveStation() {
		return arriveStation;
	}

	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}

	private final String WEBSERVICE_URL="http://webservice.webxml.com.cn/WebServices/TrainTimeWebService.asmx";
	private final String WEBSERVICE_NAMESPACE="http://WebXml.com.cn/";
	private final String WEBSERVICE_METHOD="getStationAndTimeByStationName";
	
	private final String[] WEBSERVICE_PARAM_NAME=new String[]{"StartStation","ArriveStation","UserID"};

	public String GetTrainSchedule()
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(WEBSERVICE_PARAM_NAME[0],startStation);
		params.put(WEBSERVICE_PARAM_NAME[1], arriveStation);
		params.put(WEBSERVICE_PARAM_NAME[2], "");
				
		Object object=Webservice.CallWebService(WEBSERVICE_URL, WEBSERVICE_NAMESPACE, WEBSERVICE_METHOD, params);
		
		if(object!=null && object instanceof SoapObject)
		{
			schedules=TrainScheduleService.parse((SoapObject)object);
			
			if(page!=0 && num!=0)
			{
				schedules=TrainScheduleService.filterByNum(schedules, page, num);
			}
			
			if(type!=0)
			{
				schedules=TrainScheduleService.filterByType(schedules, type);
			}
			
			if(leaveTime!=0)
			{
				schedules=TrainScheduleService.fileterByTime(schedules, leaveTime, 1);
			}
			
			if(reachTime!=0)
			{
				schedules=TrainScheduleService.fileterByTime(schedules, reachTime, 2);
			}
		}
		
		return "success";
	}
	
	
	TrainTickets trainTickets;
	String trainNo;
		
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public TrainTickets getTrainTickets() {
		return trainTickets;
	}

	public String GetTicketsByTrainNoAndStartEnd()
	{
		trainTickets=trainScheduleService.getTicketsByTrainNoAndName(trainNo, startStation, arriveStation);
		return "success";
	}
	
	
	
	private List<TrainStation> trainStations;
	
	public List<TrainStation> getTrainStations() {
		return trainStations;
	}

	public void setTrainStations(List<TrainStation> trainStations) {
		this.trainStations = trainStations;
	}

	public String GetStationInfoByTrainno()
	{
		trainStations=trainScheduleService.GetTrainStaionByTrainNo(trainNo);
		return "success";
	}
	
	String stationUrl;
	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public String GetTrainStationDetailUrl()
	{
		stationUrl=trainScheduleService.GetTrainStationDetailUrl(startStation);
		return "success";
	}
}
