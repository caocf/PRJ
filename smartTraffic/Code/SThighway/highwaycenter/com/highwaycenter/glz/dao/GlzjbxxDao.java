package com.highwaycenter.glz.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.highwaycenter.glz.model.HGlzGlzJbxx;

public interface GlzjbxxDao {
	
	/**
	 * 查询所有管理站基本信息列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectList(Integer page,Integer rows);
	
	
	/**
	 * 查询某一属性列表以作检索
	 * @param key
	 * @return
	 */
	public BaseQueryRecords selectPropertyList(String key);
	
	/**
	 * 根据Hql检索
	 * @param hql
	 * @return
	 */
	public BaseQueryRecords selectByHql(HQL hql);
	
	/**
	 * 根据hql分页检索
	 * @param hql
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectByHql(HQL hql,Integer page,Integer rows);
	

	/**
	 * 查询某一属性列表以作检索
	 * @param key
	 * @return
	 */
	public List<Object[]> selectPropertyList(String key1,String key2);

	public HGlzGlzJbxx selectUnique(String stationId);
	
	public String selectGlzName(String stationId);
	
	public String selectGljgmc(String stationId);
}
