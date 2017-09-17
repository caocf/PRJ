package com.sts.bike.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sts.bike.action.BikeAction;
import com.sts.bike.model.Bike;
import com.sts.bike.model.Region;

public class BikeService 
{
	public List<Bike> getBikes(int page,int num)
	{	
		return simulateBike(30);
	}
	
	public List<Bike> SearchBikesByName(int page,int num,String name,int region)
	{
		return simulateBike(10,name);
	}
	
	public List<Bike> SearchBikesByRegion(int region)
	{
		return simulateBike(20);
	}
	
	public List<Region> GetRegion()
	{
		List<Region> result=new ArrayList<Region>();
		Region region=new Region();
		region.setRegionID(2);
		region.setRegionName(BikeAction.REGIONS[2]);
		List<String> strings=new ArrayList<String>();
		strings.add("人民公园");
		strings.add("少儿公园");
		region.setHotPoint(strings);
		result.add(region);
		
		return result;
	}
	
	public List<Bike> simulateBike(int num)
	{
		 return simulateBike(num, "地址");
	}
	
	public List<Bike> simulateBike(int num,String name)
	{
		List<Bike> result=new ArrayList<Bike>();
		
		double long1=120.726627;
		double lan1=30.794475;
		
		double long2=120.813008;
		double lan2=30.744207;
		
		int rand1=(int)(lan1*1e6)-(int)(lan2*1e6);
		int rand2=(int)(long2*1e6)-(int)(long1*1e6);
		
		for(int i=0;i<num;i++)
		{
			Bike temp=new Bike();
			temp.setAddress(name+i);
			temp.setBikeStation("站点"+i);
			
			int j=new Random().nextInt(50);
			temp.setTotal(j);
			int k=new Random().nextInt(j);
			temp.setBorrowed(k);
			temp.setLeft(j-k);
			
			temp.setId(i);
			temp.setUpdateTime(new Date());
			
			temp.setLantitude(((int)(lan2*1e6)+(new Random().nextInt(rand1)))/1000000.0);
			temp.setLongtitude(((int)(long1*1e6)+(new Random().nextInt(rand2)))/1000000.0);
			
			result.add(temp);
		}
		
		return result;
	}
}
