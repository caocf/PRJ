package com.example.smarttraffic.train;

/**
 * 铁路车票信息
 * @author Administrator zhou
 *
 */
public class TrainTickets 
{
	private int id;
	private String leaveTime;
	private String reachTime;
	private String trainNumber;
	private String trainKind;
	private String startCity;
	private String endCity;
	private String startKind;
	private String endKind;
	private String firstCity;
	private String lastCity;
	
	private double length;
	
	private String costTime;
	
	
	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLeaveTime()
	{
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime)
	{
		this.leaveTime = leaveTime;
	}

	public String getReachTime()
	{
		return reachTime;
	}

	public void setReachTime(String reachTime)
	{
		this.reachTime = reachTime;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainKind() {
		return trainKind;
	}

	public void setTrainKind(String trainKind) {
		this.trainKind = trainKind;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getStartKind() {
		return startKind;
	}

	public void setStartKind(String startKind) {
		this.startKind = startKind;
	}

	public String getEndKind() {
		return endKind;
	}

	public void setEndKind(String endKind) {
		this.endKind = endKind;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	
	public String getFirstCity()
	{
		return firstCity;
	}

	public void setFirstCity(String firstCity)
	{
		this.firstCity = firstCity;
	}

	public String getLastCity()
	{
		return lastCity;
	}

	public void setLastCity(String lastCity)
	{
		this.lastCity = lastCity;
	}


	private int first;
	private int second;
	private int business;
	private int other;
	
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getBusiness() {
		return business;
	}
	public void setBusiness(int business) {
		this.business = business;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
}

