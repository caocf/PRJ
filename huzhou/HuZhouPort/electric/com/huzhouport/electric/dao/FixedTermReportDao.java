package com.huzhouport.electric.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.electric.model.FixedTermReport;

public interface FixedTermReportDao extends BaseDao<FixedTermReport> {

	// 查询条数
	public int countPageFixedTermReportInfo(FixedTermReport fixedTermReport,
			String condition) throws Exception;

	// 查询信息
	public List<?> searchFixedTermReportInfo(FixedTermReport fixedTermReport,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception;

	// 增加
	public String addFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception;

	// 删除
	public String deleteaddFixedTermReportInfo(FixedTermReport fixedTermReport)
			throws Exception;
//删除明细
	public String deleteaddRegularVisaInfo(FixedTermReport fixedTermReport)
	throws Exception;

	public List<?> findRegularVisaByIdCodition(FixedTermReport fixedTermReport)
			throws Exception;
	//获取wbservice并增加
	public boolean AddFixItemByWb(List<FixedTermReport> list)throws Exception;
}
