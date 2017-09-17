package com.sts.taxi.service;

import java.util.List;

import com.sts.taxi.dao.TaxiDao;
import com.sts.taxi.model.Taxi;
import com.sts.taxi.model.TaxiOrder;
import com.sts.taxi.model.TaxiPhone;

public class TaxiService 
{
	TaxiDao taxiDao;
	public void setTaxiDao(TaxiDao taxiDao) {
		this.taxiDao = taxiDao;
	}
	
	public boolean save(TaxiOrder t)
	{
		this.taxiDao.save(t);
		return true;
	}
	
	public List<TaxiOrder> queryById(int userid)
	{
		return this.taxiDao.queryByID(userid);
	}
	
	
	public List<?> searchByLocationCircle(int radius,double lan,double lon,int num)
	{
		return this.taxiDao.searchByLocationCircle(radius, lan, lon, num);
	}
	
	public List<?> searchAllTaxis(int page,int num,String date)
	{
		return this.taxiDao.searchAllTaxis(page,num,date);
	}
	
	public List<Taxi> searchTaxiDetailByName(String name)
	{
		return this.taxiDao.searchTaxiDetailByName(name);
	}
	
	public List<TaxiPhone> queryTaxiPhoneById(String name)
	{
		return taxiDao.queryTaxiPhoneById(name);
	}
}
