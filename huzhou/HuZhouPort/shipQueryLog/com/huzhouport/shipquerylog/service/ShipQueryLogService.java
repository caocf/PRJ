package com.huzhouport.shipquerylog.service;

import java.util.List;

import com.huzhouport.shipquerylog.dao.ShipQueryLogDao;
import com.huzhouport.shipquerylog.model.ShipQueryLog;

public class ShipQueryLogService {

	ShipQueryLogDao shipQueryLogDao;
	public void setShipQueryLogDao(ShipQueryLogDao shipQueryLogDao) {
		this.shipQueryLogDao = shipQueryLogDao;
	}
	
	public void saveShipQueryLog(ShipQueryLog log)
	{
		this.shipQueryLogDao.saveShipQueryLog(log);
	}
	
	public List<ShipQueryLog> queryShipQueryLogs(String start,String end,int page,int num)
	{
		return this.shipQueryLogDao.queryShipQueryLogs(start, end, page, num);
	}
	
	public int queryShipQueryLogsNum(String start,String end)
	{
		return this.shipQueryLogDao.queryShipQueryLogsNum(start, end);
	}
	
}
