package com.gh.modol;

public class ShipBoughtModel {
	
//	  "id": 0,
//    "title": "油船出售",
//    "shiptype": "油船",
//    "shipname": "浙嘉兴油560",
//    "shipage": "4",
//    "tons": "10",
//    "price": "8900",
//    "linkman": "沈磊",
//    "tel": "137",
//    "remark": "备注",
//    "postdate": "2016-06-02 15:35:02"
//	
	private String id;
	private String title;
	private String shiptype;
	private String shipname;
	private String tons;
	private String rentprice;
	private String linkman;
	private String tel;
	private String remark;
	private String postdate;
	private String shipage;
	
	public String getshipage() {
		return shipage;
	}
	public void setshipage(String shipage) {
		this.shipage = shipage;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String gettitle() {
		return title;
	}
	public void settitle(String title) {
		this.title = title;
	}
	
	public String getshiptype() {
		return shiptype;
	}
	public void setshiptype(String shiptype) {
		this.shiptype = shiptype;
	}
	

	public String getshipname() {
		return shipname;
	}
	public void setshipname(String shipname) {
		this.shipname = shipname;
	}
	
	public String gettons() {
		return tons;
	}
	public void settons(String tons) {
		this.tons = tons;
	}
	
	public String getrentprice() {
		return rentprice;
	}
	public void setrentprice(String rentprice) {
		this.rentprice = rentprice;
	}
	
	public String getlinkman() {
		return linkman;
	}
	public void setlinkman(String linkman) {
		this.linkman = linkman;
	}
	public String gettel() {
		return tel;
	}
	public void settel(String tel) {
		this.tel = tel;
	}

	public String getremark() {
		return remark;
	}
	public void setremark(String remark) {
		this.remark = remark;
	}
	
	
	public String getpostdate() {
		return postdate;
	}
	public void setpostdate(String postdate) {
		this.postdate = postdate;
	}
	

}
