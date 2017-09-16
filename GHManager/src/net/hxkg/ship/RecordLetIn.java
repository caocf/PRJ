package net.hxkg.ship;

import java.io.Serializable;



public class RecordLetIn implements Serializable
{
	
//	 "id": 6,
//     "start": "杭州",
//     "berthtime": "2016-6-20",
//     "status": "待审批",
//     "target": "上海",
//     "goods": "散货",
//     "rank": "爆炸品",
//     "tons": "100",
//     "unit": "吨",
//     "number": "12423000"
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String start;
	private String id;
	private String shipname;
	private String target;
	private String goods;	
	private String rank;
	private String tons;
	private String unit;
	private String berthtime;
	private String status;
	private String number;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShipname() {
		return shipname;
	}
	public void setShipname(String shipname) {
		this.shipname = shipname;
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
