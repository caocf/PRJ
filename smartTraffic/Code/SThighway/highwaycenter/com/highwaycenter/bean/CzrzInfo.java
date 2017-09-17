package com.highwaycenter.bean;

import java.sql.Timestamp;

import javax.persistence.Transient;

public class CzrzInfo {  //用于生成excel
	
	private String ryxm;    //操作人姓名
	private String rzbt;
	private String rzmc;
	private Timestamp czsj;
	private String rzlxmc;  //日志类型名称
	
	public CzrzInfo(){
		
	}
	public CzrzInfo(String ryxm, String rzbt, String rzmc, Timestamp czsj,
			String rzlxmc) {
		super();
		this.ryxm = ryxm;
		this.rzbt = rzbt;
		this.rzmc = rzmc;
		this.czsj = czsj;
		this.rzlxmc = rzlxmc;
	}
	public String getRyxm() {
		return ryxm;
	}
	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}
	public String getRzbt() {
		return rzbt;
	}
	public void setRzbt(String rzbt) {
		this.rzbt = rzbt;
	}
	public String getRzmc() {
		return rzmc;
	}
	public void setRzmc(String rzmc) {
		this.rzmc = rzmc;
	}
	public Timestamp getCzsj() {
		return czsj;
	}
	public void setCzsj(Timestamp czsj) {
		this.czsj = czsj;
	}
	public String getRzlxmc() {
		return rzlxmc;
	}
	public void setRzlxmc(String rzlxmc) {
		this.rzlxmc = rzlxmc;
	}
	
	

}
