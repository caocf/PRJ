package com.huzhouport.electric.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.electric.model.FixedTermReport;

public interface FixedTermReportService extends BaseService<FixedTermReport> {

	// 增加
	public String addFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception;

	// 查询信息
	public List<?> searchFixedTermReportInfo(FixedTermReport fixedTermReport,
			int pageNo, int pageSize) throws Exception;

	// 查询条数
	public Map<String, Integer> countPageFixedTermReportInfo(
			FixedTermReport fixedTermReport, int pageSize) throws Exception;

	// 删除
	public String deleteFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception;

	public List<?> findRegularVisaByIdCodition(FixedTermReport fixedTermReport)
			throws Exception;
	//获取wbservice并增加
	public boolean AddFixItemByWb(List<FixedTermReport> list)throws Exception;
}
