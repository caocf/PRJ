package com.SmartTraffic.multiple.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.SmartTraffic.common.Constants;
import com.SmartTraffic.common.GeoPoint;
import com.SmartTraffic.common.HttpUtil;
import com.SmartTraffic.common.PointTraslation;
import com.SmartTraffic.common.TransPoint;
import com.SmartTraffic.gcz.dao.GcdDao;
import com.SmartTraffic.highway.dao.HighwayBaseDao;
import com.SmartTraffic.multiple.model.HGcGcd;
import com.SmartTraffic.multiple.model.ShipModel;
import com.SmartTraffic.multiple.model.TaxiModel;
import com.SmartTraffic.multiple.model.TrafficModel;
import com.SmartTraffic.multiple.model.TrafficTj;
import com.SmartTraffic.taxidata.service.TaxiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.HttpPost;
import com.common.utils.PropertyLoader;

@Service("multipleService")
public class MultipleServiceImpl extends BaseService implements MultipleService{
  // public static Logger Log = Logger.getLogger(MultipleServiceImpl.class);

	@Resource(name="highwayBaseDao")
	private HighwayBaseDao highwayBaseDao;
	@Resource(name="gcdDao")
	private GcdDao gcdDao;
	@Resource(name="taxiService")
	private TaxiService taxiService;
    public BaseResult result = new BaseResult();
    public static  String SHIP_ACTION_URL ;  //船舶定位信息
    public static  String TAXI_ACTION_URL ;  //出租车定位
    public static  String TAXI_DATA_URL ;  //出租车统计数据
    public static String MAP_URL;
    public static String GETLONLAT_URL;
    public static String GETGCDINFO;
    public static String GETTRAFFIC;
    public static String innerUrl = "http://10.100.9.199:3000/mapAPI/getLatLon?nettype=0&customer=09eb9edad501e8ed917b9dbb0fb4ed66";
    public static String  outUrl = "http://122.224.81.125:3000/mapAPI/getLatLon?nettype=1&customer=09eb9edad501e8ed917b9dbb0fb4ed66";
     static {
    	 Properties pro = PropertyLoader.getPropertiesFromClassPath("infourl.properties",null);
    	 SHIP_ACTION_URL = pro.getProperty("shipurl.findallship");
    	 TAXI_ACTION_URL = pro.getProperty("taxiurl.searchalltaxis");
    	 TAXI_DATA_URL = pro.getProperty("taxiurl.searchtaxidata");
    	 MAP_URL = pro.getProperty("mapurl.getLatLon");
    	 GETLONLAT_URL = pro.getProperty("mapurl.getLatLon");
    	 GETGCDINFO = pro.getProperty("shipurl.getGcdinfo");
    	 GETTRAFFIC = pro.getProperty("shipurl.getTrafficByGcdid");
     }

@Override
public BaseResult selectTaxiInfo() {
	try{	
	//请求数据
	String resultjson = HttpUtil.sendGet(TAXI_ACTION_URL);	
	
	//判断有无数据
	JSONObject jsonobj = JSONObject.parseObject(resultjson);
	JSONObject jsonmessage = JSONObject.parseObject(jsonobj.getString("message"));
	String status = jsonmessage.getString("resultcode");
	if(status==null||!status.equals("0")){
		return new BaseResult(Constants.TAXI_RESULTNULL_CODE,"没有出租车信息");
	}

	//List<TaxiModel> modellist = jsonToList(jsonmessage.getString("obj")) ;
	
	this.result.setResultcode(BaseResult.RESULT_OK);		
	this.result.setObj(jsonmessage.getString("obj"));
	
	return result;
	}catch(IOException e){
		 writeFile(TAXI_ACTION_URL, "错误信息:"+e.getMessage());
		 //System.out.println("网络连接错误");
		e.getMessage();
		return new BaseResult(90,"网络连接错误,数据请求失败");
  }catch(Exception e){
	  e.printStackTrace();
	  return null;
  }

}

private List<TaxiModel> jsonToList(String jsonarray){
	List<TaxiModel>  modellist = new ArrayList<TaxiModel>(); 
	JSONArray array = JSONArray.parseArray(jsonarray);
	if(array!=null){
		for(int i=0;i<array.size();i++){
		     JSONObject obj = array.getJSONObject(i);
		     modellist.add(jsonToModel(obj,i));
		}	
	}
	return modellist;
}


private BaseResult jsonToShip(String jsonarray){
	String lonlatJson = "";
	String lonlatReq = "";
	List<TransPoint> pointlist = new ArrayList<TransPoint>();
	List<ShipModel>  modellist = new ArrayList<ShipModel>(); 
	JSONArray array = JSONArray.parseArray(jsonarray);
	//System.out.println("array.size()"+array.size());
	int count = 0;
	System.out.println(array.size());
	if(array!=null){
		for(int i=0;i<array.size();i++){
			count++;
			ShipModel ship = JSON.parseObject(array.get(i).toString(), ShipModel.class);
			double lon = ship.getLongitude();
			double lat = ship.getLatitude();
			String lonlats = lon+","+lat+"";
			lonlatReq = lonlatReq+ lonlats+";";
			String addDate = ship.getAdddate();
			
			if(!StringUtils.isBlank(addDate)){
		     if(addDate.contains("T")){
		    	 String replace = addDate.replace("T", " ");
			     ship.setAdddate(replace);  
		     }
			}
			
			if((1+i)%20==0){
				lonlatReq=lonlatReq.substring(0,lonlatReq.length()-1);
				String json = transLonlatTo02(lonlatReq);
				
				JSONObject jsonobj = JSONObject.parseObject(json).getJSONObject("result");
				JSONObject poisObj =JSONObject.parseObject(jsonobj.toString()).getJSONObject("pois");
				JSONArray  shiparray =JSONObject.parseObject(poisObj.toString()).getJSONArray("item");
				// System.out.println("count："+count);
				for(int j=0;j<shiparray.size();j++){
					TransPoint point = JSON.parseObject(shiparray.get(j).toString(), TransPoint.class);	
					pointlist.add(point);
				}
				lonlatReq="";
			}else if(i==array.size()-1){
				lonlatReq=lonlatReq.substring(0,lonlatReq.length()-1);
				String json = transLonlatTo02(lonlatReq);
				JSONObject jsonobj = JSONObject.parseObject(json).getJSONObject("result");
				JSONObject poisObj =JSONObject.parseObject(jsonobj.toString()).getJSONObject("pois");
				System.out.println("(1+i)%20"+((1+i)%20));
				if((1+i)%20==1){
					JSONObject item =JSONObject.parseObject(poisObj.toString()).getJSONObject("item");
					float lastlon = item.getFloatValue("lon");
					float lastlan = item.getFloatValue("lat");
					TransPoint point = new TransPoint();
					point.setLat(lastlan);
					point.setLon(lastlon);
					  //System.out.println("count："+count+"--现在的时间："+DateTimeUtil.getCurrTimeFmt());
					pointlist.add(point);
				}else{
				JSONArray shiparray =JSONObject.parseObject(poisObj.toString()).getJSONArray("item");
			     //System.out.println("count："+count+"--现在的时间："+DateTimeUtil.getCurrTimeFmt());
				for(int j=0;j<shiparray.size();j++){
					TransPoint point = JSON.parseObject(shiparray.get(j).toString(), TransPoint.class);	
					pointlist.add(point);
				   }
				}
				lonlatReq="";
			}else{
			}
			modellist.add(ship);
			
		}	
		//System.out.println("结束时间："+DateTimeUtil.getCurrTimeFmt());
		BaseResult baseresult = new BaseResultOK();
		
		baseresult.setObj(modellist);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pointlist",pointlist);
		baseresult.setMap(map);
		return baseresult;
	}else{
        return new BaseResult(-1,"暂无船舶定位信息");
	}
}


protected List<GeoPoint> shiplistToPoint(List<ShipModel>  modellist){
	
	List<GeoPoint> list =new ArrayList<GeoPoint>();
	String lonlats = "";
	int count=0;
	////System.out.println("开始时间1："+DateTimeUtil.getCurrTimeFmt());
	String json = "";
	for(int i=0;i<modellist.size();i++){
		double lon = modellist.get(i).getLongitude();
		double lat = modellist.get(i).getLatitude();
		lonlats = lonlats +lon+","+lat+";";
		if((i+1)%50==0){
			count++;
			lonlats = lonlats.substring(0,lonlats.length()-1);
			
			json = json+  transformPyLonlatList(lonlats);
			//list.addAll(pointlist);
			////System.out.println(DateTimeUtil.getCurrTimeFmt()+":"+lonlats);
			lonlats="";
			
		}else{
			
		}
	}
////System.out.println("结束时间："+DateTimeUtil.getCurrTimeFmt());
	return list;
}

protected String shiplistToPointStr(List<ShipModel>  modellist){
	
	String list = "";
	String lonlats = "";
	int count=0;
	//System.out.println("开始时间："+DateTimeUtil.getCurrTimeFmt());
	for(int i=0;i<modellist.size();i++){
		double lon = modellist.get(i).getLongitude();
		double lat = modellist.get(i).getLatitude();
		lonlats = lonlats +lon+","+lat+";";
		if((i+1)%50==0){
			count++;
			lonlats = lonlats.substring(0,lonlats.length()-1);
			
			String str =   transformPyLonlatStr(lonlats);
			list = list+str;
			//System.out.println("size:"+lonlats.length()+"|count:"+count+"--"+DateTimeUtil.getCurrTimeFmt());
			lonlats="";
			
		}else{
			
		}
	}
	////System.out.println("count"+count);
	////System.out.println("结束时间："+DateTimeUtil.getCurrTimeFmt());
	return list;
}


