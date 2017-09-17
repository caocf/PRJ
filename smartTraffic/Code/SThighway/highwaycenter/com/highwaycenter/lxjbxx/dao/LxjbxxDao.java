package com.highwaycenter.lxjbxx.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.highwaycenter.lxjbxx.model.HJcLxjbxx;


public interface LxjbxxDao {
	/**
	 * 根据bzbm 获取路线详情
	 * @param bzbm
	 * @return
	 */
	public HJcLxjbxx selectByBzbm( String bzbm);
	/**
	 * 路线详情列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectXqList(Integer page,Integer rows);
	
	/**
	 * 路线概况列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseQueryRecords selectGkList(Integer page,Integer rows,Integer xzqhdm,String selectvalue,String selectId);
	
	
	/**
	 * 查询某一属性列表以作检索
	 * @param key
	 * @return
	 */
	public List<Object[]> selectPropertyList(String key1,String key2);
	
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
	 * 根据路线代码获取路线简称
	 * @param lxdm
	 * @return
	 */
	public String selectLxjc(String lxdm);
	
	/**
	 * 根据条件搜索路线概况列表
	 */
	public BaseQueryRecords selectGkListByCondition(Integer page,Integer rows,String condition);
	
	/**
	 * 搜索扩展内容
	 */
	public String selectKznr(int kzbh);
	
	/**
	 * 编辑扩展内容
	 */
     public void editKznr(int kzbh,String kznr);
	
}
