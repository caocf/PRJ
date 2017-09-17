package com.SmartTraffic.multiple.service.impl;

import java.util.Date;
import java.util.List;

import com.common.action.result.BaseResult;

public interface MultipleService {
	public BaseResult selectTaxiInfo();
	public BaseResult selectShipInfo();
	public BaseResult selectTaxiData();
	public BaseResult selectJdInfo();
	public BaseResult selectNumById(int jtlId);
	
	public BaseResult showGcdInfo();

	public BaseResult showTrafficByGcd(String gcdid);

	public BaseResult showTrafficByGcdDate(String gcdid,
			Date startdate, Date enddate);
}
