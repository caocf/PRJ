package com.SmartTraffic.highway.service;

import com.common.action.result.BaseResult;


public interface HighWayTrafficService {
	
	public BaseResult selectTransDataTimeCS();

	/**
	 * 查询观测站列表信息
	 * @return
	 */
	public BaseResult selectGczInfo();
	
	/**
	 * 根据路程代码查询年月日统计数据
	 * @param lxdm
	 * @return
	 */
	//public BaseResult selectJdInfo(String lxdm,String lxjc);
	/**
	 * 查询流量统计的省国道高速
	 * @return
	 */
	public BaseResult selectJdlx();
	
	
	
	
	public BaseResult selectDayData(String lxdm);
	public BaseResult selectMonData(String lxdm);
	public BaseResult selectYearData(String lxdm);
}
