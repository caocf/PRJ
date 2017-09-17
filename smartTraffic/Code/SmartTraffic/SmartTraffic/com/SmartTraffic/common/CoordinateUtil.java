package com.SmartTraffic.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.SmartTraffic.highway.model.StakePoint;
import com.SmartTraffic.highway.model.TrafficModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CoordinateUtil {
public static final String BASE_URL = "http://172.20.8.46:8181/Stake/lines.json?";
public static final String TRANS_URL = "http://172.20.8.46:8181/Stake/stakeinfo.json?";

public static String transferStakename(String lxdm,Float zh){
	DecimalFormat decimalFormat=new DecimalFormat(".000");//构造方法的字符格式这里如果小数不足2位,会以0补足.
	String zhstr = decimalFormat.format(zh);//format 返回的是字符串
	//System.out.println("zh没有转换前"+zh);
	//System.out.println("zh转换中"+zhstr);
	if(zhstr.indexOf(".")>-1){
		String zhs[] = zhstr.split("\\.");
	//	System.out.println("zh转换后"+zhs[0]+"|"+zhs[1]);
		return lxdm+"-K"+zhs[0]+"+"+zhs[1];
	}
	return null;
}

public static List<String> transferKzhToList(String stakenames,String offset,String dir) {
	List<String> list = new ArrayList<String>();
	try {
	   stakenames = URLDecoder.decode(stakenames, "UTF-8");
	   stakenames = URLEncoder.encode(stakenames, "GBK"); 	   
	   String urlPath = TRANS_URL+"stakenames="+stakenames;
	   if(dir==null){
		   dir = "0";
	   }
       if(dir!=null&&!dir.equals("")){
    	   dir = URLDecoder.decode(dir, "UTF-8");
    	   dir = URLEncoder.encode(dir, "GBK"); 
    	   urlPath = urlPath+"&dir="+dir;
       }
		//System.out.println("k桩号url"+urlPath);
	String json = HttpUtil.sendGet(urlPath);
	//System.out.println("调用k桩号转经纬度result"+json);
      if(json==null||json.equals("")){
      	return null;
      }
      
      JSONObject  jsonObj = JSONObject.parseObject(json);
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

public static String transferStakename(String lxbh,String zh){
try{
	String str = lxbh+"-";
	float zhfloat = Float.parseFloat(zh);
	String zhstr = String.format("%.3f", zhfloat);
	System.out.println(zhstr);
	if(zhstr.indexOf(".")>-1){
		String zhs[] = zhstr.split("\\.");
		if(zhs[1].equals("000")){
			return str+"K"+zhs[0];
		}
		return str+"K"+zhs[0]+"+"+zhs[1];
	}else{
		return str+"K"+zhstr+"0";
	}
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
}



public static TrafficModel transLine(String stakenames1,String stakenames2,
		Integer dir,Integer count) {
	List<StakePoint> list = new ArrayList<StakePoint>();
	 TrafficModel model = new  TrafficModel();
	try { 
 		   String stakenames = stakenames1+";"+stakenames2;
	      String urlPath = BASE_URL+"stakename="+stakenames;
	        
	      if(dir!=null&&!dir.equals("")){
	        	 urlPath =urlPath+"&dir="+dir;
	      }else{
	    	     urlPath =urlPath+"&dir=0";
	      }
       
		System.out.println("k桩号url"+urlPath);
	String json = HttpUtil.sendGet(urlPath);
	System.out.println("调用k桩号之间的连续纬度result"+json);
      if(json==null||json.equals("")){
    	  model.setSxLineCode(Constants.KSTAKELINE_NULL_CODE);
    	  return model;
      }
      
      JSONObject  jsonObj =JSONObject.parseObject(json);
      String statusCode = jsonObj.getString("statuscode");
      if(statusCode.equals(""+Constants.KSTAKELINE_EXITE_CODE)){
      	JSONArray jsonarray = jsonObj.getJSONArray("lines");
      	System.out.println(stakenames1+"youshuju:>>>>>>>>>>>>>>>>>>>>"+jsonarray);
        for(int i=0;i<jsonarray.size();i++){
        	JSONObject pointObj = jsonarray.getJSONObject(i);
        	StakePoint point = new StakePoint();
        	point.setLatitude(pointObj.getDouble("latitude"));
        	point.setLongitude(pointObj.getDouble("longitude"));
        	list.add(point);
        }

        	model.setSxLineCode(Constants.KSTAKELINE_EXITE_CODE);
        	model.setSxList(list);
       
      }else{
    	  int code = Integer.parseInt(statusCode);
    	 
          	model.setSxLineCode(code);
 
      }
      	
	}catch(IOException e){
		  model.setSxLineCode(Constants.KSTAKELINE_EXCEPTION_CODE);
			return model;
	}
	return model;

}


public static String transferStakename(String lxdm,String knamepre,String knameafter){
	return lxdm+"-"+knamepre+"+"+knameafter;
}

public static String transferKzh(String stakenames,String offset,String dir) {
	try {
	   stakenames = URLDecoder.decode(stakenames, "UTF-8");

	   stakenames = URLEncoder.encode(stakenames, "GBK"); 	   
	   String urlPath = TRANS_URL+"stakenames="+stakenames;
	   
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

}
