package com.SmartTraffic.taxidata.dao;

import java.util.List;

public interface TaxiDao {
	public List<?> searchAllTaxis(String date);
	
	public long searchAllTaxisCount();
	
	public long searchActiveCount(String date);
	
	public float searchAvrSpeed(String date);
}
