package com.gh.modol;

import java.io.Serializable;

public class ShipCircleListModel  implements Serializable 
{
	private String id;
	private String title;
	private String content;
	private String price;
	private String tradetype;
	private String postime;
	private String sourceid;	
	
	public String typenameString;
	
	public String fromString,fromid;
	public String to,toid;
	public String tel;
	public String remark;
	
	public String goodstypeid;
	public String goodstypename;
	public String goodsname;
	public String tons;
	public String packaging;
	public String shiptypeid;
	public String shiptypename;
	public String shipname;
	public String load;
	public String route;
	public String age; 
	
	public String getsourceid() {
		return sourceid;
	}
	public void setsourceid(String sourceid) {
		this.sourceid = sourceid;
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
	
	public String getcontent() {
		return content;
	}
	public void setcontent(String content) {
		this.content = content;
	}
	

	public String getprice() {
		return price;
	}
	public void setprice(String price) {
		this.price = price;
	}
	
	public String gettradetype() {
		return tradetype;
	}
	public void settradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getpostime() {
		return postime;
	}
	public void setpostime(String postime) {
		this.postime = postime;
	}

}
