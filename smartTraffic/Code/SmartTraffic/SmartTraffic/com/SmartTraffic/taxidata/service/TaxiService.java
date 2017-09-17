package com.SmartTraffic.taxidata.service;

import java.util.List;
import java.util.Map;

import com.common.action.result.BaseResult;

public interface TaxiService {
	/**
	 * 查询所有车辆信息
	 * @param date
	 * @return
	 */
	public BaseResult searchAllTaxis(String date);

	/**
	 * 查询车辆统计数据
	 * @param data
	 * @return
	 */
	public Map<String,Object> searchTaxiData(String data);

}
