package com.huzhouport.electric.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.electric.dao.FixedTermReportDao;
import com.huzhouport.electric.model.FixedTermReport;
import com.huzhouport.electric.service.FixedTermReportService;

public class FixedTermReportServiceImpl extends BaseServiceImpl<FixedTermReport> implements FixedTermReportService{
	
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private FixedTermReportDao fixedTermReportDao;
	

	public void setFixedTermReportDao(FixedTermReportDao fixedTermReportDao) {
		this.fixedTermReportDao = fixedTermReportDao;
	}

	public String addFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception {
		this.fixedTermReportDao.addFixedTermReportInfo(fixedTermReport);
		return null;
	}

	public Map<String, Integer> countPageFixedTermReportInfo(
			FixedTermReport fixedTermReport, int pageSize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.fixedTermReportDao.countPageFixedTermReportInfo(
				fixedTermReport, qcs.QCS(fixedTermReport.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchFixedTermReportInfo(FixedTermReport fixedTermReport,
			int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.fixedTermReportDao.searchFixedTermReportInfo(fixedTermReport, qcs
				.QCS(fixedTermReport.getQueryCondition()), qcs
				.Sequence(fixedTermReport.getQueryCondition()), beginIndex,
				pageSize);
	}

	public String deleteFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception {
		this.fixedTermReportDao.deleteaddRegularVisaInfo(fixedTermReport);
		this.fixedTermReportDao.deleteaddFixedTermReportInfo(fixedTermReport);
		
		return null;
	}

	public List<?> findRegularVisaByIdCodition(FixedTermReport fixedTermReport)
			throws Exception {
		
		return this.fixedTermReportDao.findRegularVisaByIdCodition(fixedTermReport);
	}
	//获取wbservice并增加
	public boolean AddFixItemByWb(List<FixedTermReport> list)throws Exception{
		return this.fixedTermReportDao.AddFixItemByWb(list);
	}
}