 private TaxiModel jsonToModel(JSONObject obj,int i){
	 TaxiModel model = new TaxiModel(i+1);
	 model.setBz(obj.getString("bz"));
	 model.setCllxmc(obj.getString("cllxmc"));
	 model.setCp(obj.getString("cp"));
	 model.setCphm(obj.getString("cphm"));
	 model.setCplxmc(obj.getString("cplxmc"));
	 model.setCpys(obj.getString("cpys"));
	 model.setRllx(obj.getString("rllx"));
	 model.setXzqhdm(obj.getString("xzqhdm"));
	 model.setXzqhmc(obj.getString("xzqhmc"));
	 model.setYhmc(obj.getString("yhmc"));
	 model.setYsxzmc(obj.getString("ysxzmc"));
	 String jd = obj.getString("BJ_JD");
	 String wd = obj.getString("BJ_WD");
	 double jdDouble ;
	 double wdDouble;
	 try{
		 jdDouble = Double.parseDouble(jd)/1000000;
	 }catch(Exception e){
		 jdDouble = -1;
	 }
	 try{
	 wdDouble = Double.parseDouble(wd)/1000000;
	 }catch(Exception e){
		 wdDouble=-1;
	 }
	 model.setJD(jdDouble);
	 model.setWD(wdDouble);
	 return model;
 }
 
