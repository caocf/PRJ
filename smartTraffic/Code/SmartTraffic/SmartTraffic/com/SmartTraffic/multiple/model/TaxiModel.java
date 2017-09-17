package com.SmartTraffic.multiple.model;

public class TaxiModel {
	private int taxiId;  //出租车id
	private String xzqhdm;  //行政区划代码
	private String xzqhmc;  //行政区划名称
	private String cp;      //车牌
	private String cphm;    //车牌号码
	//private String cllx;    //车辆类型
	private String cllxmc;  //车辆类型名称
	private String cplxmc;  //车牌类型名称
	private String cpys;    //车牌颜色
	private String rllx;   //燃料类型
	private String yhmc;   //所属公司
	private String ysxzmc; //营运
	private String bz;//备注
	private double JD;//经度
	private double WD;//纬度
	private double SJ_JD;
	private double SJ_WD;
	
	public double getJD() {
		return JD;
	}
	public void setJD(double JD) {
		this.JD =  JD;
	}
	public double getWD() {
		return WD;
	}
	public void setWD(double  WD) {
		this.WD = WD;
	}
	public TaxiModel(){
		
	}
    public TaxiModel(int taxiId){
    	this.taxiId = taxiId;
	}
	public int getTaxiId() {
		return taxiId;
	}
	public void setTaxiId(int taxiId) {
		this.taxiId = taxiId;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getXzqhmc() {
		return xzqhmc;
	}
	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getCphm() {
		return cphm;
	}
	public void setCphm(String cphm) {
		this.cphm = cphm;
	}
	public String getCllxmc() {
		return cllxmc;
	}
	public void setCllxmc(String cllxmc) {
		this.cllxmc = cllxmc;
	}
	public String getCplxmc() {
		return cplxmc;
	}
	public void setCplxmc(String cplxmc) {
		this.cplxmc = cplxmc;
	}
	public String getCpys() {
		return cpys;
	}
	public void setCpys(String cpys) {
		this.cpys = cpys;
	}
	public String getRllx() {
		return rllx;
	}
	public void setRllx(String rllx) {
		this.rllx = rllx;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getYsxzmc() {
		return ysxzmc;
	}
	public void setYsxzmc(String ysxzmc) {
		this.ysxzmc = ysxzmc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public double getSJ_JD() {
		return SJ_JD;
	}
	public void setSJ_JD(double sJ_JD) {
		SJ_JD = sJ_JD;
	}
	public double getSJ_WD() {
		return SJ_WD;
	}
	public void setSJ_WD(double sJ_WD) {
		SJ_WD = sJ_WD;
	}
 
	
	
}
