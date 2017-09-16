package com.huzhouport.electric.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.electric.dao.RegularVisaDao;
import com.huzhouport.electric.model.RegularVisa;
import com.huzhouport.electric.service.RegularVisaService;

public class RegularVisaServiceImpl extends BaseServiceImpl<RegularVisa> implements RegularVisaService{
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private RegularVisaDao regularVisaDao;
	
	public void setRegularVisaDao(RegularVisaDao regularVisaDao) {
		this.regularVisaDao = regularVisaDao;
	}


	public String addRegularVisaInfo(RegularVisa regularVisa) throws Exception {
		regularVisa.setReportTime(new Date());
		this.regularVisaDao.addRegularVisaInfo(regularVisa);
		return null;
	}


	public List<?> seeRegularVisaID(RegularVisa regularVisa) throws Exception {
		return this.regularVisaDao.seeRegularVisaID(regularVisa);
	}


	public Map<String, Integer> countPageRegularVisaInfo(
			RegularVisa regularVisa, int pageSize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.regularVisaDao.countPageRegularVisaInfo(
				regularVisa, qcs.QCS(regularVisa.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}


	public List<?> searchRegularVisaInfo(RegularVisa regularVisa, int pageNo,
			int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.regularVisaDao.searchRegularVisaInfo(regularVisa,
				qcs.QCS(regularVisa.getQueryCondition()), qcs
						.Sequence(regularVisa.getQueryCondition()),
				beginIndex, pageSize);
	}

}
