package com.sts.breakRules.action;

import java.util.List;

import com.sts.breakRules.model.BreakRulesRecord;
import com.sts.breakRules.service.BreakRulesService;

public class BreakRulesAction 
{
	private String carID;
	private String engineID;
	private String frameID;
	private int page;
	private int num;
	
	BreakRulesService breakRulesService;	
	private List<BreakRulesRecord> records;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNum() {
		return num;
	}
	
	public String getEngineID() {
		return engineID;
	}

	public void setEngineID(String engineID) {
		this.engineID = engineID;
	}

	public String getFrameID() {
		return frameID;
	}

	public void setFrameID(String frameID) {
		this.frameID = frameID;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<BreakRulesRecord> getRecords() {
		return records;
	}

	public void setRecords(List<BreakRulesRecord> records) {
		this.records = records;
	}

	public void setBreakRulesService(BreakRulesService breakRulesService) {
		this.breakRulesService = breakRulesService;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}
	
	
	public String GetBreakRulesRecord()
	{
		if(carID.length()>0)
		{
			records=breakRulesService.GetRecords(carID,engineID,frameID);
		}
		
		return "success";
	}
}
