package com.zjport.supervise.model;

public class XQCB
{

	private String areacode;
	private String areaname;
	private long count;
	private long wx;
	private long jzx;

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getWx() {
		return wx;
	}

	public void setWx(long wx) {
		this.wx = wx;
	}

	public long getJzx() {
		return jzx;
	}

	public void setJzx(long jzx) {
		this.jzx = jzx;
	}

	public XQCB() {
	}

	public XQCB(String areacode, String areaname, long count, long wx, long jzx) {
		this.areacode = areacode;
		this.areaname = areaname;
		this.count = count;
		this.wx = wx;
		this.jzx = jzx;
	}
}
