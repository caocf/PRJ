package com.highwaycenter.glz.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.HdjcxjcBean;
import com.highwaycenter.bean.HdjcxjcmxBean;
import com.highwaycenter.bean.QljcxjcBean;
import com.highwaycenter.bean.QljcxjcmxBean;
import com.highwaycenter.bean.selectListBean;

public interface XcHdjcxjcDao {
	/**
	 * 根据公路站ID,涵洞Id,搜索字段来查询列表
	 * @param page
	 * @param rows
	 * @param stationId
	 * @param bridgeId
	 * @param selectvalue
	 * @return
	 */
	public BaseQueryRecords selectHdjcxjcList(Integer page,Integer rows,String stationId,String culverId,String selectvalue);
	
/**
 * 根据id查询详情
 * @param id
 * @return
 */
	public HdjcxjcBean selectHdjcxjcXq(String id);

	/**
	 * 根据checkId查询明细列表
	 * @param checkId
	 * @return
	 */
	public List<HdjcxjcmxBean> selecHdjcxjcmxXq(String checkId);
	
	/**
	 * 查询某一属性列表以作检索
	 * @param key
	 * @return
	 */
	public List<Object[]> selectPropertyList(String key1,String key2);
	
	/**
	 * 查询涵洞名字列表
	 * @return
	 */
	public List<selectListBean> selectHdCodeList();

}
