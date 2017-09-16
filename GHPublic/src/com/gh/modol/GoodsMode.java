package com.gh.modol;

public class GoodsMode {
	
	
//		"id": 2,
//     "titile": "嘉兴拉货",
//     "type": "c",
//     "name": "沙子",
//     "tons": "5",
//     "packaging": "散装",
//     "startport": "a",
//     "unloadport": "b",
//     "price": "1000",
//     "linkman": "王三",
//     "tel": "139",
//     "remark": "备注",
//     "postdate": "2016-06-02 15:25:28"
	
	private String id;
	private String title;
	private String type;
	private String name;
	private String tons;
	
	private String packaging;
	private String startport;
	private String unloadport;
	
	
	private String price;
	private String linkman;
	private String tel;
	private String remark;
	private String postdate;
	public String getpackaging() {
		return packaging;
	}
	public void setpackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getstartport() {
		return startport;
	}
	public void setstartport(String startport) {
		this.startport = startport;
	}
	public String getunloadport() {
		return unloadport;
	}
	public void setunloadport(String unloadport) {
		this.unloadport = unloadport;
	}
	

	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type = type;
	}
	

	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
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
	
	public String gettons() {
		return tons;
	}
	public void settons(String tons) {
		this.tons = tons;
	}
	
	public String getprice() {
		return price;
	}
	public void setprice(String price) {
		this.price = price;
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
