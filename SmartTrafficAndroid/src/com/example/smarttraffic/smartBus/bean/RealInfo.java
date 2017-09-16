package com.example.smarttraffic.smartBus.bean;

/**
 * 线路实时数据
 * @author Administrator zhou
 *
 */
public class RealInfo 
{
	int num;						//序号
	double distance;				//距离（米）
	int interStationNum;			//距离（站点）
	int crowed;						//拥挤度
	int speed;						//速度
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getInterStationNum() {
		return interStationNum;
	}
	public void setInterStationNum(int interStationNum) {
		this.interStationNum = interStationNum;
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
}
