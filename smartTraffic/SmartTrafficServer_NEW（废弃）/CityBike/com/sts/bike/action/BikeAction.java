package com.sts.bike.action;

import java.util.List;

import com.sts.bike.model.Bike;
import com.sts.bike.model.Region;
import com.sts.bike.service.BikeService;

public class BikeAction 
{
	private int page;
	private int num;
	
	private int total;
	private BikeService bikeService;
	private List<Bike> bikes;
	private List<Region> regions;
	private String name;			//站点名称
	private int region;				//区域	1、全部；2、南湖区； 3、秀洲区； 4、嘉善；5、海盐；6、海宁；7、平湖；8、桐乡
	public static final String[] REGIONS=new String[]{"全部","南湖区","秀洲区","嘉善","海盐","海宁","平湖","桐乡"};	
	
	public String getRegionsName()
	{
		return region==0?REGIONS[0]:REGIONS[region-1];		
	}
	public List<Bike> getBikes() {
		return bikes;
	}
	public void setBikes(List<Bike> bikes) {
		this.bikes = bikes;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setBikeService(BikeService bikeService) {
		this.bikeService = bikeService;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}	
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	
	public String GetBikes()
	{
		bikes=bikeService.getBikes(page,num);
		total=(bikes==null?0:bikes.size());
		return "success";
	}
	
	public String SearchBikesByName()
	{
		if(!name.equals(""))
		{
			bikes=bikeService.SearchBikesByName(page,num,name,region);
		}
		total=(bikes==null?0:bikes.size());
		
		return "success";
	}
	
	public String SearchBikesByRegion()
	{
		if(region!=0)
		{
			bikes=bikeService.SearchBikesByRegion(region);
		}
		total=(bikes==null?0:bikes.size());
		
		return "success";
	}
	
	public String GetRegion()
	{

		regions=bikeService.GetRegion();

		total=(regions==null?0:regions.size());
		
		return "success";
	}
	
	public String GetCityBikeInfo()
	{
		return "success";
	}
}
