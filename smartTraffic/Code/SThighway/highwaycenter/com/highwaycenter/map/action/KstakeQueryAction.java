package com.highwaycenter.map.action;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.common.HttpUtil;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class KstakeQueryAction extends BaseAction{

	private static final long serialVersionUID = -5432493301301898709L;
	private String stakenames;  //路线
//	private String stakenames2;//桩号
	private String dir;
	private BaseResult result;
	
	public static final String BASE_URL = "http://172.20.8.46:8080/Stake/stakeinfo.json?";

	public String transferPre() {
    	try {
		   if (stakenames == null || stakenames.equals("")){
			   result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
		   }
		   
		   if(stakenames.indexOf("+")>-1){
			   stakenames.replace("+", "\u002b");
		   }
		   stakenames = URLDecoder.decode(stakenames, "UTF-8");

		   stakenames = URLEncoder.encode(stakenames, "GBK"); 
		   
		   String urlPath = BASE_URL+"stakenames="+stakenames;
		   
           if(dir!=null&&!dir.equals("")){
        	   dir = URLDecoder.decode(dir, "UTF-8");
        	   dir = URLEncoder.encode(dir, "GBK"); 
        	   urlPath = urlPath+"&dir="+dir;
           }

          // urlPath +=
		String json = HttpUtil.sendGet(urlPath);
		result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
        result.setObj(json);
    	}catch(Exception e){
    		e.printStackTrace();
    		 result = new BaseResult(101,"发生异常，获取数据失败");
    	}
    	System.out.println("k桩号获取的坐标系result："+result.getObj());
	      return "json";
    	}
	
	
	
	public String getStakenames() {
		return stakenames;
	}


	public BaseResult getResult() {
		return result;
	}



	public void setStakenames(String stakenames) {
		this.stakenames = stakenames;
	}



	public void setDir(String dir) {
		this.dir = dir;
	}



	public String getDir() {
		return dir;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}



	
	
	
}
