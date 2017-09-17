package com.highwaycenter.lxjbxx.model;

public class HJcXzqh implements java.io.Serializable {
	
	private static final long serialVersionUID = -3905307066654112684L;
	private Integer xzqhdm;
	private String xzqhmc;
	private String sjxzqhdm;
	private int ordercolumn;
	
	public HJcXzqh(){
		
	}
	
	
	public HJcXzqh(Integer xzqhdm, String xzqhmc, String sjxzqhdm) {
		super();
		this.xzqhdm = xzqhdm;
		this.xzqhmc = xzqhmc;
		this.sjxzqhdm = sjxzqhdm;
	}

	public Integer getXzqhdm() {
		return xzqhdm;
	}


	public void setXzqhdm(Integer xzqhdm) {
		this.xzqhdm = xzqhdm;
	}


	public String getXzqhmc() {
		return xzqhmc;
	}
	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}
	public String getSjxzqhdm() {
		return sjxzqhdm;
	}
	public void setSjxzqhdm(String sjxzqhdm) {
		this.sjxzqhdm = sjxzqhdm;
	}


	public int getOrdercolumn() {
		return ordercolumn;
	}


	public void setOrdercolumn(int ordercolumn) {
		this.ordercolumn = ordercolumn;
	}
	
	
	
	
	

}
