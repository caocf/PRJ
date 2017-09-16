package com.huzhouport.electricreport.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.electricreport.model.Certificate;

public interface BoatcardService {
	//证书列表
	public List<?> ShowBoatcardList(Certificate certificate) throws Exception;
	// 删除
	public boolean DeleteBoatCard(Certificate certificate) throws Exception;
	// 修改
	public boolean UpdateBoatCard(Certificate certificate) throws Exception;
	// 新增
	public boolean AddBoatCard(Certificate certificate) throws Exception;
	//分页
	public Map<String, Integer> CountBoatcardListByPage(Certificate certificate,Integer pageSize) throws Exception;
	public List<?> ShowBoatcardListByPage(Certificate certificate,Integer pageNo,Integer pageSize) throws Exception;
}
