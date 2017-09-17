package com.highwaycenter.glz.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.GlzJbxx;
import com.highwaycenter.glz.model.HGlzGlzXxxx;

public interface GlzxxxxDao {
	/**
	 * 查询公路站详情
	 * @param id
	 * @return
	 */
	public HGlzGlzXxxx selectGlzqx(String id);
	
	/**
	 *  查询公路站概况列表
	 * @param page
	 * @param rows
	 * @param stationId
	 * @param selectvalue
	 * @return
	 */
	public BaseQueryRecords selectGlzgkList(Integer page, Integer rows,
			String stationId, String selectvalue);
	
	
	
	public BaseQueryRecords selectGlzgkListNew(Integer page,Integer rows,String xzqhdm,String selectvalue,String selectId);
	
	/**
	 * 查询某一属性列表以作检索
	 * @param key
	 * @return
	 */
	public List<Object[]> selectPropertyList(String key1,String key2);

}
