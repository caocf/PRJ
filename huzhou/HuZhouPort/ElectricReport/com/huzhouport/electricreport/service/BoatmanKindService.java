package com.huzhouport.electricreport.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.electricreport.model.BoatmanKind;

public interface BoatmanKindService {
	// 数据字典列表
	public List<?> ShowBoatmanKindList(BoatmanKind bk) throws Exception;
	// 删除
	public boolean DeleteBoatmanKind(BoatmanKind bk) throws Exception;
	// 修改
	public boolean UpdateBoatmanKind(BoatmanKind bk) throws Exception;
	// 新增
	public Boolean AddBoatmanKind(BoatmanKind bk) throws Exception;
	//分页
	public Map<String, Integer> CountBoatmanKindListByPage(QueryCondition queryCondition,Integer pageSize) throws Exception;
	public List<?> ShowBoatmanKindListByPage(QueryCondition queryCondition,Integer pageNo,Integer pageSize) throws Exception;
}
