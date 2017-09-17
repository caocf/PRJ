package com.highwaycenter.bean;

public class XzqhBean implements java.io.Serializable {

	private static final long serialVersionUID = -1612463856885578619L;
	private Integer xzqhdm;
	private String xzqh;
		
	public XzqhBean(){
		super();
	}
	
	public XzqhBean(Integer xzqhdm,String xzqh){
		super();
		this.xzqh = xzqh;
		this.xzqhdm = xzqhdm;
	}
	public Integer getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(Integer xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getXzqh() {
		return xzqh;
	}
	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}
	
	
	

}
