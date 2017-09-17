package com.sts.smartbus.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sts.common.util.GlobalVar;
import com.sts.parking.action.PostDateFromSIWEI;
import com.sts.smartbus.dao.CircleDao;
import com.sts.smartbus.model.Circle;
import com.sts.smartbus.model.QRCode;

public class CircleSearchService 
{
	PostDateFromSIWEI postDateFromSIWEI=new PostDateFromSIWEI();
	
	CircleDao circleDao;
	public void setCircleDao(CircleDao circleDao) {
		this.circleDao = circleDao;
	}
	
	public List<Circle> SearchCircleByID(int id,int page,int num)
	{
		List<Circle> result=new ArrayList<Circle>();
		
		String url=GlobalVar.POI_CAT_ACTION+"?poiCat="+id+"&json="+0;
		
		if(page!=0 && num!=0)
			url+="&startPosition="+page+"&pageNumber="+num;
		
		String data=postDateFromSIWEI.GetData(url);
		result=parseCircle(data);
		
		return result;
	}
	
	public List<Circle> SearchCircleByName(String name,int page,int num)
	{
		List<Circle> result=new ArrayList<Circle>();
		
		try {
			name=URLEncoder.encode(name,"UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String url=GlobalVar.POI_KEY_ACTION+"?key="+name+"&json="+0;
		
		if(page!=0 && num!=0)
			url+="&startPosition="+page+"&pageNumber="+num;
		else
			url+="&startPosition="+1+"&pageNumber="+10;
		
		String data=postDateFromSIWEI.GetData(url);
		
		System.out.println(url);
		System.out.println(data);
		
		result=parseCircle(data);
		
		return result;
	}
	
	public List<Circle> parseCircle(String data)
	{
		List<Circle> result=new ArrayList<Circle>();
		
		data=changeErrorJson(data);		
		
		JSONObject jsonObject=JSONObject.fromObject(data);
		
		jsonObject=jsonObject.getJSONObject("FeatureCollection");
		
		
		System.out.println(data);
		
		JSONArray array=jsonObject.getJSONArray("featureMember");
		
		for(int i=0;i<array.size();i++)
		{
			jsonObject=array.getJSONObject(i);
			jsonObject=jsonObject.getJSONObject("JXPOI");
			Circle temp=new Circle();
			
			temp.setAddress(jsonObject.getString("ADDRESS"));
			temp.setId(jsonObject.getInt("OID"));
			
			temp.setName(jsonObject.getString("NAME"));
//			temp.setPhone();
			temp.setRegion(jsonObject.getString("OWNDS"));
			temp.setStreet(jsonObject.getString("OWNSTREET"));
			temp.setTime(jsonObject.getString("TIME_"));
			
			jsonObject=jsonObject.getJSONObject("GEOMETRY");
			jsonObject=jsonObject.getJSONObject("Point");
			String lanAndLon=jsonObject.getString("coordinates");
			String[] lanAndLons=lanAndLon.split(",");
			temp.setLan(Double.valueOf(lanAndLons[1]));
			temp.setLon(Double.valueOf(lanAndLons[0]));
			
			result.add(temp);
		}
		
		
		return result;
	}
	
	public String  changeErrorJson(String data)
	{
		String result=data.replace("[", "");
		result= result.replace("]", "");
		
		result=result.replace("\"featureMember\":", "\"featureMember\":[");
		result=result.replace("}}}}", "}}]}}");
		return result;		
	}
	
	public String changeError2(String data)
	{
		String result=data;
		
		result=result.replace("\"JXPOI\":", "");
		result=result.replace("{{", "{");
		result=result.replace("}}", "}");
		
		return result;
	}
	
	public List<QRCode> queryStation(String name,int num)
	{
		return this.circleDao.queryName(name,num);
	}

}
