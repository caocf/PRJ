package com.example.smarttraffic.smartBus.bean;

import java.util.List;

public class LineOnStation 
{
	private int Id;
	private String Name;
	private int Direct;
	private String LineType;
	private boolean IsRing;
	private double Price;
	private double NormalPrice;
	private double SeasonPrice;
	private String IcCard;
	private int StartStationId;
	private int EndStationId;
	private String StartStationName;
	private String EndStationName;
	private String StartTime;
	private String EndTime;
	private String Remark;
	private String TicketType;
	private String Time;
	
	private boolean isSelect;
	
	
	private List<BusLocation> BusLocationList;
	
	
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getDirect() {
		return Direct;
	}
	public void setDirect(int direct) {
		Direct = direct;
	}
	public String getLineType() {
		return LineType;
	}
	public void setLineType(String lineType) {
		LineType = lineType;
	}
	public boolean isIsRing() {
		return IsRing;
	}
	public void setIsRing(boolean isRing) {
		IsRing = isRing;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public double getNormalPrice() {
		return NormalPrice;
	}
	public void setNormalPrice(double normalPrice) {
		NormalPrice = normalPrice;
	}
	public double getSeasonPrice() {
		return SeasonPrice;
	}
	public void setSeasonPrice(double seasonPrice) {
		SeasonPrice = seasonPrice;
	}
	public String getIcCard() {
		return IcCard;
	}
	public void setIcCard(String icCard) {
		IcCard = icCard;
	}
	public int getStartStationId() {
		return StartStationId;
	}
	public void setStartStationId(int startStationId) {
		StartStationId = startStationId;
	}
	public int getEndStationId() {
		return EndStationId;
	}
	public void setEndStationId(int endStationId) {
		EndStationId = endStationId;
	}
	public String getStartStationName() {
		return StartStationName;
	}
	public void setStartStationName(String startStationName) {
		StartStationName = startStationName;
	}
	public String getEndStationName() {
		return EndStationName;
	}
	public void setEndStationName(String endStationName) {
		EndStationName = endStationName;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public List<BusLocation> getBusLocationList() {
		return BusLocationList;
	}
	public void setBusLocationList(List<BusLocation> busLocationList) {
		BusLocationList = busLocationList;
	}
	public String getTicketType() {
		return TicketType;
	}
	public void setTicketType(String ticketType) {
		TicketType = ticketType;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	
	
}
