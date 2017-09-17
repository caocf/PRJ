package com.highwaycenter.bean;

public class Qlgk  implements java.io.Serializable {

	private static final long serialVersionUID = 8116216576633355823L;    //桥梁概况
	
	private String bzbm;    //标识编码
	private String qldm;    //桥梁代码
	private String lxdm;    //路线代码
	private String lxjc;    //路线简称            可以做检索 like
	private String qlbh;    //桥梁编号
	private Integer xzqhdm;  //行政区划代码         
	private String xzqh;    //行政区划          可以做检索   列表
	private String qlmc;    //桥梁名称          可以做检索  like
	private Float qlzxzh;   //桥梁中心桩号
	private String gldwmc;  //管理单位名称      可以做检索  列表/like
	private String qlszdm;  //桥梁所在地名
	private String gydwmc;  //管养单位名称
	private String jgdwmc; //监管单位名称
	private Float qlqc;    //桥梁全长 
	private String xjnd;      //修建年度          可以做检索
	
	
	public Qlgk(){
		
	}
	
	
	public Qlgk(String bzbm, String qldm, String lxdm, String lxjc,
			String qlbh, Integer xzqhdm, String xzqh, String qlmc, Float qlzxzh,
			String gldwmc, String qlszdm, String gydwmc, String jgdwmc,
			Float qlqc, String xjnd) {
		super();
		this.bzbm = bzbm;
		this.qldm = qldm;
		this.lxdm = lxdm;
		this.lxjc = lxjc;
		this.qlbh = qlbh;
		this.xzqhdm = xzqhdm;
		this.xzqh = xzqh;
		this.qlmc = qlmc;
		this.qlzxzh = qlzxzh;
		this.gldwmc = gldwmc;
		this.qlszdm = qlszdm;
		this.gydwmc = gydwmc;
		this.jgdwmc = jgdwmc;
		this.qlqc = qlqc;
		this.xjnd = xjnd;
			
		
	}


	public String getBzbm() {
		return bzbm;
	}


	public void setBzbm(String bzbm) {
		this.bzbm = bzbm;
	}


	public String getQldm() {
		return qldm;
	}


	public void setQldm(String qldm) {
		this.qldm = qldm;
	}


	public String getLxdm() {
		return lxdm;
	}


	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}


	public String getLxjc() {
		return lxjc;
	}


	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}


	public String getQlbh() {
		return qlbh;
	}


	public void setQlbh(String qlbh) {
		this.qlbh = qlbh;
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


	public String getQlmc() {
		return qlmc;
	}


	public void setQlmc(String qlmc) {
		this.qlmc = qlmc;
	}


	public Float getQlzxzh() {
		return qlzxzh;
	}


	public void setQlzxzh(Float qlzxzh) {
		this.qlzxzh = qlzxzh;
	}


	public String getGldwmc() {
		return gldwmc;
	}


	public void setGldwmc(String gldwmc) {
		this.gldwmc = gldwmc;
	}


	public String getQlszdm() {
		return qlszdm;
	}


	public void setQlszdm(String qlszdm) {
		this.qlszdm = qlszdm;
	}


	public String getGydwmc() {
		return gydwmc;
	}


	public void setGydwmc(String gydwmc) {
		this.gydwmc = gydwmc;
	}


	public String getJgdwmc() {
		return jgdwmc;
	}


	public void setJgdwmc(String jgdwmc) {
		this.jgdwmc = jgdwmc;
	}


	public Float getQlqc() {
		return qlqc;
	}


	public void setQlqc(Float qlqc) {
		this.qlqc = qlqc;
	}


	public String getXjnd() {
		return xjnd;
	}


	public void setXjnd(String xjnd) {
		this.xjnd = xjnd;
	}
	

	

}
