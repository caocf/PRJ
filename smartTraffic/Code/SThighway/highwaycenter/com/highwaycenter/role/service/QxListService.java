package com.highwaycenter.role.service;

import java.util.List;

import com.common.action.BaseResult;
import com.highwaycenter.bean.TreeNodes;
//xiugai26
public interface QxListService {

	/**
	 * 版本2基本权限列表：新增或编辑权限组的基本权限列表
	 * @param qxzbh
	 * @return
	 */
	public BaseResult findjbqxlist(Integer qxzbh);
	
	/**
	 * 版本2权限组列表：权限组列表
	 */
	public BaseResult findQxzlist(int hasjbqx,int jsbh);
	
	
	/**
	 * 版本2权限组列表：权限组列表(带分页)
	 */
	public BaseResult findQxzlist(int hasjbqx,int page,int rows);
	/**
	 * 版本2权限组列表接口3：
	 * @param qxzbh
	 * @return
	 */
	public BaseResult zqxListByFqxz(Integer qxzbh);
	
	/**
	 *版本2： 权限组权限联动
	 * @param fqxbh
	 * @return
	 */
	public BaseResult selectZqxByFqxz(int fqxbh);
	
	

}
