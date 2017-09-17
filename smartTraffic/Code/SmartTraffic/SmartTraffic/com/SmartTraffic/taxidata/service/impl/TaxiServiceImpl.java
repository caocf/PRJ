package com.SmartTraffic.taxidata.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.SmartTraffic.common.GeoPoint;
import com.SmartTraffic.common.PointTraslation;
import com.SmartTraffic.common.TransPoint;
import com.SmartTraffic.highway.model.StakePoint;
import com.SmartTraffic.multiple.model.HGcGcd;
import com.SmartTraffic.multiple.service.impl.MultipleServiceImpl;
import com.SmartTraffic.taxidata.dao.TaxiDao;
import com.SmartTraffic.taxidata.service.TaxiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.action.result.BaseResult;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;

@Service("taxiService")
public class TaxiServiceImpl extends BaseService implements TaxiService{

	@Resource(name="taxiDao")
	private TaxiDao taxiDao;
	 public static String innerUrl = "http://10.100.9.199:3000/mapAPI/getLatLon?nettype=0&customer=09eb9edad501e8ed917b9dbb0fb4ed66";
	    public static String  outUrl = "http://122.224.81.125:3000/mapAPI/getLatLon?nettype=1&customer=09eb9edad501e8ed917b9dbb0fb4ed66";
	@Override
	public BaseResult searchAllTaxis(String date) {
		{
			List<TransPoint> pointlist = new ArrayList<TransPoint>();
			List<Map<String,Object>> list =  (List<Map<String, Object>>) this.taxiDao.searchAllTaxis(date);
			if(list==null){
				return null;
			}
			String lonlatJson = "";
			String lonlatReq = "";
			int count = 0;
			//System.out.println("開始時間："+DateTimeUtil.getCurrTimeFmt());
			for(int i=0;i<list.size();i++){
				count++;
				Map<String,Object> map = list.get(i);
				double lon = bigDecimalToDouble(map.get("JD"))/10000000;
				double lat = bigDecimalToDouble(map.get("WD"))/10000000;
				String lonlats = lon+","+lat+"";
				lonlatReq = lonlatReq+ lonlats+";";
				if((1+i)%20==0){
					lonlatReq=lonlatReq.substring(0,lonlatReq.length()-1);
					String json = transLonlatTo02(lonlatReq);
					//System.out.println("json"+json);
					JSONObject jsonobj = JSONObject.parseObject(json).getJSONObject("result");
					JSONObject poisObj =JSONObject.parseObject(jsonobj.toString()).getJSONObject("pois");
					JSONArray array =JSONObject.parseObject(poisObj.toString()).getJSONArray("item");
					//System.out.println("count："+count);
					for(int j=0;j<array.size();j++){
						TransPoint point = JSON.parseObject(array.get(j).toString(), TransPoint.class);	
						pointlist.add(point);
					}
					lonlatReq="";
				}else if(i==list.size()-1){
					lonlatReq=lonlatReq.substring(0,lonlatReq.length()-1);
					String json = transLonlatTo02(lonlatReq);
					JSONObject jsonobj = JSONObject.parseObject(json).getJSONObject("result");
					JSONObject poisObj =JSONObject.parseObject(jsonobj.toString()).getJSONObject("pois");

					//System.out.println("(1+i)%20"+((1+i)%20));
					if((1+i)%20==1){
						JSONObject item =JSONObject.parseObject(poisObj.toString()).getJSONObject("item");
						float lastlon = item.getFloatValue("lon");
						float lastlan = item.getFloatValue("lat");
						TransPoint point = new TransPoint();
						point.setLat(lastlan);
						point.setLon(lastlon);
						pointlist.add(point);
					}else{
						JSONArray shiparray =JSONObject.parseObject(poisObj.toString()).getJSONArray("item");
					    // System.out.println("count："+count+"--现在的时间："+DateTimeUtil.getCurrTimeFmt());
						for(int j=0;j<shiparray.size();j++){
							TransPoint point = JSON.parseObject(shiparray.get(j).toString(), TransPoint.class);	
							pointlist.add(point);
						   }
						}
					    lonlatReq="";
				}else{
					
				}
				list.set(i, map);
			}
			BaseResult result = new BaseResult();
			result.setResultcode(BaseResult.RESULT_OK);
			result.setObj(list);
			Map map = new HashMap<String,Object>();
			map.put("points", pointlist);
			result.setMap(map);
			//System.out.println("結束時間："+DateTimeUtil.getCurrTimeFmt());
			return result;

		}
	}

	public static double bigDecimalToDouble(Object obj){
		if(obj instanceof BigDecimal){
			BigDecimal demical = (BigDecimal) obj;
			return demical.doubleValue();
		}else {
			return (double)obj;
		}
	}
	
	@Override
	public Map<String,Object> searchTaxiData(String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		long taxiAllNum = this.taxiDao.searchAllTaxisCount();
		long taxiActNum = this.taxiDao.searchActiveCount(date);
		float taxiAvgSpeed = this.taxiDao.searchAvrSpeed(date);
		map.put("taxiAllNum", taxiAllNum);
		map.put("taxiActNum", taxiActNum);
		map.put("taxiAvgSpeed", taxiAvgSpeed);
		return map;
	}

	
	
	protected static String transLonlatTo02(String lonlats){
		String url = innerUrl;
		long a = System.currentTimeMillis();
		StringBuffer s = new StringBuffer();
		String params = "&latlon=";
		
		params = params +lonlats;
	try{
		URL u = new URL(url  + params);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(u.openStream()));
		StringBuffer bs = new StringBuffer();
		String l = null;
		while ((l = buffer.readLine()) != null) {
			bs.append(l.replaceAll("\\s", ""));
		}
		buffer.close();
		return bs.toString();
	}catch(Exception e){
		e.printStackTrace();
		return "";
	}
	}
	
	
	
	
}
