package com.highwaycenter.bzbx.model;

import javax.persistence.Transient;

public class HBzbxBzbx implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer bzbxbh;  //标志标线编号
	private Integer xzqhdm;  //行政区划代码
	private String xlmc;      //线路名称
	private Integer bzbxlxbh;  //标志标线类型编号
	private String gldj;      //公路等级
	private String bzwzl;     //标志位置(左)
	private String bzwzm;     //'标志位置(中)',
	private String bzwzr;     //'标志位置(右)',
	private String bmnrta;    //版面内容图案
	private String bmnrsl;    //版面内容数量
	private String bmcc;      //版面尺寸
	private String zcxsjgg;   //支撑形式及规格
	private String azsj;    //安装时间
	private String zzazdw;    //制作安装单位
	private String gldw;      //管理单位
	private String bz;   //备注
	@Transient
	private String bzbxlxmc;  //类型名称
	@Transient
	private String bzbxpath;  //图案路径
	@Transient
	private String xzqhmc;//行政区划名称
	@Transient
	private String lxjc;//路线代码+路线简称
	public HBzbxBzbx(){
		
	}
	public HBzbxBzbx(Integer bzbxbh, Integer xzqhdm, String xlmc,
			Integer bzbxlxbh, String gldj, String bzwzl, String bzwzm,
			String bzwzr, String bmnrta, String bmnrsl, String bmcc,
			String zcxsjgg, String azsj, String zzazdw, String gldw, String bz) {
		super();
		this.bzbxbh = bzbxbh;
		this.xzqhdm = xzqhdm;
		this.xlmc = xlmc;
		this.bzbxlxbh = bzbxlxbh;
		this.gldj = gldj;
		this.bzwzl = bzwzl;
		this.bzwzm = bzwzm;
		this.bzwzr = bzwzr;
		this.bmnrta = bmnrta;
		this.bmnrsl = bmnrsl;
		this.bmcc = bmcc;
		this.zcxsjgg = zcxsjgg;
		this.azsj = azsj;
		this.zzazdw = zzazdw;
		this.gldw = gldw;
		this.bz = bz;
	}
	public HBzbxBzbx(Integer bzbxbh, Integer xzqhdm, String xlmc,
			Integer bzbxlxbh, String gldj, String bzwzl, String bzwzm,
			String bzwzr, String bmnrta, String bmnrsl, String bmcc,
			String zcxsjgg, String azsj, String zzazdw, String gldw, String bz,String bzbxlxmc) {
		super();
		this.bzbxbh = bzbxbh;
		this.xzqhdm = xzqhdm;
		this.xlmc = xlmc;
		this.bzbxlxbh = bzbxlxbh;
		this.gldj = gldj;
		this.bzwzl = bzwzl;
		this.bzwzm = bzwzm;
		this.bzwzr = bzwzr;
		this.bmnrta = bmnrta;
		this.bmnrsl = bmnrsl;
		this.bmcc = bmcc;
		this.zcxsjgg = zcxsjgg;
		this.azsj = azsj;
		this.zzazdw = zzazdw;
		this.gldw = gldw;
		this.bz = bz;
		this.bzbxlxmc = bzbxlxmc;
	}
	
	public Integer getBzbxbh() {
		return bzbxbh;
	}
	public void setBzbxbh(Integer bzbxbh) {
		this.bzbxbh = bzbxbh;
	}
	public Integer getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(Integer xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getXlmc() {
		return xlmc;
	}
	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}
	public Integer getBzbxlxbh() {
		return bzbxlxbh;
	}
	public void setBzbxlxbh(Integer bzbxlxbh) {
		this.bzbxlxbh = bzbxlxbh;
	}
	public String getGldj() {
		return gldj;
	}
	public void setGldj(String gldj) {
		this.gldj = gldj;
	}
	public String getBzwzl() {
		return bzwzl;
	}
	public void setBzwzl(String bzwzl) {
		this.bzwzl = bzwzl;
	}
	public String getBzwzm() {
		return bzwzm;
	}
	public void setBzwzm(String bzwzm) {
		this.bzwzm = bzwzm;
	}
	public String getBzwzr() {
		return bzwzr;
	}
	public void setBzwzr(String bzwzr) {
		this.bzwzr = bzwzr;
	}
	public String getBmnrta() {
		return bmnrta;
	}
	public void setBmnrta(String bmnrta) {
		this.bmnrta = bmnrta;
	}
	public String getBmnrsl() {
		return bmnrsl;
	}
	public void setBmnrsl(String bmnrsl) {
		this.bmnrsl = bmnrsl;
	}
	public String getBmcc() {
		return bmcc;
	}
	public void setBmcc(String bmcc) {
		this.bmcc = bmcc;
	}
	public String getZcxsjgg() {
		return zcxsjgg;
	}
	public void setZcxsjgg(String zcxsjgg) {
		this.zcxsjgg = zcxsjgg;
	}
	public String getAzsj() {
		return azsj;
	}
	public void setAzsj(String azsj) {
		this.azsj = azsj;
	}
	public String getZzazdw() {
		return zzazdw;
	}
	public void setZzazdw(String zzazdw) {
		this.zzazdw = zzazdw;
	}
	public String getGldw() {
		return gldw;
	}
	public void setGldw(String gldw) {
		this.gldw = gldw;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getBzbxlxmc() {
		return bzbxlxmc;
	}
	public void setBzbxlxmc(String bzbxlxmc) {
		this.bzbxlxmc = bzbxlxmc;
	}
	public String getBzbxpath() {
		return bzbxpath;
	}
	public void setBzbxpath(String bzbxpath) {
		this.bzbxpath = bzbxpath;
	}

	public String getXzqhmc() {
		return xzqhmc;
	}
	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}
	public String getLxjc() {
		return lxjc;
	}
	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}
	
	

}
