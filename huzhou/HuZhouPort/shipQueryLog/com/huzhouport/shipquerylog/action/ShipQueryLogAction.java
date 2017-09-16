package com.huzhouport.shipquerylog.action;

import java.util.List;

import com.huzhouport.aisno.model.AISNo;
import com.huzhouport.aisno.service.AISNoService;
import com.huzhouport.common.util.DateTimeUtil;
import com.huzhouport.shipquerylog.model.ShipQueryLog;
import com.huzhouport.shipquerylog.service.ShipQueryLogService;

public class ShipQueryLogAction {
	
	/*-----------------------------------------------------------*/
	ShipQueryLogService shipQueryLogService;
	public void setShipQueryLogService(ShipQueryLogService shipQueryLogService) {
		this.shipQueryLogService = shipQueryLogService;
	}
	
	AISNoService aisNoService;
	
	public void setAisNoService(AISNoService aisNoService) {
		this.aisNoService = aisNoService;
	}
	
	/*-----------------------------------------------------------*/
	String startTime;
	String endTime;
	int page;
	int num;
	ShipQueryLog shipQueryLog;
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setShipQueryLog(ShipQueryLog shipQueryLog) {
		this.shipQueryLog = shipQueryLog;
	}
	public ShipQueryLog getShipQueryLog() {
		return shipQueryLog;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	/*-----------------------------------------------------------*/
	List<ShipQueryLog> result;
	int total;
	int resultCode;
	
	public List<ShipQueryLog> getResult() {
		return result;
	}
	public int getTotal() {
		return total;
	}
	public int getResultCode() {
		return resultCode;
	}
	
	/*-----------------------------------------------------------*/
	public String saveShipQueryLog()
	{
		if(shipQueryLog==null)
		{
			resultCode=-1;
			return "success";
		}
		
		shipQueryLog.setQuerytime(DateTimeUtil.getCurrTimeFmt());
		
		if(shipQueryLog.getShipname()!=null && (shipQueryLog.getShipnum()==null || shipQueryLog.getShipnum().equals("") ))
		{
			AISNo aisNo=this.aisNoService.queryShipNameByShipName(shipQueryLog.getShipname());
			if(aisNo!=null)
				shipQueryLog.setShipnum(aisNo.getAisnum());
		}
		
		this.shipQueryLogService.saveShipQueryLog(shipQueryLog);
		
		return "success";
	}
	
	public String queryShipQueryLogs()
	{
		if(page<=0 || num <=0)
		{
			page=1;
			num=10;
		}
		
		result=this.shipQueryLogService.queryShipQueryLogs(startTime, endTime, page, num);
		
		total=this.shipQueryLogService.queryShipQueryLogsNum(startTime, endTime);
		
		return "success";
	}
	
	
}
