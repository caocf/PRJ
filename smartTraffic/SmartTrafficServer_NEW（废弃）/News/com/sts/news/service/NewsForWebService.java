package com.sts.news.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sts.common.util.Filter;
import com.sts.news.dao.NewsDao;
import com.sts.news.model.NewsDetailForPhone;
import com.sts.news.model.NewsDetailForWeb;
import com.sts.news.model.NewsForPhone;
import com.sts.news.model.NewsForWeb;
import com.sts.news.model.NewsTypeForWeb;

public class NewsForWebService 
{
	NewsDao newsDao;
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	public List<NewsTypeForWeb> SearchNewsType()
	{
		List<NewsTypeForWeb> result =new ArrayList<NewsTypeForWeb>();
		
		String[] types=new String[]{"路况信息","公共交通","交通动态","交通气象"};
		
		for(int i=0;i<types.length;i++)
		{
			NewsTypeForWeb tempForWeb=new NewsTypeForWeb();
			
			tempForWeb.setTypeID(i+1);
			tempForWeb.setTypeName(types[i]);
			
			result.add(tempForWeb);
		}
		
		return result;
	}
	
	public List<NewsForPhone> SearchListNews(int type,int page,int num)
	{
		List<NewsForPhone> result= newsDao.queryForPhones(type,new ArrayList<Integer>(), page, num);
		
		//简介
		for(NewsForPhone n:result)
		{
			String data=Filter.filterHTML(n.getContent());
			
			n.setContent(data.substring(0, 50)+"...");
		}
		
		return result;
		
//		return simulate(num==0?10:num, type);
	}
	
	public NewsDetailForPhone SearchDetailNews(int id)
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
	
	public NewsDetailForWeb simulateDetail(int id)
	{
		NewsDetailForWeb result=new NewsDetailForWeb();
		
		result.setId(id);
		result.setTitle("秀洲区交通运输局"+id);
		result.setContent("近日，秀洲区交通运输局开展以“六打六治”为重点的“打非治违”专项行动，严厉打击各类非法违法行为，强化安全生产各项工作措施落到实处。此次行动以道路客运及危险品运输、水路交通和港口经营、公路水运工程建设等领域为重点范围，公路、运管、港航等管理部门加强对企业安全检查，及时发现和解决一些难点和突出问题，依法依规开展执法，配合有关部门，切实做到“四个一律”。各企业深刻吸取江苏省昆山市“8.2”特别重大事故教训，认真组织开展自查自纠，针对存在的问题，做到整改方案、责任、时限、措施和资金“五落实”，全面提高企业依法依规安全生产的水平。截至8月底，秀洲区交通运输局系统各行业管理机构及相关企业共组织执法安全检查28次，排查安全隐患55起，已整改47起，整改率85.5%。"+id);
		result.setClickNum(new Random().nextInt(1000));
		result.setDate(new Date());
		
		return result;
	}
	
	public List<NewsForWeb> simulate(int num,int type)
	{
		List<NewsForWeb> result=new ArrayList<NewsForWeb>();
		
		String[] strings=new String[]{"城市公交","高速公路","普通公路","国道","省道","其他"};
//		boolean[] select=RequestParamsParse.filterType(type, 6);
		
		for(int i=0;i<num;i++)
		{
			NewsForWeb temp=new NewsForWeb();
			temp.setId(i);
			temp.setTitle("标题"+i);
			temp.setContent(strings[new Random().nextInt(6)]+i);
			temp.setType(type);
			temp.setDate(new Date());
			result.add(temp);
		}
		
		return result;
	}
}
