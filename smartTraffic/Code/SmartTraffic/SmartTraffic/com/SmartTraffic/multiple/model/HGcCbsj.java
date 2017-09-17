package com.SmartTraffic.multiple.model;

import java.util.Date;

public class HGcCbsj {
	private String cbsjid;
	private String cbid;//船舶id
	private String cmch;//名称
	private String aissbh;//ais
	private double cbcd;//长度
	private double cbkd;//宽度
	private double cbgd;//出水高度
	private int sfkzz;// 0空 1重载
	private int sfsxx;// 0下1上行
	private int sfcd;// 0单1船
	private int cbsl;//数量
	private int cbsd;//速度
	private double cbdw;//计算吨位
	private double cbdwtj;//统计吨位
	private String hz;//货种
	private String ejhz;//二级货种
	private double rgcbcd;//人工长度
	private double rgcbkd;//宽度
	private double rgcbgd;//高度
	private int rgsfkzz;// 人工 0空1重
	private int rgsfsxx;// 人工  1上0下
	private double rgcbsl;//人工数量
	private double rgcbdw;//人工吨位
	private String rghz;
	private String rgejhz;
	private int sfcz;// 0不1超
	private int sfcx;// 0不1超
	private int rgsfpc;// 0有1无
	private Date tgsj;//通过时间
	private String gcdid;//观测点id
	private String sfzs;// 1是0否
	private String zhsbr;
	private Date zhsbsj;

	public String getCbsjid() {
		return cbsjid;
	}

	public void setCbsjid(String cbsjid) {
		this.cbsjid = cbsjid;
	}

	public String getCbid() {
		return cbid;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public String getCmch() {
		return cmch;
	}

	public void setCmch(String cmch) {
		this.cmch = cmch;
	}

	public String getAissbh() {
		return aissbh;
	}

	public void setAissbh(String aissbh) {
		this.aissbh = aissbh;
	}

	public double getCbcd() {
		return cbcd;
	}

	public void setCbcd(double cbcd) {
		this.cbcd = cbcd;
	}

	public double getCbkd() {
		return cbkd;
	}

	public void setCbkd(double cbkd) {
		this.cbkd = cbkd;
	}

	public double getCbgd() {
		return cbgd;
	}

	public void setCbgd(double cbgd) {
		this.cbgd = cbgd;
	}

	public int getSfkzz() {
		return sfkzz;
	}

	public void setSfkzz(int sfkzz) {
		this.sfkzz = sfkzz;
	}

	public int getSfsxx() {
		return sfsxx;
	}

	public void setSfsxx(int sfsxx) {
		this.sfsxx = sfsxx;
	}

	public int getSfcd() {
		return sfcd;
	}

	public void setSfcd(int sfcd) {
		this.sfcd = sfcd;
	}

	public int getCbsl() {
		return cbsl;
	}

	public void setCbsl(int cbsl) {
		this.cbsl = cbsl;
	}

	public int getCbsd() {
		return cbsd;
	}

	public void setCbsd(int cbsd) {
		this.cbsd = cbsd;
	}

	public double getCbdw() {
		return cbdw;
	}

	public void setCbdw(double cbdw) {
		this.cbdw = cbdw;
	}

	public double getCbdwtj() {
		return cbdwtj;
	}

	public void setCbdwtj(double cbdwtj) {
		this.cbdwtj = cbdwtj;
	}

	public String getHz() {
		return hz;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}

	public String getEjhz() {
		return ejhz;
	}

	public void setEjhz(String ejhz) {
		this.ejhz = ejhz;
	}

	public double getRgcbcd() {
		return rgcbcd;
	}

	public void setRgcbcd(double rgcbcd) {
		this.rgcbcd = rgcbcd;
	}

	public double getRgcbkd() {
		return rgcbkd;
	}

	public void setRgcbkd(double rgcbkd) {
		this.rgcbkd = rgcbkd;
	}

	public double getRgcbgd() {
		return rgcbgd;
	}

	public void setRgcbgd(double rgcbgd) {
		this.rgcbgd = rgcbgd;
	}

	public int getRgsfkzz() {
		return rgsfkzz;
	}

	public void setRgsfkzz(int rgsfkzz) {
		this.rgsfkzz = rgsfkzz;
	}

	public int getRgsfsxx() {
		return rgsfsxx;
	}

	public void setRgsfsxx(int rgsfsxx) {
		this.rgsfsxx = rgsfsxx;
	}

	public double getRgcbsl() {
		return rgcbsl;
	}

	public void setRgcbsl(double rgcbsl) {
		this.rgcbsl = rgcbsl;
	}

	public double getRgcbdw() {
		return rgcbdw;
	}

	public void setRgcbdw(double rgcbdw) {
		this.rgcbdw = rgcbdw;
	}

	public String getRghz() {
		return rghz;
	}

	public void setRghz(String rghz) {
		this.rghz = rghz;
	}

	public String getRgejhz() {
		return rgejhz;
	}

	public void setRgejhz(String rgejhz) {
		this.rgejhz = rgejhz;
	}

	public int getSfcz() {
		return sfcz;
	}

	public void setSfcz(int sfcz) {
		this.sfcz = sfcz;
	}

	public int getSfcx() {
		return sfcx;
	}

	public void setSfcx(int sfcx) {
		this.sfcx = sfcx;
	}

	public int getRgsfpc() {
		return rgsfpc;
	}

	public void setRgsfpc(int rgsfpc) {
		this.rgsfpc = rgsfpc;
	}

	public Date getTgsj() {
		return tgsj;
	}

	public void setTgsj(Date tgsj) {
		this.tgsj = tgsj;
	}

	public String getGcdid() {
		return gcdid;
	}

	public void setGcdid(String gcdid) {
		this.gcdid = gcdid;
	}

	public String getSfzs() {
		return sfzs;
	}

	public void setSfzs(String sfzs) {
		this.sfzs = sfzs;
	}

	public String getZhsbr() {
		return zhsbr;
	}

	public void setZhsbr(String zhsbr) {
		this.zhsbr = zhsbr;
	}

	public Date getZhsbsj() {
		return zhsbsj;
	}

	public void setZhsbsj(Date zhsbsj) {
		this.zhsbsj = zhsbsj;
	}

}
