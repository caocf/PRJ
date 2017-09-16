package com.huzhouport.apply.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.apply.dao.ApplyDao;
import com.huzhouport.apply.model.Apply;
import com.huzhouport.apply.service.ApplyService;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;

public class ApplyServiceImpl extends BaseServiceImpl<Apply> implements ApplyService{
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private ApplyDao applyDao;

	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	public String addApplyInfo(Apply apply) throws Exception {
		this.applyDao.addApplyInfo(apply);
		return null;
	}

	public Map<String, Integer> countPageApplyInfo(Apply apply, int pageSize)
			throws Exception {
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.applyDao.countPageApplyInfo(apply, qcs
				.QCS(apply.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchApplyInfo(Apply apply, int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.applyDao.searchApplyInfo(apply, qcs.QCS(apply
				.getQueryCondition()), qcs.Sequence(apply
				.getQueryCondition()), beginIndex, pageSize);
	}

	public List<?> seeApplyID(Apply apply) throws Exception {
		
		return this.applyDao.seeApplyID(apply);
	}

}
