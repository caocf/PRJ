package com.gh.modol;
/*
 * 码头实体
 */
public class DockModel {

//	  {
//          "wharfid": 1,
//          "wharfname": "乌镇码头",
//          "wharfnum": "1154879",
//          "operations": "36",
//          "city": "嘉兴",
//          "area": "桐乡"
//      }

	private String wharfname;
	private String wharfnum;
	
	public String getwharfname() {
		return wharfname;
	}
	public void setwharfname(String wharfname) {
		this.wharfname = wharfname;
	}
	public String getwharfnum() {
		return wharfnum;
	}
	public void setwharfnum(String wharfnum) {
		this.wharfnum = wharfnum;
	}
}
