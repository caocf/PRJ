package com.sts.station.action;

import java.util.Date;
import java.util.List;

import com.sts.station.model.Agent;
import com.sts.station.model.BusCode;
import com.sts.station.model.BusSchedule;
import com.sts.station.model.BusStation;
import com.sts.station.model.BusStationIntroduce;
import com.sts.station.model.BusStationRealInfo;
import com.sts.station.service.BusStationService;

public class BusStationAction 
{
	private String startStation;
	private String arriveStation;
	private Date date;
	private int page;
	private int num;
	private int type;	
	private int leaveTime;
	private int reachTime;
	private int total;
	
	BusStationService busStationService;
	private List<BusSchedule> busSchedules;
	private List<BusStation> busStations;
	private List<BusStationRealInfo> busStationRealInfos;
	
	private String stationName;
	private String busCode;
	private BusCode busCodes;
	
	
	public List<BusStationRealInfo> getBusStationRealInfos() {
		return busStationRealInfos;
	}
	public void setBusStationRealInfos(List<BusStationRealInfo> busStationRealInfos) {
		this.busStationRealInfos = busStationRealInfos;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	
	public BusCode getBusCodes() {
		return busCodes;
	}
	public void setBusCodes(BusCode busCodes) {
		this.busCodes = busCodes;
	}
	public List<BusStation> getBusStations() {
		return busStations;
	}
	public void setBusStations(List<BusStation> busStations) {
		this.busStations = busStations;
	}
	public List<BusSchedule> getBusSchedules() {
		return busSchedules;
	}
	public void setBusSchedules(List<BusSchedule> busSchedules) {
		this.busSchedules = busSchedules;
	}
	public void setBusStationService(BusStationService busStationService) {
		this.busStationService = busStationService;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
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
	
	public String GetBusStationPositionInfo()
	{
		busStations=busStationService.GetBusStationPositionInfo();
		total=(busStations==null?0:busStations.size());
		return "success";
	}
	public String GetBusTickets()
	{
		busSchedules=busStationService.GetBusTickets(startStation,arriveStation,date);
		total=(busSchedules==null?0:busSchedules.size());
		return "success";
	}
	
	public String GetBusTicketsByStationName()
	{
		busSchedules=busStationService.GetBusTickets(stationName);
		total=(busSchedules==null?0:busSchedules.size());
		return "success";
	}
	
	public String GetBusCodeByStationName()
	{
		if(!stationName.equals(""))
			busStationRealInfos=busStationService.SearchBusCodesByStation(stationName);
		total=(busStationRealInfos==null?0:1);
		return "success";
	}
	
	public String GetBusCodeByBusCode()
	{
		if(!busCode.equals(""))
			busCodes=busStationService.SearchCodesByBusCode(busCode);
		total=(busCodes==null?0:1);
		return "success";
	}
	
	public String GetBusStation()
	{
		return "success";
	}
	
	
	private List<BusStationIntroduce> busStationIntroduces;
	
	public List<BusStationIntroduce> getBusStationIntroduces() {
		return busStationIntroduces;
	}
	public void setBusStationIntroduces(
			List<BusStationIntroduce> busStationIntroduces) {
		this.busStationIntroduces = busStationIntroduces;
	}
	public String GetStationInfoByBusNo()
	{
		busStationIntroduces=busStationService.GetBusStaionByBusNo(busCode);
		return "success";
	}
	
	String stationUrl;
	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public String GetBusStationDetailUrl()
	{
		stationUrl=busStationService.GetBusStationDetailUrl(startStation);
		return "success";
	}
	
	
	List<Agent> agents;
	
	public List<Agent> getAgents() {
		return agents;
	}
	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public String GetAgent()
	{
		agents=busStationService.GetAgentList(page, num);	
		return "success";
	}
	
}
