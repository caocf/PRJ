package com.elc.transfer.entity;

import java.util.ArrayList;
import java.util.List;

public class Transfer {
	public Transfer()
	{
		walks=new ArrayList<WalkResult>();
		buss=new ArrayList<BusResult>();
		bikes=new ArrayList<BikeResult>();
	}
	
	private double startLan;
	private double endLan;
	private double startLon;
	private double endLon;
	
	private List<WalkResult> walks;
	private List<BusResult> buss;
	private List<BikeResult> bikes;
	
	public List<WalkResult> getWalks() {
		return walks;
	}
	public List<BusResult> getBuss() {
		return buss;
	}
	public void setWalks(List<WalkResult> walks) {
		this.walks = walks;
	}
	public void setBuss(List<BusResult> buss) {
		this.buss = buss;
	}
	public List<BikeResult> getBikes() {
		return bikes;
	}
	public void setBikes(List<BikeResult> bikes) {
		this.bikes = bikes;
	}
	public double getStartLan() {
		return startLan;
	}
	public void setStartLan(double startLan) {
		this.startLan = startLan;
	}
	public double getEndLan() {
		return endLan;
	}
	public void setEndLan(double endLan) {
		this.endLan = endLan;
	}
	public double getStartLon() {
		return startLon;
	}
	public void setStartLon(double startLon) {
		this.startLon = startLon;
	}
	public double getEndLon() {
		return endLon;
	}
	public void setEndLon(double endLon) {
		this.endLon = endLon;
	}
	
}
