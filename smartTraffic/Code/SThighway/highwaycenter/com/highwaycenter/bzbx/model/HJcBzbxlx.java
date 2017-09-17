package com.highwaycenter.bzbx.model;

public class HJcBzbxlx implements java.io.Serializable {


	private static final long serialVersionUID = 5460254969152992310L;



	private Integer bzbxlxbh;
	private String bzbxlxmc;
	
	public HJcBzbxlx(){
		
	}
	public HJcBzbxlx(Integer bzbxlxbh, String bzbxlxmc) {
		super();
		this.bzbxlxbh = bzbxlxbh;
		this.bzbxlxmc = bzbxlxmc;
	}
	public Integer getBzbxlxbh() {
		return bzbxlxbh;
	}
	public void setBzbxlxbh(Integer bzbxlxbh) {
		this.bzbxlxbh = bzbxlxbh;
	}
	public String getBzbxlxmc() {
		return bzbxlxmc;
	}
	public void setBzbxlxmc(String bzbxlxmc) {
		this.bzbxlxmc = bzbxlxmc;
	}
	
}
