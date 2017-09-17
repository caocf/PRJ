package com.sts.news.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sts.common.util.Filter;
import com.sts.news.model.JTGZ;
import com.sts.news.model.NewsDetailForPhone;
import com.sts.news.model.NewsForPhone;
import com.sts.news.model.RoadInfo;
import com.sts.news.model.RoadWork;
import com.sts.news.model.TZXX;
import com.sts.news.service.NewsForPhoneService;

public class NewsForPhoneAction {
	private int type;
	private String subType;
	private int page;
	private int num;
	private List<NewsForPhone> newsList;
	private List<TZXX> tzxxs;
	private List<JTGZ> jtgzs;
	JTGZ jtgz;
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

	public JTGZ getJtgz() {
		return jtgz;
	}
	public NewsDetailForPhone getNewsDetailForPhone() {
		return newsDetailForPhone;
	}

	public List<?> getTzxxs() {
		return tzxxs;
	}

	public List<JTGZ> getJtgzs() {
		return jtgzs;
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

	// 获取列表新闻
	public String GetListNews() {
		if (page == 0 || num == 0) {
			page = 1;
			num = 10;
		}
		newsList = newsForPhoneService.GetListNews(type, subType, page, num);
		total = newsForPhoneService.count(type, subType);
		return "success";
	}

	// 获取详细新闻
	public String GetDetailNews() {
		newsDetailForPhone = newsForPhoneService.GetDetailNews(newsID);
		return "success";
	}

	// 获取公交新闻
	public String GetBusListNews() {
		if (page == 0 || num == 0) {
			page = 1;
			num = 10;
		}

		newsList = newsForPhoneService.GetListNews(1, "1", page, num);
		total = newsForPhoneService.count(type, "1");
		return "success";
	}

	// 获取公交详情
	public String GetBusDetailNews() {
		newsDetailForPhone = newsForPhoneService.GetDetailNews(newsID);
		return "success";
	}

	List<RoadInfo> roadInfos;

	public List<RoadInfo> getRoadInfos() {
		return roadInfos;
	}

	// 获取路况信息
	public String GetRoadNews() {
		if (page == 0 || num == 0) {
			page = 1;
			num = 10;
		}

		roadInfos = this.newsForPhoneService.queryRoadList(page, num);
		total = this.newsForPhoneService.countRoad();

		return "success";
	}

	List<RoadWork> roadWorks;

	public List<RoadWork> getRoadWorks() {
		return roadWorks;
	}

	// 道路施工信息
	public String GetRoadWorkNews() {
		if (page == 0 || num == 0) {
			page = 1;
			num = 10;
		}

		this.roadWorks = this.newsForPhoneService.queryRoadWorkList(page, num);
		total = this.newsForPhoneService.countRoadWork();
		return "success";
	}

	
	/*-----------------------通阻信息接口---------------------------------*/
	
	private String content;
	private String startTime;
	private String endTime;
	private int ignoreHtmlTag;						//1去标签 0不去，默认不去
	private String xxbh;
	private TZXX tzxx;
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public void setIgnoreHtmlTag(int ignoreHtmlTag) {
		this.ignoreHtmlTag = ignoreHtmlTag;
	}

	public void setXxbh(String xxbh) {
		this.xxbh = xxbh;
	}
	public TZXX getTzxx() {
		return tzxx;
	}
	
	public String QueryTzxx() {

		this.tzxxs = this.newsForPhoneService.queryTzxxs(content, startTime,
				endTime);

		if(ignoreHtmlTag==1 && tzxxs!=null)
		for(TZXX t:tzxxs)
		{
			t.setNR(Filter.filterHTML(t.getNR()));
		}
		
		return "success";
	}

	public String QueryTzxxList()
	{
		if(page<1 || num <1)
		{
			page=1;
			num=10;
		}
		
		this.tzxxs=this.newsForPhoneService.queryTzxxList(page, num);
		this.total=this.newsForPhoneService.countTzxx();
		
		if(ignoreHtmlTag==1 && tzxxs!=null)
			for(TZXX t:tzxxs)
			{
				t.setNR(Filter.filterHTML(t.getNR()));
			}
		
		return "success";
	}
	
	public String QueryTzxxDetail()
	{
		this.tzxx=this.newsForPhoneService.queryTzxxDetail(xxbh);
		
		if(ignoreHtmlTag==1 && tzxx!=null)
			tzxx.setNR(Filter.filterHTML(tzxx.getNR()));
		
		return "success";
	}
	
	
	
	
	public String QueryJTGZList()
	{

		this.jtgzs=this.newsForPhoneService.queryJTGZList(page, num);
		this.total=this.newsForPhoneService.countJTGZ();
		
		if(ignoreHtmlTag==1 && jtgzs!=null)
			for(JTGZ t:jtgzs)
			{
				t.setNR(Filter.filterHTML(t.getNR()));
			}
		
		return "success";
	}
	
	public String QueryJTGZDetail()
	{
		
		this.jtgz=this.newsForPhoneService.queryJTGZDetail(xxbh);
		
		if(ignoreHtmlTag==1 && jtgz!=null)
			jtgz.setNR(Filter.filterHTML(tzxx.getNR()));
		
		return "success";
	}

}
