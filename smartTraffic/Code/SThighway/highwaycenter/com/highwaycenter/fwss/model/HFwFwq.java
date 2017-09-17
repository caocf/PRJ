package com.highwaycenter.fwss.model;

import javax.persistence.Transient;

public class HFwFwq implements java.io.Serializable {

	private static final long serialVersionUID = 22814908366711275L;
	private Integer fwqbh;//服务区编号
	private String fwqmc;//服务区名称
	private String ssgs;//所属公司
	private String xlmc;//线路名称
	private String sxjkzh;//上行进口桩号
	private String xxjkzh;//下行进口桩号
	private String sxckzh;//上行出口桩号
	private String xxckzh;//下行出口桩号
	private Integer jyss;//加油设施
	private Integer cyss;//餐饮设施	
	private Integer zsss;//住宿设施
	private Integer gwss;//购物设施
	private Integer clwxss;//车辆维修设施
	private String glzdzh;//管理中队桩号
	private String zczzh;//治超站桩号
	private String gljjzh;//管理交警桩号
	@Transient
	private String lxjc;//路线代码+路线简称
	
	public HFwFwq(){
		
	}
	public HFwFwq(Integer fwqbh,String fwqmc,String ssgs,String xlmc){//undo
		super();
		this.fwqbh = fwqbh;
		this.fwqmc = fwqmc;
		this.ssgs = ssgs;
		this.xlmc = xlmc;
		
		
	}
	
	public HFwFwq(Integer fwqbh, String fwqmc, String ssgs, String xlmc,
			String sxjkzh, String xxjkzh, String sxckzh, String xxckzh,
			Integer jyss, Integer cyss, Integer zsss, Integer gwss,
			Integer clwxss, String glzdzh, String zczzh, String gljjzh) {
		super();
		this.fwqbh = fwqbh;
		this.fwqmc = fwqmc;
		this.ssgs = ssgs;
		this.xlmc = xlmc;
		this.sxjkzh = sxjkzh;
		this.xxjkzh = xxjkzh;
		this.sxckzh = sxckzh;
		this.xxckzh = xxckzh;
		this.jyss = jyss;
		this.cyss = cyss;
		this.zsss = zsss;
		this.gwss = gwss;
		this.clwxss = clwxss;
		this.glzdzh = glzdzh;
		this.zczzh = zczzh;
		this.gljjzh = gljjzh;
	}
	public Integer getFwqbh() {
		return fwqbh;
	}
	public void setFwqbh(Integer fwqbh) {
		this.fwqbh = fwqbh;
	}
	public String getFwqmc() {
		return fwqmc;
	}
	public void setFwqmc(String fwqmc) {
		this.fwqmc = fwqmc;
	}
	public String getSsgs() {
		return ssgs;
	}
	public void setSsgs(String ssgs) {
		this.ssgs = ssgs;
	}
	public String getXlmc() {
		return xlmc;
	}
	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}
	public String getSxjkzh() {
		return sxjkzh;
	}
	public void setSxjkzh(String sxjkzh) {
		this.sxjkzh = sxjkzh;
	}
	public String getXxjkzh() {
		return xxjkzh;
	}
	public void setXxjkzh(String xxjkzh) {
		this.xxjkzh = xxjkzh;
	}
	public String getSxckzh() {
		return sxckzh;
	}
	public void setSxckzh(String sxckzh) {
		this.sxckzh = sxckzh;
	}

	
	
	public String getXxckzh() {
		return xxckzh;
	}
	public void setXxckzh(String xxckzh) {
		this.xxckzh = xxckzh;
	}
	public Integer getJyss() {
		return jyss;
	}
	public void setJyss(Integer jyss) {
		this.jyss = jyss;
	}
	public Integer getCyss() {
		return cyss;
	}
	public void setCyss(Integer cyss) {
		this.cyss = cyss;
	}
	public Integer getZsss() {
		return zsss;
	}
	public void setZsss(Integer zsss) {
		this.zsss = zsss;
	}
	public Integer getGwss() {
		return gwss;
	}
	public void setGwss(Integer gwss) {
		this.gwss = gwss;
	}
	public Integer getClwxss() {
		return clwxss;
	}
	public void setClwxss(Integer clwxss) {
		this.clwxss = clwxss;
	}
	public String getGlzdzh() {
		return glzdzh;
	}
	public void setGlzdzh(String glzdzh) {
		this.glzdzh = glzdzh;
	}
	public String getZczzh() {
		return zczzh;
	}
	public void setZczzh(String zczzh) {
		this.zczzh = zczzh;
	}
	public String getGljjzh() {
		return gljjzh;
	}
	public void setGljjzh(String gljjzh) {
		this.gljjzh = gljjzh;
	}

	
	
	
	public String getLxjc() {
		return lxjc;
	}
	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}
	
	
	
	
	
	
}
