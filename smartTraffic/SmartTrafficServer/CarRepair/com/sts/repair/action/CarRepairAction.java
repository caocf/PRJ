package com.sts.repair.action;

import java.util.List;

import com.sts.repair.model.CarRepair;
import com.sts.repair.service.CarRepairService;

public class CarRepairAction 
{
	private double lantitude;
	private double longtitude;
	private int radius;
	private List<CarRepair> carRepairs;
	
	private String name;
	
	private int page;
	private int num;
	
	private CarRepairService carRepairService;
		
	public void setNum(int num) {
		this.num = num;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setCarRepairService(CarRepairService carRepairService) {
		this.carRepairService = carRepairService;
	}
	public double getLantitude() {
		return lantitude;
	}
	public void setLantitude(double lantitude) {
		this.lantitude = lantitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}

	public List<CarRepair> getCarRepairs() {
		return carRepairs;
	}
	public void setCarRepairs(List<CarRepair> carRepairs) {
		this.carRepairs = carRepairs;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	//经纬度获取
	public String GetCarRepairPoint()
	{
		carRepairs=carRepairService.GetCarRepairPoint(lantitude,longtitude,radius);
		
		return "success";
	}
	
	//名称获取
	public String GetCarRepairByName()
	{
		if(page>0&num>0)
			carRepairs=this.carRepairService.queryRepairByNameFromDB(name, (page-1)*num, num);
		else
			carRepairs=this.carRepairService.queryRepairByNameFromDB(name, 0,10);
		return "success";
	}
	
	//默认获取
	public String GetCarRepair()
	{
		if(page>0&&num>0)
			carRepairs=this.carRepairService.queryRepairFromDB((page-1)*num, num);
		else
			carRepairs=this.carRepairService.queryRepairFromDB(0, 10);
		return "success";
	}
}
