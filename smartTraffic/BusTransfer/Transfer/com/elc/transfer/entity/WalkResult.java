package com.elc.transfer.entity;

public class WalkResult
{
	private int seq;
	private String start;
	private double lan1;
	private double lon1;
	
	private String end;
	private double lan2;
	private double lon2;
	
	private String direct;
	private double distance;
	private int time;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public double getLan1() {
		return lan1;
	}
	public void setLan1(double lan1) {
		this.lan1 = lan1;
	}
	public double getLon1() {
		return lon1;
	}
	public void setLon1(double lon1) {
		this.lon1 = lon1;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public double getLan2() {
		return lan2;
	}
	public void setLan2(double lan2) {
		this.lan2 = lan2;
	}
	public double getLon2() {
		return lon2;
	}
	public void setLon2(double lon2) {
		this.lon2 = lon2;
	}
	public String getDirect() {
		return direct;
	}
	public void setDirect(String direct) {
		this.direct = direct;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
