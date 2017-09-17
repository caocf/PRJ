package com.sts.taxi.service;

import java.util.List;

import com.sts.taxi.dao.TaxiDao;
import com.sts.taxi.model.TaxiOrder;

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
}
