package com.SmartTraffic.multiple.action;

import java.util.List;

import javax.annotation.Resource;

import com.SmartTraffic.multiple.service.impl.MultipleService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

public class MultipleAction extends BaseAction{
	
	private static final long serialVersionUID = 2859688009174203624L;
	private int jtlid;
	
	@Resource(name="multipleService")
	private MultipleService multipleService;
    private BaseResult result;
    private String gcdid;

	public String showGcdInfo() {
		result = this.multipleService.showGcdInfo();
		return "success";
	}

	public String showTrafficByGcd() {
		result = this.multipleService.showTrafficByGcd(gcdid);
		return "success";
	}

	public String selectTaxiInfo(){
		this.result = multipleService.selectTaxiInfo();
		return "success";
	}

	public String selectShipInfo(){
		this.result = multipleService.selectShipInfo();	
		return "success";
	}
	
	
	public String selectTaxiData(){
		this.result = multipleService.selectTaxiData();
		return "success";
	}
	
	public BaseResult getResult() {
		return result;
	}
	
	public String selectNumById(){
		this.result = this.multipleService.selectNumById(jtlid);
		return "success";
	}
	
	//jd
	public String selectJdInfo(){
		this.result = this.multipleService.selectJdInfo();
		return "success";
	}

	public void setJtlid(int jtlid) {
		this.jtlid = jtlid;
	}

	public void setGcdid(String gcdid) {
		this.gcdid = gcdid;
	}


	
	
	
}