 @Override
	public BaseResult selectTaxiData() {
		try{	
			//请求数据
			//Log.debug("出租车请求地址:"+TAXI_ACTION_URL);	
			String resultjson = HttpUtil.sendGet(TAXI_DATA_URL);	
			//Log.debug("出租车请求信息:"+resultjson);	
			//判断有无数据
			JSONObject jsonobj = JSONObject.parseObject(resultjson);
			JSONObject jsonmessage = JSONObject.parseObject(jsonobj.getString("message"));
			String status = jsonmessage.getString("resultcode");
			if(status==null||!status.equals("0")){
				return new BaseResult(Constants.TAXI_RESULTNULL_CODE,"没有出租车统计数据");
			}

			//List<TaxiModel> modellist = jsonToList(jsonmessage.getString("obj")) ;
			
			this.result.setResultcode(BaseResult.RESULT_OK);		
			this.result.setObj(jsonmessage.getString("obj"));
			return result;
			}catch(IOException e){
				 //System.out.println("网络连接错误");
				 writeFile(TAXI_DATA_URL, "错误信息:"+e.getMessage());
				 e.getMessage();
				 return new BaseResult(90,"网络连接错误,数据请求失败");
			 }catch(Exception e){
				  e.printStackTrace();
				  return null;
			  
		  }
		
	}
	@Override
	public BaseResult selectShipInfo() {
		try{	
			//请求数据
			Date temp=new Date();
			long t=temp.getTime()-10*60*1000;
			temp=new Date(t);	
			String time = DateTimeUtil.getTimeFmt(temp);
			Map<String, Object> params  = new HashMap<String, Object>();
			params.put("startlongtitude", "120.360837");
			params.put("startlatitude", "31.086247");
			params.put("endlongitude", "121.392236");
			params.put("endlatitude", "30.356072");
			params.put("messagetype", "2");
			params.put("adddate", time);
		    String resultjson = HttpPost.post(SHIP_ACTION_URL,params);	
			
			//判断有无数据
		    if(resultjson==null||resultjson.equals("")){
		    	return new BaseResult(90,"网络连接错误,数据请求失败");
		    }
		    
			JSONObject jsonobj = JSONObject.parseObject(resultjson);
			JSONObject jsonmessage = JSONObject.parseObject(jsonobj.getString("baseResult"));
			String status = jsonmessage.getString("resultcode");
			if(status==null||!status.equals("1")){
				return new BaseResult(Constants.TAXI_RESULTNULL_CODE,"没有船户信息");
			}
            String info = jsonmessage.getJSONObject("obj").getString("data");
		
            this.result = this.jsonToShip(info);
			return result;
			}catch(Exception e){
				 writeFile(SHIP_ACTION_URL, "错误信息:"+e.getMessage());
				 //System.out.println("网络连接错误");
				 e.printStackTrace();
				 return new BaseResult(90,"网络连接错误,数据请求失败");
		  }
	}
	@Override
	public BaseResult selectJdInfo() {
		this.result.setResultcode(BaseResult.RESULT_OK);
		this.result.setObj(this.highwayBaseDao.selectJdInfo());
		return result;
	}

