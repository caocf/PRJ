package com.sts.smartbus.action;

import java.util.List;

import com.sts.smartbus.model.Circle;
import com.sts.smartbus.model.QRCode;
import com.sts.smartbus.service.CircleSearchService;

public class CircleSearchAction 
{
	int id;
	int page;
	int num;
	String name;
	
	CircleSearchService circleSearchService;
	List<Circle> circles;
	List<QRCode> busStations;
	
	int addPoi;									//1则返回包含POI的内容
	int poiNum;									//所需poi结果的数量
	
	public void setAddPoi(int addPoi) {
		this.addPoi = addPoi;
	}
	public List<QRCode> getBusStations() {
		return busStations;
	}	
	public List<Circle> getCircles() {
		return circles;
	}
	
	

	public void setId(int id) {
		this.id = id;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setCircleSearchService(CircleSearchService circleSearchService) {
		this.circleSearchService = circleSearchService;
	}


	/**
	 * 通过类型ID获取热点列表
	 * @return
	 */
	public String SearchCircleByID()
	{
		circles=this.circleSearchService.SearchCircleByID(id, page, num);
		
		return "success";
	}
	
	/**
	 * 通过名称获取相似热点列表
	 * @return
	 */
	public String SearchPointByName()
	{
		circles=this.circleSearchService.SearchCircleByName(name, page, num);
		return "success";
	}
	
	/**
	 * 搜索相似公交站点
	 * @return
	 */
	public String SearchSimilarityBusStation()
	{
		//num为0则查询出全部
		busStations=this.circleSearchService.queryStation(name, num);
		
		if(addPoi==1)
		{
			if(poiNum==0)
				poiNum=10;
			
			circles=this.circleSearchService.SearchCircleByName(name, 1, poiNum);
		}
		
		return "success";
	}
}
