package com.highwaycenter.role.service;

import java.util.List;

import com.common.action.BaseResult;
import com.highwaycenter.role.model.HJcJbjsb;

public interface JsqxService {
	/**
	 * 保存角色
	 * @param js
	 * @param qxjh
	 * @return
	 */
	public BaseResult jsSave(HJcJbjsb js,List<Integer> qxjh);
	/**
	 * 编辑角色
	 * @param js
	 * @param qxjh
	 * @return
	 */
	public BaseResult jsUpdate(HJcJbjsb js,List<Integer> qxjh);
	/**
	 * 删除角色
	 * @param jsbh
	 * @return
	 */
	public BaseResult jsDelete(int jsbh);
	/**
	 * 角色列表及权限信息
	 * @param page
	 * @param rows
	 * @return
	 */
	public BaseResult jsListAll(int page,int rows);
	/**
	 * 获取角色下拉列表
	 * @return
	 */
	public BaseResult jslist();
	
	/**
	 * 批量删除角色 undo
	 */
	public BaseResult jsAllDelete(String jss);
}
