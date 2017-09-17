package com.highwaycenter.glz.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.QljcxjcBean;
import com.highwaycenter.bean.QljcxjcmxBean;
import com.highwaycenter.bean.selectListBean;

public interface XcQljcxjcDao {
	/**
	 * 根据公路站ID,桥梁Id,搜索字段来查询列表
	 * @param page
	 * @param rows
	 * @param stationId
	 * @param bridgeId
	 * @param selectvalue
	 * @return
	 */
	public BaseQueryRecords selectQljcxjcList(Integer page,Integer rows,String stationId,String bridgeId,String selectvalue);
	
/**
 * 根据id查询详情
 * @param id
 * @return
 */
	public QljcxjcBean selectQljcxjcXq(String id);

	/**
	 * 根据checkId查询明细列表
	 * @param checkId
	 * @return
	 */
	public List<QljcxjcmxBean> selecQljcxjcmxXq(String checkId);
	
	/**
	 * 查询某一属性列表以作检索
	 * @param key
	 * @return
	 */
	public List<Object[]> selectPropertyList(String key1,String key2);
	
	
	/**
	 * 查询桥梁名字列表
	 * @return
	 */
	public List<selectListBean> selectQlNameList();

}
