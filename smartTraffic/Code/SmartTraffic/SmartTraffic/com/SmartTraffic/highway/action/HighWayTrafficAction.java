package com.SmartTraffic.highway.action;

import javax.annotation.Resource;

import com.SmartTraffic.highway.service.HighWayTrafficService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

public class HighWayTrafficAction  extends BaseAction{

	private static final long serialVersionUID = 8383778154483542971L;
	private BaseResult result;
	@Resource(name = "hwtrafficService")
	private HighWayTrafficService hwtrafficService;
	private String lxdm;//查询的路线代码
	private String lxjc;//路线简称
	

	/**
	 * 实时拥挤度查询
	 * @return
	 */
	public String selectTransDataTimeCS() {
		this.result = hwtrafficService.selectTransDataTimeCS();
		return "success";
	}

	/**
	 * 查询观测站列表信息
	 * @return
	 */
	public String selectGczInfo(){
		this.result = hwtrafficService.selectGczInfo();
		return "success";
	}
	
	/**
	 * 查询交调信息的国省道路线列表
	 * @return
	 */
	public String selectJdlx(){
		this.result = hwtrafficService.selectJdlx();
		return "success";
		
	}
	
	/**
	 * 根据路线代码查询流量数据（全部放在后台查询速度太慢，作废）
	 * @return
	 */
	public String selectJdInfo(){
		//this.result = this.hwtrafficService.selectJdInfo(lxdm,lxjc);
		return "success";
	}
	
	public String selectDayData(){
		this.result = this.hwtrafficService.selectDayData(lxdm);
		return "success";
	}
	
	public String selectMonData(){
		this.result = this.hwtrafficService.selectMonData(lxdm);
		return "success";
	}
	
	public String selectYearData(){
		this.result = this.hwtrafficService.selectYearData(lxdm);
		return "success";
	}
	
	
	
	public BaseResult getResult() {
		return result;
	}

	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}

	public String getLxjc() {
		return lxjc;
	}

	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}
     
	
	
	
}
