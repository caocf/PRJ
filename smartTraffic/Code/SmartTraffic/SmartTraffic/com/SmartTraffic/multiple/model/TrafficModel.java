package com.SmartTraffic.multiple.model;

public class TrafficModel {
	private double cbsl;
	private double cbdw;
	
	public TrafficModel(){
		super();
	}
	
	
	public TrafficModel(double cbsl, double cbdw) {
		super();
		this.cbsl = cbsl;
		this.cbdw = cbdw;
	}


	public double getCbsl() {
		return cbsl;
	}
	public void setCbsl(double cbsl) {
		this.cbsl = cbsl;
	}
	public double getCbdw() {
		return cbdw;
	}
	public void setCbdw(double cbdw) {
		this.cbdw = cbdw;
	}
	
	

}
