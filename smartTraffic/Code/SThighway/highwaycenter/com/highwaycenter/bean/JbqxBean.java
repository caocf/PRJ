package com.highwaycenter.bean;

public class JbqxBean implements java.io.Serializable {

	private static final long serialVersionUID = 8511985288712263138L;
	
	private Integer qxbh;
	private String qxmc;
	private boolean isChoose;
	
	public JbqxBean(){
		
	}
	
	
	public JbqxBean(Integer qxbh, String qxmc, boolean isChoose) {
		super();
		this.qxbh = qxbh;
		this.qxmc = qxmc;
		this.isChoose = isChoose;
	}
	public Integer getQxbh() {
		return qxbh;
	}
	public void setQxbh(Integer qxbh) {
		this.qxbh = qxbh;
	}
	public String getQxmc() {
		return qxmc;
	}
	public void setQxmc(String qxmc) {
		this.qxmc = qxmc;
	}
	public boolean isChoose() {
		return isChoose;
	}
	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}
	
	
	
	

}
