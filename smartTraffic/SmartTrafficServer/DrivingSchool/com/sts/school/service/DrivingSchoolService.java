package com.sts.school.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sts.bike.action.BikeAction;
import com.sts.school.dao.DrivingSchoolDao;
import com.sts.school.model.DrirvingSchooleNews;
import com.sts.school.model.DrivingSchool;
import com.sts.school.model.Region;

public class DrivingSchoolService 
{
	DrivingSchoolDao drivingSchoolDao;
	public void setDrivingSchoolDao(DrivingSchoolDao drivingSchoolDao) {
		this.drivingSchoolDao = drivingSchoolDao;
	}
	public List<DrivingSchool> queryDrivingSchoolFromDB(int start,int num)
	{
		return this.drivingSchoolDao.querySchoolDefault(start, num);
	}
	
	public List<DrivingSchool> queryDrivingSchoolByNameFromDB(String name,int start,int num)
	{
		return this.drivingSchoolDao.querySchoolByName(name, start, num);
	}
	
	
	//以下为模拟信息
	
	public List<DrivingSchool> GetDrivingSchools(double lantitude,double longtitude,int radius,int sort)
	{
		List<DrivingSchool> result=simulate();
			
		return result;
	}
	
	public List<DrivingSchool> GetDrivingSchoolByName(String name)
	{
		List<DrivingSchool> result=simulate(name);
		
		return result;
	}
	
	public List<DrirvingSchooleNews> SearchDrivingListNews(int type,int page,int num)
	{
		return simulate(num==0?10:num, type);
	}
	
	public DrirvingSchooleNews SearchDetailNews(int id)
	{
		return simulateDetail(id);
	}
	
	public DrirvingSchooleNews simulateDetail(int id)
	{
		DrirvingSchooleNews result=new DrirvingSchooleNews();
		
		result.setId(id);
		result.setTitle("秀洲区交通运输局"+id);
		result.setContent("近日，秀洲区交通运输局开展以“六打六治”为重点的“打非治违”专项行动，严厉打击各类非法违法行为，强化安全生产各项工作措施落到实处。此次行动以道路客运及危险品运输、水路交通和港口经营、公路水运工程建设等领域为重点范围，公路、运管、港航等管理部门加强对企业安全检查，及时发现和解决一些难点和突出问题，依法依规开展执法，配合有关部门，切实做到“四个一律”。各企业深刻吸取江苏省昆山市“8.2”特别重大事故教训，认真组织开展自查自纠，针对存在的问题，做到整改方案、责任、时限、措施和资金“五落实”，全面提高企业依法依规安全生产的水平。截至8月底，秀洲区交通运输局系统各行业管理机构及相关企业共组织执法安全检查28次，排查安全隐患55起，已整改47起，整改率85.5%。"+id);
		result.setDate(new Date());
		
		return result;
	}
	
	public List<DrirvingSchooleNews> simulate(int num,int type)
	{
		List<DrirvingSchooleNews> result=new ArrayList<DrirvingSchooleNews>();
		
//		boolean[] select=RequestParamsParse.filterType(type, 6);
		
		for(int i=0;i<num;i++)
		{
			DrirvingSchooleNews temp=new DrirvingSchooleNews();
			temp.setId(i);
			temp.setTitle("标题"+i);
			temp.setContent("驾校信息");
			temp.setType(type);
			temp.setDate(new Date());
			result.add(temp);
		}
		
		return result;
	}
	
	
	public List<DrivingSchool> simulate(String name)
	{
		List<DrivingSchool> result=new ArrayList<DrivingSchool>();
		double long1=120.726627;
		double lan1=30.794475;
		
		double long2=120.813008;
		double lan2=30.744207;
		
		String address="交通银行嘉兴分行附近";
		String phone="1234567890";
		
		Random random=new Random();
		
		for(int i=0;i<10;i++)
		{
			DrivingSchool temp=new DrivingSchool();
			
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
	
	public List<DrivingSchool> simulate()
	{
		return simulate("嘉兴国宏驾校");
	}
	
	public List<Region> GetRegions()
	{
		String[] temp= BikeAction.REGIONS;
		
		List<Region> result=new ArrayList<Region>();
		
		for(int i=0;i<temp.length;i++)
		{
			Region tempRegion=new Region();
			tempRegion.setRegionID(i);
			tempRegion.setRegionName(temp[i]);
			result.add(tempRegion);
		}
		
		return result;
		
	}
}
