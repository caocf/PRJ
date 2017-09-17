package com.sts.smartbus.service;

import java.util.List;

import com.sts.smartbus.dao.SmartBusOracleDao;
import com.sts.smartbus.model.BusStation;

public class SmartBusOracleService 
{
	SmartBusOracleDao smartBusOracleDao;
	public void setSmartBusOracleDao(SmartBusOracleDao smartBusOracleDao) {
		this.smartBusOracleDao = smartBusOracleDao;
	}
	
	/**
	 * 通过站点id获取站点信息
	 * @param id 站点id
	 * @return 站点列表信息
	 */
	public List<BusStation> queryStationByStationID(int id)
	{
		return this.smartBusOracleDao.queryStationByStationID(id);
	}
	
	/**
	 * 站点模糊匹配
	 * @param name
	 * @param contaionBM
	 * @return
	 */
	public List<BusStation> queryStationByStationName(String name,boolean contaionBM,int limit)
	{
		return this.smartBusOracleDao.queryStationByStationName(name, contaionBM,limit);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<?> queryAllLines()
	{
		return this.smartBusOracleDao.queryAllLines();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<?> queryAllStations()
	{
		return this.smartBusOracleDao.queryAllStations();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<?> queryAllBikes()
	{
		return this.smartBusOracleDao.queryAllBikes();
	}
}
