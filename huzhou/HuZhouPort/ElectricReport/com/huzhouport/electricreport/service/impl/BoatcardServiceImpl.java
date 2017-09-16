package com.huzhouport.electricreport.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.electricreport.dao.BoatcardDao;
import com.huzhouport.electricreport.dao.BoatmanKindDao;
import com.huzhouport.electricreport.model.BoatmanKind;
import com.huzhouport.electricreport.model.Certificate;
import com.huzhouport.electricreport.service.BoatcardService;
import com.huzhouport.electricreport.service.BoatmanKindService;

public class BoatcardServiceImpl extends BaseServiceImpl<Certificate> implements BoatcardService{
	private BoatcardDao boatcardDao;
	
	public void setBoatcardDao(BoatcardDao boatcardDao) {
		this.boatcardDao = boatcardDao;
	}
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	public void setQcs(QueryConditionSentence qcs) {
		this.qcs = qcs;
	}
	// 删除
	public boolean DeleteBoatCard(Certificate certificate) throws Exception{
		return this.boatcardDao.DeleteBoatCard(certificate);
	}
	// 修改
	public boolean UpdateBoatCard(Certificate certificate) throws Exception{
		return this.boatcardDao.UpdateBoatCard(certificate);
	}
	//新增
	public boolean AddBoatCard(Certificate certificate) throws Exception{
		return this.boatcardDao.AddBoatCard(certificate);
	}
	//分页
	public Map<String, Integer> CountBoatcardListByPage(Certificate certificate,Integer pageSize) throws Exception{
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.boatcardDao.CountBoatcardListByPage(certificate);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}
	public List<?> ShowBoatcardListByPage(Certificate certificate,Integer pageNo,Integer pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.boatcardDao.ShowBoatcardListByPage(certificate, beginIndex,
				pageSize);
	}
	public List<?> ShowBoatcardList(Certificate certificate) throws Exception {
		// TODO Auto-generated method stub
		return this.boatcardDao.ShowBoatcardList(certificate);
	}
}
