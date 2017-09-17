package com.highwaycenter.bean;

public class Lxgk implements java.io.Serializable {  //路线简称
	

	private static final long serialVersionUID = -7193845668666676950L;
	
	private String bzbm;     //标识编码
	private String lxdm;     //路线代码
	private String ldbh;     //路段编号
	private String gldwmc;   //管理单位名称
	private String lxjc;     //路线简称
	private String lxdfmc;   //路线地方名称
	private Float ldqdzh;    //路段起点桩号
	private Float ldzdzh;    //路段止点桩号
	private Float lc;        //里程
	private String ldqdmc;    //路段起点名称    
	private String ldzdmc;    //路段止点名称
	private String xzqh;      //行政区划
	private Integer xzqhdm;   //行政区划代码
	private String gydwmc;     //管养单位名称
	private String kgrq;      //开工日期
	private String jgrq;      //竣工日期
	private String ysrq;      //验收日期
	private String tcrq;      //通车日期
	private String xjnd;      //修建年度
	private String jsdj;     //技术等级
	private Integer sjcs;    //设计时速
	public Lxgk(){
		
	}
	
	public Lxgk(String bzbm, String lxdm, String ldbh, String gldwmc,
			String lxjc, String lxdfmc, Float ldqdzh, Float ldzdzh, Float lc,
			String ldqdmc, String ldzdmc, String xzqh, Integer xzqhdm,
			String gydwmc, String jsdj,Integer sjcs) {
		super();
		this.bzbm = bzbm;
		this.lxdm = lxdm;
		this.ldbh = ldbh;
		this.gldwmc = gldwmc;
		this.lxjc = lxjc;
		this.lxdfmc = lxdfmc;
		this.ldqdzh = ldqdzh;
		this.ldzdzh = ldzdzh;
		this.lc = lc;
		this.ldqdmc = ldqdmc;
		this.ldzdmc = ldzdmc;
		this.xzqh = xzqh;
		this.xzqhdm = xzqhdm;
		this.gydwmc = gydwmc;
		this.jsdj = jsdj;
		this.sjcs = sjcs;
	}
	public Lxgk(String bzbm, String lxdm, String ldbh, String gldwmc,
			String lxjc, String lxdfmc, Float ldqdzh, Float ldzdzh, Float lc,
			String ldqdmc, String ldzdmc, String xzqh, Integer xzqhdm,
			String gydwmc, String kgrq, String jgrq, String ysrq, String tcrq,
			String xjnd,String jsdj) {
		super();
		this.bzbm = bzbm;
		this.lxdm = lxdm;
		this.ldbh = ldbh;
		this.gldwmc = gldwmc;
		this.lxjc = lxjc;
		this.lxdfmc = lxdfmc;
		this.ldqdzh = ldqdzh;
		this.ldzdzh = ldzdzh;
		this.lc = lc;
		this.ldqdmc = ldqdmc;
		this.ldzdmc = ldzdmc;
		this.xzqh = xzqh;
		this.xzqhdm = xzqhdm;
		this.gydwmc = gydwmc;
		this.kgrq = kgrq;
		this.jgrq = jgrq;
		this.ysrq = ysrq;
		this.tcrq = tcrq;
		this.xjnd = xjnd;
		this.jsdj = jsdj;
	}
	public Lxgk(String bzbm, String lxdm, String ldbh, String gldwmc,
			String lxjc, String lxdfmc, Float ldqdzh, Float ldzdzh, Float lc,
			String ldqdmc, String ldzdmc, String xzqh, Integer xzqhdm,
			String gydwmc, String kgrq, String jgrq, String ysrq, String tcrq,
			String xjnd,String jsdj,Integer sjcs) {
		super();
		this.bzbm = bzbm;
		this.lxdm = lxdm;
		this.ldbh = ldbh;
		this.gldwmc = gldwmc;
		this.lxjc = lxjc;
		this.lxdfmc = lxdfmc;
		this.ldqdzh = ldqdzh;
		this.ldzdzh = ldzdzh;
		this.lc = lc;
		this.ldqdmc = ldqdmc;
		this.ldzdmc = ldzdmc;
		this.xzqh = xzqh;
		this.xzqhdm = xzqhdm;
		this.gydwmc = gydwmc;
		this.kgrq = kgrq;
		this.jgrq = jgrq;
		this.ysrq = ysrq;
		this.tcrq = tcrq;
		this.xjnd = xjnd;
		this.jsdj = jsdj;
		this.sjcs =sjcs;
	}
	public String getBzbm() {
		return bzbm;
	}
	public void setBzbm(String bzbm) {
		this.bzbm = bzbm;
	}
	public String getLxdm() {
		return lxdm;
	}
	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}
	public String getLdbh() {
		return ldbh;
	}
	public void setLdbh(String ldbh) {
		this.ldbh = ldbh;
	}
	public String getGldwmc() {
		return gldwmc;
	}
	public void setGldwmc(String gldwmc) {
		this.gldwmc = gldwmc;
	}
	public String getLxjc() {
		return lxjc;
	}
	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}
	public String getLxdfmc() {
		return lxdfmc;
	}
	public void setLxdfmc(String lxdfmc) {
		this.lxdfmc = lxdfmc;
	}
	public Float getLdqdzh() {
		return ldqdzh;
	}
	public void setLdqdzh(Float ldqdzh) {
		this.ldqdzh = ldqdzh;
	}
	public Float getLdzdzh() {
		return ldzdzh;
	}
	public void setLdzdzh(Float ldzdzh) {
		this.ldzdzh = ldzdzh;
	}
	public Float getLc() {
		return lc;
	}
	public void setLc(Float lc) {
		this.lc = lc;
	}
	public String getLdqdmc() {
		return ldqdmc;
	}
	public void setLdqdmc(String ldqdmc) {
		this.ldqdmc = ldqdmc;
	}
	public String getLdzdmc() {
		return ldzdmc;
	}
	public void setLdzdmc(String ldzdmc) {
		this.ldzdmc = ldzdmc;
	}
	public String getXzqh() {
		return xzqh;
	}
	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}
	public Integer getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(Integer xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getGydwmc() {
		return gydwmc;
	}
	public void setGydwmc(String gydwmc) {
		this.gydwmc = gydwmc;
	}
	public String getKgrq() {
		return kgrq;
	}
	public void setKgrq(String kgrq) {
		this.kgrq = kgrq;
	}
	public String getJgrq() {
		return jgrq;
	}
	public void setJgrq(String jgrq) {
		this.jgrq = jgrq;
	}
	public String getYsrq() {
		return ysrq;
	}
	public void setYsrq(String ysrq) {
		this.ysrq = ysrq;
	}
	public String getTcrq() {
		return tcrq;
	}
	public void setTcrq(String tcrq) {
		this.tcrq = tcrq;
	}
	public String getXjnd() {
		return xjnd;
	}
	public void setXjnd(String xjnd) {
		this.xjnd = xjnd;
	}

	public String getJsdj() {
		return jsdj;
	}

	public void setJsdj(String jsdj) {
		this.jsdj = jsdj;
	}

	public Integer getSjcs() {
		return sjcs;
	}

	public void setSjcs(Integer sjcs) {
		this.sjcs = sjcs;
	}
	
	

}
