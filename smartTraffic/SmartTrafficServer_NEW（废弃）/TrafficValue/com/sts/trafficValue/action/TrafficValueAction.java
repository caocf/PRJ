package com.sts.trafficValue.action;

import java.util.List;

import com.sts.trafficValue.model.RealRoadInfo;
import com.sts.trafficValue.model.TrafficValue;
import com.sts.trafficValue.service.TrafficValueService;

public class TrafficValueAction 
{
	TrafficValue trafficValue;
	TrafficValueService trafficValueService;
	
	public TrafficValue getTrafficValue() {
		return trafficValue;
	}

	public void setTrafficValue(TrafficValue trafficValue) {
		this.trafficValue = trafficValue;
	}

	public void setTrafficValueService(TrafficValueService trafficValueService) {
		this.trafficValueService = trafficValueService;
	}

	
	/**
	 * 获取交通指数
	 * @return
	 */
	public String GetTrafficValue()
	{
		trafficValue=trafficValueService.GetTrafficValue();
		
		return "success";
	}
	
	
	List<RealRoadInfo> realRoadInfos;
	int type;
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<RealRoadInfo> getRealRoadInfos() {
		return realRoadInfos;
	}

	public void setRealRoadInfos(List<RealRoadInfo> realRoadInfos) {
		this.realRoadInfos = realRoadInfos;
	}

	public String GetRealInformation()
	{
		realRoadInfos=trafficValueService.GetRealRoadInfo(type);
		
		return "success";
	}
	
	int page;
	int num;
	int direction;
	public void setPage(int page) {
		this.page = page;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	String result;
	String result_area;
	public String getResult() {
		return result;
	}
	public String getResult_area() {
		return result_area;
	}
	//道路
	public String queryRoad()
	{
		if(page>0&num>0)
			result=this.trafficValueService.GetRoadTrafficValue(direction==0?1:direction, page, num);
		
		return "success";
	}
	//通道
	public String queryChannel()
	{
		if(page>0&num>0)
			result=this.trafficValueService.GetChannelTrafficValue(direction==0?1:direction, page, num);
		
		return "success";
	}
	//热点
	public String queryHot()
	{
		if(page>0&num>0)
			result=this.trafficValueService.GetHotTrafficValue(page, num);
		return "success";
	}
	//区域
	public String queryRegion()
	{
		if(page>0&num>0)
			result=this.trafficValueService.GetRegionTrafficValue(page, num);
		return "success";
	}
	//区域 二环
	public String queryArea()
	{
		if(page>0&num>0){
			result=this.trafficValueService.GetRegionTrafficValue(page, num);
			result_area=this.trafficValueService.GetAreaTrafficValue(page, num);
		}
			
		return "success";
	}
	
	
}
