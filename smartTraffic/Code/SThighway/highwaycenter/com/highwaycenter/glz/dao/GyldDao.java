package com.highwaycenter.glz.dao;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.glz.model.HGlzGlzGyld;

public interface GyldDao {
	/**
	 * 查询公路站概况列表
	 * @param page
	 * @param rows
	 * @param stationId  公路站id
	 * @param selectvalue
	 * @return
	 */
	public BaseQueryRecords selectGyldgkList(Integer page, Integer rows, String stationId, String selectvalue);
	
	
	/**
	 * 公路站详情
	 * @param id
	 * @return
	 */
	public HGlzGlzGyld selectGyldxq(String id);
	
}
