package com.huzhouport.electricreport.dao;

import java.util.List;

import com.huzhouport.electricreport.model.BoatmanKind;



public interface BoatmanKindDao {
	// 数据字典列表
	public List<?> ShowBoatmanKindList(BoatmanKind bk) throws Exception;
	// 删除
	public boolean DeleteBoatmanKind(BoatmanKind bk) throws Exception;
	// 修改
	public boolean UpdateBoatmanKind(BoatmanKind bk) throws Exception;
	// 新增
	public boolean AddBoatmanKind(BoatmanKind bk) throws Exception;
	//查询条数
	public int CountBoatmanKindListByPage(String condition)throws Exception;
	//查询本页
	public List<?> ShowBoatmanKindListByPage(String condition,String sequence,int startSet, int maxSet) throws Exception;
}
