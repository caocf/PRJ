package com.sts.trafficinfo.service;

import java.util.List;

import com.sts.trafficinfo.dao.TrafficInfoDao;
import com.sts.trafficinfo.model.ICcard;
import com.sts.trafficinfo.model.TaxiCompany;
import com.sts.trafficinfo.model.TicketHotLine;
import com.sts.trafficinfo.model.TicketsAgent;
import com.sts.trafficinfo.model.TrafficPoint;


public class TrafficInfoService {
	
	/*----------------------dao类-------------------------------*/
	
	private TrafficInfoDao trafficInfoDao;
	
	public void setTrafficInfoDao(TrafficInfoDao trafficInfoDao) {
		this.trafficInfoDao = trafficInfoDao;
	}

	
	/*--------------------------service 内容----------------------------*/
	
	
	/**
	 * 
	 * @param zone
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryICcards(int zone,int page,int num)
	{
		return trafficInfoDao.queryICcards(zone, page, num);
	}
	
	/**
	 * 
	 * @param type
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTrafficPoints(int type,int page,int num)
	{
		return trafficInfoDao.queryTrafficPoints(type, page, num);
	}
	
	/**
	 * 
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTaxiCompanies(int page,int num)
	{
		return trafficInfoDao.queryTaxiCompanies(page, num);
	}
	
	/**
	 * 
	 * @param zone
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTicketsAgents(int zone,int page,int num)
	{
		return trafficInfoDao.queryTicketsAgents(zone, page, num);
	}
	
	/**
	 * 
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTicketHotLines(int page,int num)
	{
		return trafficInfoDao.queryTicketHotLines(page, num);
	}
}
