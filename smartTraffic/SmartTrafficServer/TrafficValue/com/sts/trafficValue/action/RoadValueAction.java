package com.sts.trafficValue.action;

import com.sts.trafficValue.service.TrafficValueService;

public class RoadValueAction {
	private TrafficValueService trafficValueService;

	public void setTrafficValueService(TrafficValueService trafficValueService) {
		this.trafficValueService = trafficValueService;
	}
	private String result;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}



	// 1.查询各时间点道路中度（重度）拥堵路段
	public String queryMergedConRticByTime() {
		result = this.trafficValueService.queryMergedConRticByTime();

		return "success";
	}

}
