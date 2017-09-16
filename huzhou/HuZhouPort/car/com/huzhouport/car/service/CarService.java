package com.huzhouport.car.service;

import java.util.List;

import com.huzhouport.car.dao.CarDao;
import com.huzhouport.car.model.CarApplication;

public class CarService 
{
	CarDao carDao;	

	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}
	
	public void applyCar(CarApplication ca)
	{
		carDao.applyCar(ca);
	}
	
	public List<?>  findMyCarAps(int userid,int status)
	{
		return carDao.findMyCarAps(userid,status);
	}
	
	public List<?>  findMyCarCheck1(int apid,int status)
	{
		return carDao.findMyCarCheck1(apid,status);
	}
	public List<?>  findMyCarCheck2(int officeid,int status)
	{
		return carDao.findMyCarCheck2(officeid,status);
	}
	public void commitCheck(CarApplication carApplication)
	{
		carDao.commitCheck(carApplication);
	}
	public CarApplication findCarApplicationByID(int id)
	{
		return carDao.findCarApplicationByID(id);
	}
	public List<?> findUnvaliableCars(String d1,String d2)
	{
		return carDao.findUnvaliableCars(d1, d2);
		
	}
	public List<?> findAllCars()
	{
		return carDao.findAllCars();
		
	}
	
	public int countAp(int userid)
	{
		return carDao.countAp(userid);
		
	}
	public int countMyCheck(int userid)
	{
		return carDao.countMyCheck(userid);
		
	}
	public int countOfficeCheck(int userid)
	{
		return carDao.countOfficeCheck(userid);
		
	}
}
