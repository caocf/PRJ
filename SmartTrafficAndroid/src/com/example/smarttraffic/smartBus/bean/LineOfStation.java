package com.example.smarttraffic.smartBus.bean;

/**
 * 途径站点线路（包含实时信息）
 * @author Administrator zhou
 *
 */
public class LineOfStation extends LineInfo
{
	public static final String[] STRING_CROWED=new String[]{"——","通畅","正常","拥挤"};
	public static final String[] STRING_SPEED=new String[]{"——","通畅","正常","拥堵"};
	
	double distance;		//距离
	int crowed;				//0、未知、1:正常；2：较拥挤 ;3、严重
	int speed;				//0、未知、1：正常；2：缓慢行驶；3、拥堵
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getCrowed() {
		return crowed;
	}
	public void setCrowed(int crowed) {
		this.crowed = crowed;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	boolean isSelect;
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	
	
}