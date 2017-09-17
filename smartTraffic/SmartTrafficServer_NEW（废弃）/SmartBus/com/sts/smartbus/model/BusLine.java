package com.sts.smartbus.model;

/**
 * 公交线路
 * @author Administrator
 *
 */
public class BusLine 
{
	private int Id;
	private String Name;
	private String ShortName;
	private String LineType;
	private boolean IsRing;
	private int UpStartStationId;
	private int UpEndStationId;
	private int DownStartStationId;
	private int DownEndStationId;
	private double Price;
	private double NormalPrice;
	private double SeasonPrice;
	private String IcCard;
	private String LineUpStartTime;
	private String LineUpEndTime;
	private String LineDownStartTime;
	private String LineDownEndTime;
	private String UpStartStationName;
	private String UpEndStationName;
	private String DownStartStationName;
	private String DownEndStationName;
	private String Remark;
	private int LinedownIntervalTime;
	private int LinedownLength;
	private int LineupIntervalTime;
	private int LineupLength;
	
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
	public String getShortName() {
		return ShortName;
	}
	public void setShortName(String shortName) {
		ShortName = shortName;
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
	public int getUpStartStationId() {
		return UpStartStationId;
	}
	public void setUpStartStationId(int upStartStationId) {
		UpStartStationId = upStartStationId;
	}
	public int getUpEndStationId() {
		return UpEndStationId;
	}
	public void setUpEndStationId(int upEndStationId) {
		UpEndStationId = upEndStationId;
	}
	public int getDownStartStationId() {
		return DownStartStationId;
	}
	public void setDownStartStationId(int downStartStationId) {
		DownStartStationId = downStartStationId;
	}
	public int getDownEndStationId() {
		return DownEndStationId;
	}
	public void setDownEndStationId(int downEndStationId) {
		DownEndStationId = downEndStationId;
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
	public String getLineUpStartTime() {
		return LineUpStartTime;
	}
	public void setLineUpStartTime(String lineUpStartTime) {
		LineUpStartTime = lineUpStartTime;
	}
	public String getLineUpEndTime() {
		return LineUpEndTime;
	}
	public void setLineUpEndTime(String lineUpEndTime) {
		LineUpEndTime = lineUpEndTime;
	}
	public String getLineDownStartTime() {
		return LineDownStartTime;
	}
	public void setLineDownStartTime(String lineDownStartTime) {
		LineDownStartTime = lineDownStartTime;
	}
	public String getLineDownEndTime() {
		return LineDownEndTime;
	}
	public void setLineDownEndTime(String lineDownEndTime) {
		LineDownEndTime = lineDownEndTime;
	}
	public String getUpStartStationName() {
		return UpStartStationName;
	}
	public void setUpStartStationName(String upStartStationName) {
		UpStartStationName = upStartStationName;
	}
	public String getUpEndStationName() {
		return UpEndStationName;
	}
	public void setUpEndStationName(String upEndStationName) {
		UpEndStationName = upEndStationName;
	}
	public String getDownStartStationName() {
		return DownStartStationName;
	}
	public void setDownStartStationName(String downStartStationName) {
		DownStartStationName = downStartStationName;
	}
	public String getDownEndStationName() {
		return DownEndStationName;
	}
	public void setDownEndStationName(String downEndStationName) {
		DownEndStationName = downEndStationName;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public int getLinedownIntervalTime() {
		return LinedownIntervalTime;
	}
	public void setLinedownIntervalTime(int linedownIntervalTime) {
		LinedownIntervalTime = linedownIntervalTime;
	}
	public int getLinedownLength() {
		return LinedownLength;
	}
	public void setLinedownLength(int linedownLength) {
		LinedownLength = linedownLength;
	}
	public int getLineupIntervalTime() {
		return LineupIntervalTime;
	}
	public void setLineupIntervalTime(int lineupIntervalTime) {
		LineupIntervalTime = lineupIntervalTime;
	}
	public int getLineupLength() {
		return LineupLength;
	}
	public void setLineupLength(int lineupLength) {
		LineupLength = lineupLength;
	}
}
