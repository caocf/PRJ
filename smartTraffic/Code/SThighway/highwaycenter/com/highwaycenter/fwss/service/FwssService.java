package com.highwaycenter.fwss.service;

import com.common.action.BaseResult;
import com.highwaycenter.fwss.model.HFwFwq;
import com.highwaycenter.fwss.model.HFwSfz;

public interface FwssService {
	/**
	 * 联合查询服务区和收费站2张表
	 * @param page
	 * @param rows
	 * @param selectValue  查询字段（服务设施名称）
	 * @param fwssType     服务设施类别
	 * @return result
	 */
	public BaseResult selectUnion(int page,int rows,String selectValue,String fwssType);
	
	/**
	 * 查询服务设施详情
	 * @param fwssbh
	 * @param type 1：服务区 2.收费站
	 * @return result
	 */
	public BaseResult selectFwssXq(String fwssbh,int type);
	
	/**
	 * 删除服务设施
	 * @param fwssbh
	 * @param type 1：服务区 2.收费站
	 * @return  
	 */
	public BaseResult deleteFwss(String fwssbh,int type);
	
	/**
	 * 新增服务区
	 * @param fwq
	 * @return fwqbh
	 */
	public BaseResult saveFwq(HFwFwq fwq);
	
	/**
	 * 编辑服务区
	 * @param fwq
	 * @return 
	 */
	public BaseResult updateFwq(HFwFwq fwq);

	/**
	 * 新增收费站
	 * @param sfz
	 * @return
	 */
	public BaseResult saveSfz(HFwSfz sfz);
	
	/**
	 * 编辑收费站
	 * @param sfz
	 * @return
	 */
	public BaseResult updateSfz(HFwSfz sfz);
	
	/**
	 * 查询服务设施列表
	 * @param page
	 * @param rows
	 * @param selectValue  查询字段
	 * @param type  1：服务区 2.收费站
	 * @return
	 */
	public BaseResult selectFwssList(int page,int rows,String selectValue,int type,String selectId);

}
