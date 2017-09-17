package com.sts.school.action;

import java.util.List;

import com.sts.school.model.DrirvingSchooleNews;
import com.sts.school.model.DrivingSchool;
import com.sts.school.model.Region;
import com.sts.school.service.DrivingSchoolService;

public class DrivingSchoolAction 
{
	private double lantitude;
	private double longtitude;
	private int radius;
	private List<DrivingSchool> list;
	
	private int sort;				//0:距离；1：等级;  默认0；
	private DrivingSchoolService drivingSchoolService;
	
	private String schoolName;
		
	public void setDrivingSchoolService(DrivingSchoolService drivingSchoolService) {
		this.drivingSchoolService = drivingSchoolService;
	}
	
	public void setLantitude(double lantitude) {
		this.lantitude = lantitude;
	}
	
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public double getLantitude() {
		return lantitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public int getRadius() {
		return radius;
	}

	public int getSort() {
		return sort;
	}

	public List<DrivingSchool> getList() {
		return list;
	}

	public void setList(List<DrivingSchool> list) {
		this.list = list;
	}
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	//默认获取驾校信息
	public String GetDrivingSchool()
	{
		
		if(page>0&&num>0)
			list=this.drivingSchoolService.queryDrivingSchoolFromDB((page-1)*num, num);
		else 
			list=this.drivingSchoolService.queryDrivingSchoolFromDB(0, 10);
			
		return "success";
	}
	
	//根据名称获取驾校名称
	public String GetDrivingSchoolByName()
	{
		if(page>0&&num>0)
			list=this.drivingSchoolService.queryDrivingSchoolByNameFromDB(schoolName,(page-1)*num, num);
		else 
			list=this.drivingSchoolService.queryDrivingSchoolByNameFromDB(schoolName,0, 10);
		return "success";
	}

	//经纬度
	public String GetDrivingSchoolByLocation()
	{
		list=drivingSchoolService.GetDrivingSchools(lantitude,longtitude,radius,sort);
		return "success";
	}
	
	
	int region;
	int id;
	int num;
	int page;
	int total;
	
	List<DrirvingSchooleNews> drirvingSchooleNews;
	DrirvingSchooleNews drirvingSchooleDetailNews;
	
	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) 
	{
		this.page = page;
	}

	public int getTotal() 
	{
		return total;
	}

	public void setTotal(int total) 
	{
		this.total = total;
	}
	
	public List<DrirvingSchooleNews> getDrirvingSchooleNews() {
		return drirvingSchooleNews;
	}

	public void setDrirvingSchooleNews(List<DrirvingSchooleNews> drirvingSchooleNews) {
		this.drirvingSchooleNews = drirvingSchooleNews;
	}

	public DrirvingSchooleNews getDrirvingSchooleDetailNews() {
		return drirvingSchooleDetailNews;
	}

	public void setDrirvingSchooleDetailNews(DrirvingSchooleNews drirvingSchooleDetailNews) 
	{
		this.drirvingSchooleDetailNews = drirvingSchooleDetailNews;
	}

	public String SearchDrivingListNews()
	{
		drirvingSchooleNews=drivingSchoolService.SearchDrivingListNews(region,page,num);
		total=(drirvingSchooleNews==null?0:drirvingSchooleNews.size());
		return "success";
	}
	
	public String SearchDrivingDetailNews()
	{
		drirvingSchooleDetailNews=drivingSchoolService.SearchDetailNews(id);
		return "success";
	}
	
	List<Region> regions;
	
	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public String GetRegionForSchool()
	{
		regions=drivingSchoolService.GetRegions();
		return "success";
	}
}
