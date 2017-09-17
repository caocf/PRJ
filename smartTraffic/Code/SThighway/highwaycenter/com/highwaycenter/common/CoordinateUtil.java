package com.highwaycenter.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.highwaycenter.video.model.HSpCameraDTO;

public class CoordinateUtil {
	public static final String BASE_URL = "http://172.20.8.46:8080/Stake/stakeinfo.json?";
	
	public static String transferStakename(String lxdm,String knamepre,String knameafter){
		return lxdm+"-"+knamepre+"+"+knameafter;
	}
	
	public static String transferStakename(String lxdm,Float zh){
		String zhstr = zh.toString();
		if(zhstr.indexOf(".")>-1){
			String zhs[] = zhstr.split("\\.");
	
			return lxdm+"-K"+zhs[0]+"+"+zhs[1];
		}
		return null;
	}
	
	public static String transferKzh(String stakenames,String offset,String dir) {
    	try {
		   stakenames = URLDecoder.decode(stakenames, "UTF-8");

		   stakenames = URLEncoder.encode(stakenames, "GBK"); 	   
		   String urlPath = BASE_URL+"stakenames="+stakenames;
		   
           if(dir!=null&&!dir.equals("")){
        	   dir = URLDecoder.decode(dir, "UTF-8");
        	   dir = URLEncoder.encode(dir, "GBK"); 
        	   urlPath = urlPath+"&dir="+dir;
           }
   		System.out.println("k桩号url"+urlPath);
		String json = HttpUtil.sendGet(urlPath);
		System.out.println("调用k桩号转经纬度result"+json);
		return json;
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("网络异常，查询失败");
    		 return null;
    	}
	
	}
	
	public static List<String> transferKzhToList(String stakenames,String offset,String dir) {
		List<String> list = new ArrayList<String>();
    	try {
		   stakenames = URLDecoder.decode(stakenames, "UTF-8");
		   stakenames = URLEncoder.encode(stakenames, "GBK"); 	   
		   String urlPath = BASE_URL+"stakenames="+stakenames;
		   
           if(dir!=null&&!dir.equals("")){
        	   dir = URLDecoder.decode(dir, "UTF-8");
        	   dir = URLEncoder.encode(dir, "GBK"); 
        	   urlPath = urlPath+"&dir="+dir;
           }
   		System.out.println("k桩号url"+urlPath);
		String json = HttpUtil.sendGet(urlPath);
		System.out.println("调用k桩号转经纬度result"+json);
          if(json==null||json.equals("")){
          	return null;
          }
          
          JSONObject  jsonObj = new JSONObject(json);
          String statusCode = jsonObj.getString("statuscode");
          if(statusCode.equals("100000")){
          	JSONArray jsonarray = jsonObj.getJSONArray("stakelist");
          	JSONObject stakeobj = jsonarray.getJSONObject(0);
          	Double jw = stakeobj.getDouble("offsetlongitude");
          	Double wd = stakeobj.getDouble("offsetlatitude");
          	if(jw!=null){
          		list.add(0, jw.toString());
          	}else{
          		list.add(0, "");
          	}
          	if(wd!=null){
          		list.add(1, wd.toString());
          	}else{
          		list.add(1, "");
          	}
          }else{
          	return null;
          }
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("网络异常，查询失败");
    		 return null;
    	}
    	return list;
	
	}
	

}
