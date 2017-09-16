package com.huzhouport.electricreport.dao;

import java.util.List;
import java.util.Map;

import com.huzhouport.electricreport.model.Certificate;



public interface BoatcardDao {
	//证书列表
	public List<?> ShowBoatcardList(Certificate certificate) throws Exception;
	// 删除
	public boolean DeleteBoatCard(Certificate certificate) throws Exception;
	// 修改
	public boolean UpdateBoatCard(Certificate certificate) throws Exception;
	// 新增
	public boolean AddBoatCard(Certificate certificate) throws Exception;
	//分页
	public int CountBoatcardListByPage(Certificate certificate) throws Exception;
	public List<?> ShowBoatcardListByPage(Certificate certificate,int startSet, int maxSet) throws Exception;
}
