package com.huzhouport.attendace.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.dao.LocationDao;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.attendace.service.LocationService;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;

public class LocationServiceImpl extends BaseServiceImpl<Location> implements LocationService{
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private LocationDao locationDao;

	
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public Map<String, Integer> countPageLocationInfo(Location location,
			int pageSize) throws Exception {
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.locationDao.countPageLocationInfo(location, qcs
				.QCS(location.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchLocationInfo(Location location, int pageNo,
			int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.locationDao.searchLocationInfo(location, qcs.QCS(location
				.getQueryCondition()), qcs.Sequence(location
				.getQueryCondition()), beginIndex, pageSize);
	}

}