	@Override
	public BaseResult selectNumById(int jtlId) {
		this.result.setResultcode(BaseResult.RESULT_OK);
		this.result.setObj(this.highwayBaseDao.selectNumById(jtlId));
		return result;
	}
 
	
	public static void writeFile(String url,String result){
		//记录时间
	    String today = DateTimeUtil.getCurrTimeFmt("yyyy_MM_dd");
		String filePath = ServletActionContext.getServletContext().getRealPath("/")+today+"_connect.txt";
		File file =new File(filePath);
		try{ 
		if(!file.exists()){
				file.createNewFile();		
		}
		
		FileWriter fileWritter;
		fileWritter = new FileWriter(filePath,true);
		
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        String now = DateTimeUtil.getCurrTimeFmt();
        bufferWritter.write(now+":远程连接：'"+url+"'失败，返回结果："+"\r\n");
        bufferWritter.write(result+"\r\n");
        bufferWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static GeoPoint transformPyLonlat(String points){
		//List<GeoPoint> list = new ArrayList<GeoPoint> ();
		String getUrl = MAP_URL+"&latlon="+points;
	//	//System.out.println(getUrl);
		try {
			String resultjson = HttpUtil.sendGet(getUrl);
		    if(resultjson==null||resultjson.equals("")){
		    	return null;
		    }
		    
			JSONObject jsonobj = JSONObject.parseObject(resultjson).getJSONObject("result");
			JSONObject jsonmessage = JSONObject.parseObject(jsonobj.toString()).getJSONObject("pois");
			JSONObject item =JSONObject.parseObject(jsonmessage.toString()).getJSONObject("item");
			float lon = item.getFloatValue("lon");
			float lan = item.getFloatValue("lat");
			return new GeoPoint(lan,lon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
 
	
	
	public static String transformPyLonlatList(String points){
		List<GeoPoint> list = new ArrayList<GeoPoint> ();
		
		String getUrl = MAP_URL+"&latlon="+points;	
		////System.out.println("getUrl"+getUrl);
		//	String resultjson = transLonlatTo02(points);
	String resultjson = sendGet(getUrl);
	//	try {
		//	resultjson = transLonlatTo02(points);
		//	//System.out.println("查詢123："+DateTimeUtil.getCurrTimeFmt());
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}*/
			return resultjson;
		
		
	}
	
	
	public static String transformPyLonlatStr(String points){
		String returnStr = "";
		String getUrl = MAP_URL+"&latlon="+points;
		try {
		/*	String resultjson = HttpPost.post(GETLONLAT_URL, params,10*1000);*/
			String resultjson = HttpUtil.sendGet(getUrl);
		    if(resultjson==null||resultjson.equals("")){
		    	return returnStr;
		    }
		    
			JSONObject jsonobj = JSONObject.parseObject(resultjson).getJSONObject("result");
			JSONObject jsonmessage = JSONObject.parseObject(jsonobj.toString()).getJSONObject("pois");
			String array = JSONObject.parseObject(jsonmessage.toString()).toJSONString();
			//JSONArray array =JSONObject.parseObject(jsonmessage.toString()).getJSONArray("item");
		    return array;
			/*float lon = item.getFloatValue("lon");
			float lan = item.getFloatValue("lan");*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		
	}

	@Override
	public BaseResult showGcdInfo() {
		List<HGcGcd> list = new ArrayList<HGcGcd>();
		//请求数据
		String resultjson;
		try {
		resultjson = HttpUtil.sendGet(GETGCDINFO);
		JSONObject jsonobj = JSONObject.parseObject(resultjson).getJSONObject("baseResult");
		JSONObject jsonmessage = JSONObject.parseObject(jsonobj.toString()).getJSONObject("obj");
		JSONArray array =JSONObject.parseObject(jsonmessage.toString()).getJSONArray("data");
		for(int i=0;i<array.size();i++){
			HGcGcd gcd = JSON.parseObject(array.get(i).toString(), HGcGcd.class);	
			list.add(gcd);
		}
		this.result = new BaseResultOK();
		this.result.setObj(list);
		return this.result;
		} catch (IOException e) {
			e.printStackTrace();
			return this.result;
		}	
	}

	@Override
	public BaseResult showTrafficByGcd(String gcdid) {
		TrafficTj tj = tjTraffic(gcdid);
		BaseResult baseResult = new BaseResultOK();
		baseResult.setObj(tj);
		return baseResult;
	}

	
	protected static TrafficTj tjTraffic(String gcdid){
		//请求数据
		String resultjson;
		try {
			//System.out.println("gcdi:"+gcdid);
			resultjson = HttpUtil.sendGet(GETTRAFFIC+"?gcdid="+gcdid);
			JSONObject jsonobj = JSONObject.parseObject(resultjson).getJSONObject("baseResult");
			JSONObject jsonmessage = JSONObject.parseObject(jsonobj.toString()).getJSONObject("map");
			JSONObject monthObj = JSONObject.parseObject(jsonmessage.toString()).getJSONObject("monthrecords");
			JSONObject dayObj = JSONObject.parseObject(jsonmessage.toString()).getJSONObject("dayrecords");
			JSONObject yearObj = JSONObject.parseObject(jsonmessage.toString()).getJSONObject("yearrecords");
			JSONArray monthArray =JSONObject.parseObject(monthObj.toString()).getJSONArray("data");
			JSONArray yearArray =JSONObject.parseObject(yearObj.toString()).getJSONArray("data");
			JSONArray dayArray =JSONObject.parseObject(dayObj.toString()).getJSONArray("data");
			
			TrafficModel monthModel = changeTrafficModel(monthArray);
			 TrafficModel yearModel = changeTrafficModel(yearArray);
			 TrafficModel dayModel = changeTrafficModel(dayArray);
			 TrafficTj tj = new TrafficTj(dayModel,monthModel,yearModel);
			 return tj;
			 
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
				}
		
	}
	
	
	
	@Override
	public BaseResult showTrafficByGcdDate(String gcdid, Date startdate,
			Date enddate) {
      return null;
	}
	
	
	

	public static String sendGet(String url)    {
		StringBuffer result = new StringBuffer();
		try {
			URL httpurl = new URL(url);
			/*HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection();
			httpConn.setConnectTimeout(5*1000);
			httpConn.setRequestMethod("GET");*/
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpurl.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line.replaceAll("\\s", ""));
			}
			//in.close();
		} catch (IOException e) {

			//System.out.println("没有结果！" + e);
           return "";
		}
		//System.out.println(result.toString());
		return result.toString();
	}
	
	
	protected static String transLonlatTo02(String lonlats){
		String url = innerUrl;
		long a = System.currentTimeMillis();
		StringBuffer s = new StringBuffer();
		String params = "&latlon=";
		
		params = params +lonlats;
		
	try{
		URL u = new URL(url  + params);
		/*//System.out.println(url + "?" +params);*/
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
	
	protected static TrafficModel changeTrafficModel(JSONArray array){
		JSONArray obj12 = array.getJSONArray(0);
		/*//System.out.println(obj12);*/
		Object obj1 =  obj12.get(0);
		Object obj2 =  obj12.get(1);
		
		double cbsl = 0;
		double cbdw = 0;
		if(obj1 instanceof BigDecimal){
			cbsl =  ((BigDecimal) obj1).doubleValue();
		}
		if(obj2 instanceof BigDecimal){
			cbdw =  ((BigDecimal) obj2).doubleValue();
		}
		return new TrafficModel(cbsl,cbdw);
	}
	
	
}
