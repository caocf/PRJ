package com.highwaycenter.gljg.service;

import java.util.List;

import com.common.action.BaseResult;
import com.highwaycenter.bean.TreeNodes;

public interface GljgListService {
	/**
	 * 获取所有根节点的管理机构
	 */
	public BaseResult selectFgljgAll();
	
	/**
	 * 根据父节点机构获取所有子节点的管理机构
	 */
	public BaseResult selectZgljgByFjg(String fjgbh);
	
	/**
	 * 根据机构获取所有部门信息
	 */
	public BaseResult selectBmByJg(String fjgbh);
	

	/**
	 * 根据部门获取人员列表信息,value可以传递拼音首字母做检索
	 */
	public BaseResult selectRyByBm(String bmdm, String gljgdm,int page, int rows,String xmpyszm,String selectvalue);
	
	/**
	 * 获取所有机构的树结构
	 */
	public BaseResult selectjgAll(String sfxzgljg);
	
	
	/**
	 * 根据机构编号获取详细信息
	 * 
	 */
	public BaseResult selectJgInfo(String fjgbh);
	
	
	public void diguiFjg(String fjbdm, TreeNodes fjgNode);
	
	/**
	 * 根据机构获取一级下级部门
	 */
	public BaseResult selectOrderList(String bmdm,String jgdm);
	
	/**
	 * 根据机构获取人员
	 */
	public BaseResult selectRyOrderList(String bmdm);
	
	/**
	 * 更新人员排序字段
	 */
	public BaseResult updateRyOrderList(List<String> orderlist);
	
	/**
	 * 更新部门排序字段
	 */
	public BaseResult updateOrderList(List<String> orderlist);
	
	
	
	/**
	 * 获取部门信息
	 */
	public BaseResult selectBmXq(String bmdm);
	
	/**
	 * 获取全部管理机构信息
	 */
	public BaseResult selectGljgList();
	
	/**
	 * 获取行政处罚的管理机构信息
	 * 
	 */
	public BaseResult selectXzcfGljg();
} 


