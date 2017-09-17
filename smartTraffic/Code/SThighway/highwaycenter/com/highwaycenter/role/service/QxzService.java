package com.highwaycenter.role.service;

import java.util.List;

import com.common.action.BaseResult;
import com.highwaycenter.role.model.HJcQxz;
import com.highwaycenter.role.model.HJcQxzqxdy;

public interface QxzService {
	/**
	 * 新增权限组
	 * @param qxz
	 * @param qxjh
	 * @return
	 */
	public BaseResult qxzSave(HJcQxz qxz,List<Integer> qxjh);
	/**
	 * 删除权限组
	 * @param qxzbh
	 * @return
	 */
	public BaseResult qxzDelete(int qxzbh);
	/**
	 * 编辑权限组
	 * @param qxzbh
	 * @param qxjh
	 * @return
	 */
	public BaseResult qxToQxzSave(HJcQxz qxzbh, List<Integer> qxjh);



}
