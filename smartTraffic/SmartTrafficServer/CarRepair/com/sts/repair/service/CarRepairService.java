package com.sts.repair.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sts.repair.dao.CarRepairDao;
import com.sts.repair.model.CarRepair;

public class CarRepairService 
{
	
	CarRepairDao carRepairDao;
	public void setCarRepairDao(CarRepairDao carRepairDao) {
		this.carRepairDao = carRepairDao;
	}
	
	public List<CarRepair> queryRepairFromDB(int start,int num)
	{
		return this.carRepairDao.queryRepairDefault(start, num);
	}
	
	public List<CarRepair> queryRepairByNameFromDB(String name,int start,int num)
	{
		return this.carRepairDao.queryRepairByName(name, start, num);
	}
	
	
	//以下为模拟数据
	
	public List<CarRepair> GetCarRepairPoint(double lantitude,double longtitude,int radius)
	{
		List<CarRepair> result=simulate();
			
		return result;
	}
	
	public List<CarRepair> GetCarRepairByName(String name)
	{
		List<CarRepair> result=simulate(name);
			
		return result;
	}
	
	public List<CarRepair> simulate()
	{
		return simulate("车之家汽车服务中心");
	}
	
	public List<CarRepair> simulate(String name)
	{
		List<CarRepair> result=new ArrayList<CarRepair>();
		double long1=120.726627;
		double lan1=30.794475;
		
		double long2=120.813008;
		double lan2=30.744207;
		
		String address="纺公路1127号33弄";
		String phone="1234567890";
		
		Random random=new Random();
		
		for(int i=0;i<10;i++)
		{
			CarRepair temp=new CarRepair();
			
			temp.setName(name+i);
			temp.setAddress(address+i);
			temp.setDistance(random.nextInt(10000));
			temp.setLantitude((int)(((lan1-lan2)/10*i+lan2)*1000000)/1000000.0);
			temp.setLongtitude((int)(((long2-long1)/10*i+long1)*1000000)/1000000.0);
			temp.setLevel(random.nextInt(5));
			temp.setPhone(phone+i);
			
			result.add(temp);	
		}
		
		return result;
	}
}
