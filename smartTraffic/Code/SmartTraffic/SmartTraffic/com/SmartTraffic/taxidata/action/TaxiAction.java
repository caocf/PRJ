package com.SmartTraffic.taxidata.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.SmartTraffic.taxidata.service.TaxiService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

public class TaxiAction extends BaseAction {

	
	private static final long serialVersionUID = -5241934756797491770L;
	private List<?> results ;
	private Map<String,Object> map;
	@Resource(name="taxiService")
	private TaxiService taxiService;
	
	private BaseResult message = new BaseResult();
	
	public String searchAllTaxis(){
		try {
			Date temp=new Date();
			long t=temp.getTime()-5*60*1000;
			temp=new Date(t);	
			String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp);
			message = this.taxiService.searchAllTaxis(date);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			message.setResultcode(90);
			message.setResultdesc("网络连接错误，出租车定位失败");
		}
		return "success";
	}
	
	
	public String searchTaxiData(){
		try {
			Date temp=new Date();
			long t=temp.getTime()-5*60*1000;
			temp=new Date(t);	
			String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp);
			
			map = this.taxiService.searchTaxiData(date);
			message.setResultcode(BaseResult.RESULT_OK);
            message.setObj(map);
			
		} catch (Exception e) {
			message.setResultcode(90);
			message.setResultdesc("网络连接错误,出租车统计信息获取失败");
		}
		return "success";
	}
	

	public BaseResult getMessage() {
		return message;
	}

	
	
}
