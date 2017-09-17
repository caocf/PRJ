package com.sts.liveness.service;

import java.util.List;
import java.util.Map;

import com.sts.liveness.dao.LivenessDao;
import com.sts.liveness.model.Liveness;


public class LivenessService {

	LivenessDao livenessDao;
	public void setLivenessDao(LivenessDao livenessDao) {
		this.livenessDao = livenessDao;
	}
	
	public void save(Liveness liveness)
	{
		this.livenessDao.save(liveness);
	}
	
	public void update(Liveness liveness)
	{
		this.livenessDao.update(liveness);
	}
	
	public List<Liveness> findByImei(String imei,String date)
	{
		return this.livenessDao.findByImei(imei, date);
	}
	
	public List<Map<String, Object>> queryLivenessCount(String start,String end) {
		return this.livenessDao.queryLivenessCount(start, end);
	}
}
