package com.highwaycenter.gljg.model;

public class HJcGlbmgx implements java.io.Serializable {

	private static final long serialVersionUID = 7099103877888459603L;
	private String bmdm;
	private String sjbmdm;
	
	public HJcGlbmgx(String bmdm,String sjbmdm){
		this.bmdm = bmdm;
		this.sjbmdm = sjbmdm;
	}
	
	public HJcGlbmgx(){
		
	}
	
	public String getBmdm() {
		return bmdm;
	}
	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}
	public String getSjbmdm() {
		return sjbmdm;
	}
	public void setSjbmdm(String sjbmdm) {
		this.sjbmdm = sjbmdm;
	}

	
	
}
