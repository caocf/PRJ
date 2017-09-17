package com.sts.news.action;

import java.util.List;

import com.sts.news.model.NewsDetailForPhone;
import com.sts.news.model.NewsForPhone;
import com.sts.news.model.NewsTypeForWeb;
import com.sts.news.service.NewsForWebService;

/**
 * web端新闻快讯
 * @author Administrator zhou
 *
 */
public class NewsForWebAction 
{
	private int type;									//新闻类型
	private int page;									//页
	private int num;									//数
	private List<NewsForPhone> newsList;					//返回新闻列表
	private NewsForWebService newsForWebService;		
	private int total;									//返回新闻总数
	
	private int newsID;									//新闻ID
	private NewsDetailForPhone newsDetailForWeb;			//返回新闻详情
	
	
	private List<NewsTypeForWeb> newsTypeForWebs;					//返回新闻类型	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public int getNewsID() {
		return newsID;
	}

	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}

	public NewsDetailForPhone getNewsDetailForWeb() {
		return newsDetailForWeb;
	}

	public void setNewsDetailForWeb(NewsDetailForPhone newsDetailForWeb) {
		this.newsDetailForWeb = newsDetailForWeb;
	}

	public List<NewsTypeForWeb> getNewsTypeForWebs() {
		return newsTypeForWebs;
	}

	public void setNewsTypeForWebs(List<NewsTypeForWeb> newsTypeForWebs) {
		this.newsTypeForWebs = newsTypeForWebs;
	}

	public void setNewsForWebService(NewsForWebService newsForWebService) {
		this.newsForWebService = newsForWebService;
	}

	//新闻类型
	public String SearchNewsTypeForWeb()
	{
		newsTypeForWebs=newsForWebService.SearchNewsType();
		total=(newsTypeForWebs==null?0:newsTypeForWebs.size());
		return "success";
	}
	
	//新闻列表
	public String SearchListNewsForWeb()
	{
		if(page==0 || num==0)
		{
			page=1;
			num=10;
		}
		
		newsList=newsForWebService.SearchListNews(type,page,num);
		total=(newsList==null?0:newsList.size());
		return "success";
	}
	
	//新闻详情
	public String SearchDetailNewsForWeb()
	{
		newsDetailForWeb=newsForWebService.SearchDetailNews(newsID);
		return "success";
	}
}
