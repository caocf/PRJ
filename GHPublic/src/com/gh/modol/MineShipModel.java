package com.gh.modol;

import java.util.List;

public class MineShipModel {
	
	
//    "shipid": 3,
//    "shipname": "浙安吉货1931",
//    "regnumber": "16",
//    "firstregnum": null,
//    "ssrn": null,
//    "englishname": "ppp",
//    "homeport": "i",
//    "shiptype": "i",
//    "manufacturer": "i",
//    "builtdate": "i",
//    "imo": null
	
	
//    "englishname：审核状态   	（0：审核中 1：审核未通过  1：已通过）
	
	
	private String shipid;
	private String shipname;
	private String regnumber;
	private String firstregnum;
	private String ssrn;
	
	private String englishname;
	private String homeport;
	private String shiptype;
	
	private String manufacturer;
	private String builtdate;
	private String imo;
	public Status status=new Status();
	
	private MyShipItemStateModel mMyShipItemStateModels;
	
	
	public  class Status
	{
		public int id;
		public String statusnameString;
	}

	
	public MyShipItemStateModel getmMyShipItemStateModels() {
		return mMyShipItemStateModels;
	}
	public void setmMyShipItemStateModels(
			MyShipItemStateModel mMyShipItemStateModels) {
		this.mMyShipItemStateModels = mMyShipItemStateModels;
	}
	
	
	public String getshipid() {
		return shipid;
	}
	public void setshipid(String shipid) {
		this.shipid = shipid;
	}
	public String getshipname() {
		return shipname;
	}
	public void setshipname(String shipname) {
		this.shipname = shipname;
	}
	public String getregnumber() {
		return regnumber;
	}
	public void setregnumber(String regnumber) {
		this.regnumber = regnumber;
	}
	

	public String getfirstregnum() {
		return firstregnum;
	}
	public void setfirstregnum(String firstregnum) {
		this.firstregnum = firstregnum;
	}
	

	public String getssrn() {
		return ssrn;
	}
	public void setssrn(String ssrn) {
		this.ssrn = ssrn;
	}
	
	
	
	public String getenglishname() {
		return englishname;
	}
	public void setenglishname(String englishname) {
		this.englishname = englishname;
	}
	
	public String gethomeport() {
		return homeport;
	}
	public void sethomeport(String homeport) {
		this.homeport = homeport;
	}
	
	public String getshiptype() {
		return shiptype;
	}
	public void setshiptype(String shiptype) {
		this.shiptype = shiptype;
	}
	
	public String getmanufacturer() {
		return manufacturer;
	}
	public void setmanufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getbuiltdate() {
		return builtdate;
	}
	public void setbuiltdate(String builtdate) {
		this.builtdate = builtdate;
	}
	public String getimo() {
		return imo;
	}
	public void setimo(String imo) {
		this.imo = imo;
	}
	

}
