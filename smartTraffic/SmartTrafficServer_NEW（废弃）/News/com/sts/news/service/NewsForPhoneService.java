package com.sts.news.service;

import java.util.ArrayList;
import java.util.List;

import com.sts.common.util.Filter;
import com.sts.news.dao.NewsDao;
import com.sts.news.dao.RoadDao;
import com.sts.news.dao.RoadWorkDao;

import com.sts.news.model.NewsDetailForPhone;
import com.sts.news.model.NewsForPhone;
import com.sts.news.model.RoadInfo;
import com.sts.news.model.RoadWork;


public class NewsForPhoneService 
{
	NewsDao newsDao;
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	public List<NewsForPhone> GetListNews(int type,String subType,int page,int num)
	{
		System.out.println(subType);
		String[] strs=subType.split(",");
		
		List<Integer> il=new ArrayList<Integer>();
		for(String s:strs)
		{
			il.add(Integer.valueOf(s));
		}
		
		List<NewsForPhone> result= newsDao.queryForPhones(type,il, page, num);
		
		
		//简介
		for(NewsForPhone n:result)
		{
			String data=Filter.filterHTML(n.getContent());
			
			n.setContent(data.substring(0, 50)+"...");
		}
		
		return result;
//		List<NewsForPhone> result=new ArrayList<NewsForPhone>();
//
//		return result;
		
//		return simulate(new Random().nextInt(20), type, subType);
	}
	
	public NewsDetailForPhone GetDetailNews(int id)
	{
		List<NewsDetailForPhone> result=newsDao.queryNewsForDetail(id);

		if(result!=null && result.size()>0)
		{
			NewsDetailForPhone r=result.get(0);
			
			r.setContent(Filter.filterImage(r.getContent()));
			
			return r;
		}
		else
			return null;
		
//		return simulateDetail(id);
	}
	
	public int count(int type,String subtype)
	{
		return this.newsDao.count(new NewsForPhone());
	}
	
	public List<RoadInfo> queryRoadList(int page,int num)
	{
		return this.newsDao.queryRoadInfoList((page-1)*num, num);
	}
	
	public List<RoadWork> queryRoadWorkList(int page,int num)
	{
		return this.newsDao.queryRoadWorkInfoList((page-1)*num, num);
	}
	
	public int countRoad()
	{
		return this.newsDao.queryRoadCount();
	}
	public int countRoadWork()
	{
		return this.newsDao.queryRoadWorkCount();
	}
	
//	public NewsDetailForPhone simulateDetail(int id)
//	{
//		NewsDetailForPhone result=new NewsDetailForPhone();
//		
//		result.setId(id);
//		result.setTitle("秀洲区交通运输局"+id);
//		result.setContent("    近日，秀洲区交通运输局开展以“六打六治”为重点的“打非治违”专项行动，严厉打击各类非法违法行为，强化安全生产各项工作措施落到实处。此次行动以道路客运及危险品运输、水路交通和港口经营、公路水运工程建设等领域为重点范围，公路、运管、港航等管理部门加强对企业安全检查，及时发现和解决一些难点和突出问题，依法依规开展执法，配合有关部门，切实做到“四个一律”。\n    各企业深刻吸取江苏省昆山市“8.2”特别重大事故教训，认真组织开展自查自纠，针对存在的问题，做到整改方案、责任、时限、措施和资金“五落实”，全面提高企业依法依规安全生产的水平。\n    截至8月底，秀洲区交通运输局系统各行业管理机构及相关企业共组织执法安全检查28次，"+id);
//		result.setClickNum(new Random().nextInt(1000));
//		result.setDate(new Date());
//		
//		return result;
//	}
//	
//	public List<NewsForPhone> simulate(int num,int type,int subType)
//	{
//		List<NewsForPhone> result=new ArrayList<NewsForPhone>();
//		
//		String[] strings=new String[]{"城市公交","高速公路","普通公路","国道","省道","其他"};
////		boolean[] select=RequestParamsParse.filterType(type, 6);
//		
//		for(int i=0;i<num;i++)
//		{
//			NewsForPhone temp=new NewsForPhone();
//			temp.setId(i+1);
//			temp.setTitle("标题"+i);
//			temp.setContent(strings[new Random().nextInt(6)]+i);
//			temp.setType(type);
//			temp.setSubType(subType);
//			temp.setDate(new Date().toString());
//			result.add(temp);
//		}
//		
//		return result;
//	}
}
