package com.highwaycenter.fwss.model;

import javax.persistence.Transient;

public class HFwSfz implements java.io.Serializable {

	private static final long serialVersionUID = -8551644727325766008L;
	private Integer sfzbh;//收费站编号
	private String sfzmc;//收费站名称
	private String sfzpjzm;//收费站票据站名
	private String ssgs;//所属公司
	private String xlmc;//线路名称
	private Integer sfzlxbh;//收费站类型编号
	@Transient
	private String sfzlxmc;//收费站类型名称
	private Integer jketccdsl ;//进口etc车道数量
	private Integer jkrgcdsl;//进口人工车道数量
	private String zxsxjkzh;//主线上行进口桩号
	private String zxxxjkzh;//主线下行进口桩号
	private Integer cketccdsl;//出口etc车道数量
	private Integer ckrgcdsl;//出口人工车道数量
	private String zxsxckzh;//主线上行出口桩号
	private String zxxxckzh;//主线下行出口桩号
	private String ckzx;//出口指向
	@Transient
	private String lxjc;//路线代码+路线简称
	public HFwSfz(){
		
	}

	public Integer getSfzbh() {
		return sfzbh;
	}

	public void setSfzbh(Integer sfzbh) {
		this.sfzbh = sfzbh;
	}

	public String getSfzmc() {
		return sfzmc;
	}

	public void setSfzmc(String sfzmc) {
		this.sfzmc = sfzmc;
	}

	public String getSfzpjzm() {
		return sfzpjzm;
	}

	public void setSfzpjzm(String sfzpjzm) {
		this.sfzpjzm = sfzpjzm;
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

	public Integer getSfzlxbh() {
		return sfzlxbh;
	}

	public void setSfzlxbh(Integer sfzlxbh) {
		this.sfzlxbh = sfzlxbh;
	}

	public String getSfzlxmc() {
		return sfzlxmc;
	}

	public void setSfzlxmc(String sfzlxmc) {
		this.sfzlxmc = sfzlxmc;
	}

	public Integer getJketccdsl() {
		return jketccdsl;
	}

	public void setJketccdsl(Integer jketccdsl) {
		this.jketccdsl = jketccdsl;
	}

	public Integer getJkrgcdsl() {
		return jkrgcdsl;
	}

	public void setJkrgcdsl(Integer jkrgcdsl) {
		this.jkrgcdsl = jkrgcdsl;
	}

	public String getZxsxjkzh() {
		return zxsxjkzh;
	}

	public void setZxsxjkzh(String zxsxjkzh) {
		this.zxsxjkzh = zxsxjkzh;
	}

	public String getZxxxjkzh() {
		return zxxxjkzh;
	}

	public void setZxxxjkzh(String zxxxjkzh) {
		this.zxxxjkzh = zxxxjkzh;
	}

	public Integer getCketccdsl() {
		return cketccdsl;
	}

	public void setCketccdsl(Integer cketccdsl) {
		this.cketccdsl = cketccdsl;
	}

	public Integer getCkrgcdsl() {
		return ckrgcdsl;
	}

	public void setCkrgcdsl(Integer ckrgcdsl) {
		this.ckrgcdsl = ckrgcdsl;
	}

	public String getZxsxckzh() {
		return zxsxckzh;
	}

	public void setZxsxckzh(String zxsxckzh) {
		this.zxsxckzh = zxsxckzh;
	}

	public String getZxxxckzh() {
		return zxxxckzh;
	}

	public void setZxxxckzh(String zxxxckzh) {
		this.zxxxckzh = zxxxckzh;
	}

	public String getCkzx() {
		return ckzx;
	}

	public void setCkzx(String ckzx) {
		this.ckzx = ckzx;
	}
	
	
	
	public String getLxjc() {
		return lxjc;
	}
	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}
	
	
	
	
	
	
	
	

}
