package com.sts.news.action;

import java.util.List;

import com.sts.news.model.NewsDetailForPhone;
import com.sts.news.model.NewsForPhone;
import com.sts.news.model.RoadInfo;
import com.sts.news.model.RoadWork;
import com.sts.news.service.NewsForPhoneService;

public class NewsForPhoneAction 
{
	private int type;
	private String subType;
	private int page;
	private int num;
	private List<NewsForPhone> newsList;
	private NewsForPhoneService newsForPhoneService;
	private int total;
	
	private int newsID;
	private NewsDetailForPhone newsDetailForPhone;
	
	public void setNewsForPhoneService(NewsForPhoneService newsForPhoneService) {
		this.newsForPhoneService = newsForPhoneService;
	}	
	public void setNewsDetailForPhone(NewsDetailForPhone newsDetailForPhone) {
		this.newsDetailForPhone = newsDetailForPhone;
	}
	public NewsDetailForPhone getNewsDetailForPhone() {
		return newsDetailForPhone;
	}	
	public int getNewsID() {
		return newsID;
	}
	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
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
	public List<NewsForPhone> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsForPhone> newsList) {
		this.newsList = newsList;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	//获取列表新闻
	public String GetListNews()
	{
		if(page==0 || num==0)
		{
			page=1;
			num=10;
		}
		newsList=newsForPhoneService.GetListNews(type,subType,page,num);
		total=newsForPhoneService.count(type,subType);
		return "success";
	}
	
	//获取详细新闻
	public String GetDetailNews()
	{
		newsDetailForPhone=newsForPhoneService.GetDetailNews(newsID);
		return "success";
	}
	
	//获取公交新闻
	public String GetBusListNews()
	{
		if(page==0 || num==0)
		{
			page=1;
			num=10;
		}
		
		newsList=newsForPhoneService.GetListNews(1, "1", page, num);
		total=newsForPhoneService.count(type, "1");
		return "success";
	}
	
	//获取公交详情
	public String GetBusDetailNews()
	{
		newsDetailForPhone=newsForPhoneService.GetDetailNews(newsID);
		return "success";
	}
	
	List<RoadInfo> roadInfos;
	public List<RoadInfo> getRoadInfos() {
		return roadInfos;
	}
	
	//获取路况信息
	public String GetRoadNews()
	{
		if(page==0 || num==0)
		{
			page=1;
			num=10;
		}
		
		roadInfos=this.newsForPhoneService.queryRoadList(page, num);
		total=this.newsForPhoneService.countRoad();
		
		return "success";
	}
	
	List<RoadWork> roadWorks;
	public List<RoadWork> getRoadWorks() {
		return roadWorks;
	}
	
	//道路施工信息
	public String GetRoadWorkNews()
	{
		if(page==0 || num==0)
		{
			page=1;
			num=10;
		}
		
		this.roadWorks=this.newsForPhoneService.queryRoadWorkList(page, num);
		total=this.newsForPhoneService.countRoadWork();
		return "success";
	}
	
}
