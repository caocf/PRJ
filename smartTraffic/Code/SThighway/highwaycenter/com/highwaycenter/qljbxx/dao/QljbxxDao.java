package com.highwaycenter.qljbxx.dao;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.highwaycenter.qljbxx.model.HJcQljbxx;

public interface QljbxxDao {
	/**
	 * 根据标识编码获取桥梁详细信息
	 * @param bzbm
	 * @return
	 */
	public HJcQljbxx selectByBzbm( String bzbm);
	

	/**
	 * 获取桥梁详情列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectXqList(Integer page,Integer rows);
	
	

	/**
	 * 获取桥梁概况列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectGkList(Integer page,Integer rows,Integer xzqhdm,String selectvalue,String selectId);
	
	
	/**
	 * 获取某个属性集合做下拉框检索
	 * @param key
	 * @return
	 */
	public BaseQueryRecords selectPropertyList(String key);
	
	
	/**
	 * hql检索
	 * @param hql
	 * @return
	 */
	public BaseQueryRecords selectByHql(HQL hql);
	
	/**
	 * hql检索
	 * @param hql
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectByHql(HQL hql,Integer page,Integer rows);
	
	
	
	/**
	 * 获取桥梁概况列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectGkListByCondition(Integer page,Integer rows,String condition);
	

}
