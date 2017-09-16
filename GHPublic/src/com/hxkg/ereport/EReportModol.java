package com.hxkg.ereport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EReportModol   implements Serializable
{
	public String shipname;
	public String goods;
	public String from;
	public String to;
	public String reportid;
	public String inout;
	public String time;
	public String goodscount;
	public String gascount;
	public String gastime;
	public String unit; 
	public String arr;
	public String id;
	
	public List<Map<String, Object>> list=new ArrayList<>(); 
}
