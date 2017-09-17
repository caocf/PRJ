package com.highwaycenter.data.service;

import com.common.action.BaseResult;

public interface DataService {
	/*public BaseResult selectDataState(int page,int rows);*/
	
/*	public void readAndInsert();*/
	
	/**
	 * 查询用kettle工具跑的数据模块状态
	 * @param moduletype 1: 2
	 * @return
	 */
	public BaseResult selectCurrentKettleState();
	
	/**
	 * 查询模块详情
	 */
	public BaseResult selectModuleXq(String type,String time);
	
	/**
	 * 查询模块折线信息
	 */
	public BaseResult selectModuleLine(String moduletype,Integer xnumber);
	
	/**
	 * 查询某天开始的同步成功率
	 */
	public BaseResult selectSuccessRate(String starttime,String moduletype);

}
