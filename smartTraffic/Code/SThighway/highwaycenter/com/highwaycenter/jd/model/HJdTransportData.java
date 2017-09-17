package com.highwaycenter.jd.model;

import java.util.Date;
;

public class HJdTransportData implements java.io.Serializable {

	private static final long serialVersionUID = 6531505262860506287L;
	
	private Integer tddm;// 交通量代码
	private String lxdm;//路线代码
	private String lxmc;//路线名称
	private Double gclc;//观测里程（公里）
	private Integer jdcdlshj; //机动车当量数合计
	private Integer jdczrshj; // 机动车自然数合计
	private Integer qcdlshj;//汽车当量数合计
	private Integer qczrshj;//汽车自然数合计
	private Integer xxhc; //小型货车
	private Integer zxhc; //中型货车
	private Integer dxhc; //大型货车
	private Integer tdxhc;//特大型货车
	private Integer jzxc; //集装箱车
	private Integer zxkc; //中小客车
	private Integer dkc;//大客车
	private Integer mtc;//摩托车
	private Integer tlj ; //拖拉机
    private Integer rlc;//人力车
    private Integer clc;//畜力车
    private Integer zxc;//自行车
    private Integer xsl;//行驶量（万车公里/日）
    private Integer syjtl;//适应交通量（辆/日）
	private Double jtyjd;//交通拥挤度
	private String tjsj;//统计时间
	
	
	
	public Integer getTddm() {
		return tddm;
	}
	public void setTddm(Integer tddm) {
		this.tddm = tddm;
	}
	public String getLxdm() {
		return lxdm;
	}
	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}
	public String getLxmc() {
		return lxmc;
	}
	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	public Double getGclc() {
		return gclc;
	}
	public void setGclc(Double gclc) {
		this.gclc = gclc;
	}
	public Integer getJdcdlshj() {
		return jdcdlshj;
	}
	public void setJdcdlshj(Integer jdcdlshj) {
		this.jdcdlshj = jdcdlshj;
	}
	public Integer getJdczrshj() {
		return jdczrshj;
	}
	public void setJdczrshj(Integer jdczrshj) {
		this.jdczrshj = jdczrshj;
	}
	public Integer getQcdlshj() {
		return qcdlshj;
	}
	public void setQcdlshj(Integer qcdlshj) {
		this.qcdlshj = qcdlshj;
	}
	public Integer getQczrshj() {
		return qczrshj;
	}
	public void setQczrshj(Integer qczrshj) {
		this.qczrshj = qczrshj;
	}
	public Integer getXxhc() {
		return xxhc;
	}
	public void setXxhc(Integer xxhc) {
		this.xxhc = xxhc;
	}
	public Integer getZxhc() {
		return zxhc;
	}
	public void setZxhc(Integer zxhc) {
		this.zxhc = zxhc;
	}
	public Integer getDxhc() {
		return dxhc;
	}
	public void setDxhc(Integer dxhc) {
		this.dxhc = dxhc;
	}
	public Integer getTdxhc() {
		return tdxhc;
	}
	public void setTdxhc(Integer tdxhc) {
		this.tdxhc = tdxhc;
	}
	public Integer getJzxc() {
		return jzxc;
	}
	public void setJzxc(Integer jzxc) {
		this.jzxc = jzxc;
	}
	public Integer getZxkc() {
		return zxkc;
	}
	public void setZxkc(Integer zxkc) {
		this.zxkc = zxkc;
	}
	public Integer getDkc() {
		return dkc;
	}
	public void setDkc(Integer dkc) {
		this.dkc = dkc;
	}
	public Integer getMtc() {
		return mtc;
	}
	public void setMtc(Integer mtc) {
		this.mtc = mtc;
	}
	public Integer getTlj() {
		return tlj;
	}
	public void setTlj(Integer tlj) {
		this.tlj = tlj;
	}
	public Integer getRlc() {
		return rlc;
	}
	public void setRlc(Integer rlc) {
		this.rlc = rlc;
	}
	public Integer getClc() {
		return clc;
	}
	public void setClc(Integer clc) {
		this.clc = clc;
	}
	public Integer getZxc() {
		return zxc;
	}
	public void setZxc(Integer zxc) {
		this.zxc = zxc;
	}
	public Integer getXsl() {
		return xsl;
	}
	public void setXsl(Integer xsl) {
		this.xsl = xsl;
	}
	public Integer getSyjtl() {
		return syjtl;
	}
	public void setSyjtl(Integer syjtl) {
		this.syjtl = syjtl;
	}
	public Double getJtyjd() {
		return jtyjd;
	}
	public void setJtyjd(Double jtyjd) {
		this.jtyjd = jtyjd;
	}
	public String getTjsj() {
		return tjsj;
	}
	public void setTjsj(String tjsj) {
		this.tjsj = tjsj;
	}
	
	

}
