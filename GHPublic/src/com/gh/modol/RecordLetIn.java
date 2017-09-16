package com.gh.modol;

import java.io.Serializable;

public class RecordLetIn implements Serializable
{
	public String  shipnameString;
	private String id;
	private String start;
	public String startidString;
	private String target;
	public String targetidString;
	private String goods;	
	private String rank;
	public String rankidString;
	private String tons;
	private String unit;
	public String unitidString;
	private String berthtime;
	private String status;
	private String number;
	
	public String uptime;
	public String cheker;
	public String statusString;
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getnumber() {
		return number;
	}
	public void setnumber(String number) {
		this.number = number;
	}
	
	
	public String getstart() {
		return start;
	}
	public void setstart(String start) {
		this.start = start;
	}
	
	public String gettarget() {
		return target;
	}
	public void settarget(String target) {
		this.target = target;
	}
	
	public String getgoods() {
		return goods;
	}
	public void setgoods(String goods) {
		this.goods = goods;
	}
	

	public String getrank() {
		return rank;
	}
	public void setrank(String rank) {
		this.rank = rank;
	}
	
	public String gettons() {
		return tons;
	}
	public void settons(String tons) {
		this.tons = tons;
	}
	
	public String getunit() {
		return unit;
	}
	public void setunit(String unit) {
		this.unit = unit;
	}
	public String getberthtime() {
		return berthtime;
	}
	public void setberthtime(String berthtime) {
		this.berthtime = berthtime;
	}
	public String getstatus() {
		return status;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	

}
