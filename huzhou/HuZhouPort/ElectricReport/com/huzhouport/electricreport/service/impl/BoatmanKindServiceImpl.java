package com.huzhouport.electricreport.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.electricreport.dao.BoatmanKindDao;
import com.huzhouport.electricreport.model.BoatmanKind;
import com.huzhouport.electricreport.service.BoatmanKindService;

public class BoatmanKindServiceImpl extends BaseServiceImpl<BoatmanKind> implements BoatmanKindService{
	private BoatmanKindDao boatmanKindDao;
	public void setBoatmanKindDao(BoatmanKindDao boatmanKindDao) {
		this.boatmanKindDao = boatmanKindDao;
	}
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	public void setQcs(QueryConditionSentence qcs) {
		this.qcs = qcs;
	}
	// 列表
	public List<?> ShowBoatmanKindList(BoatmanKind bk) throws Exception{
		return this.boatmanKindDao.ShowBoatmanKindList(bk);
	}
	// 删除
	public boolean DeleteBoatmanKind(BoatmanKind bd) throws Exception{
		return this.boatmanKindDao.DeleteBoatmanKind(bd);
	}
	// 修改
	public boolean UpdateBoatmanKind(BoatmanKind bd) throws Exception{
		return this.boatmanKindDao.UpdateBoatmanKind(bd);
	}
	//新增
	public Boolean AddBoatmanKind(BoatmanKind bd) throws Exception{
		return this.boatmanKindDao.AddBoatmanKind(bd);
	}
	//分页
	public Map<String, Integer> CountBoatmanKindListByPage(QueryCondition queryCondition,Integer pageSize) throws Exception{
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.boatmanKindDao.CountBoatmanKindListByPage(qcs.QCS(queryCondition));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}
	public List<?> ShowBoatmanKindListByPage(QueryCondition queryCondition,Integer pageNo,Integer pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.boatmanKindDao.ShowBoatmanKindListByPage(qcs.QCS(queryCondition),
				qcs.Sequence(queryCondition), beginIndex,
				pageSize);
	}
}
