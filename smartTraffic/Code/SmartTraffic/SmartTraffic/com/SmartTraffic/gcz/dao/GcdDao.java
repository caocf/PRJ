package com.SmartTraffic.gcz.dao;

import java.util.List;

import com.SmartTraffic.multiple.model.HGcGcd;

public interface GcdDao {
	public List<HGcGcd> showGcdInfo() ;
	public Object showTrafficByGcdDay(String gcdid, String strdate);
	public Object showTrafficByGcdMonth(String gcdid,
			String monthstartdate, String monthenddate);
	public Object showTrafficByGcdYear(String gcdid,
			String yearstartdate, String yearenddate);
	public HGcGcd showGcdByPK(String gcdid);
	
}
