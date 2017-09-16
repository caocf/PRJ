package com.example.smarttraffic.common.complain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class ComplainRequest extends BaseRequest
{
	public static final String[] COMPLAIN_NAME=new String[]{"默认类型","出行规划","长途客运","铁路信息","民航信息","汽车维修","驾培服务","新闻快讯","公共自行车","智慧公交"};
	private int type;
	private String title;
	private String content;
	private String contact;
	private Date date;
	private String persion;
	
	public String getPersion()
	{
		return persion;
	}
	public void setPersion(String persion)
	{
		this.persion = persion;
	}
	
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams postParams=new PostParams();
		postParams.setUrl(HttpUrlRequestAddress.COMPLAIN_SUBMIT_URL);
		
		Map<String, Object> param=new HashMap<String, Object>();
		
		param.put("type",type );
		param.put("title",title );
		param.put("content",content );
		param.put("contact", contact);
		postParams.setParams(param);
		
		return postParams;
	}
	
}
