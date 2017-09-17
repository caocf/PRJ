package com.highwaycenter.jd.model;

public  class HJdTxfs implements java.io.Serializable{
	
	private static final long serialVersionUID = -2631495916040927331L;
	private Integer txfsbh;//通讯方式编号
	private String txfs;//通讯方式名称
	public Integer getTxfsbh() {
		return txfsbh;
	}
	public void setTxfsbh(Integer txfsbh) {
		this.txfsbh = txfsbh;
	}
	public String getTxfs() {
		return txfs;
	}
	public void setTxfs(String txfs) {
		this.txfs = txfs;
	}
	
	

}
