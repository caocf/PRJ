package com.huzhouport.electric.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.electric.dao.ElectricReportDao;
import com.huzhouport.electric.model.ElectricReport;
import com.huzhouport.electric.service.ElectricReportService;

public class ElectricReportServiceImpl extends BaseServiceImpl<ElectricReport>
		implements ElectricReportService {
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private ElectricReportDao electricReportDao;

	public void setElectricReportDao(ElectricReportDao electricReportDao) {
		this.electricReportDao = electricReportDao;
	}

	public String addElectricReportInfo(ElectricReport electricReport)
			throws Exception {
		electricReport.setReportTime(new Date());
		this.electricReportDao.addElectricReportInfo(electricReport);
		return null;
	}

	public Map<String, Integer> countPageElectricReportInfo(
			ElectricReport electricReport, int pageSize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.electricReportDao.countPageElectricReportInfo(
				electricReport, qcs.QCS(electricReport.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchElectricReportInfo(ElectricReport electricReport,
			int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.electricReportDao.searchElectricReportInfo(electricReport,
				qcs.QCS(electricReport.getQueryCondition()), qcs
						.Sequence(electricReport.getQueryCondition()),
				beginIndex, pageSize);
	}

	public List<?> seeElectricReportID(ElectricReport electricReport)
			throws Exception {

		return this.electricReportDao.seeElectricReportID(electricReport);
	}

	public int countPageElectricReportInfoAD(ElectricReport electricReport,
			int pageSize) throws Exception {
		int total = this.electricReportDao.countPageElectricReportInfoAD(
				electricReport, qcs.QCS(electricReport.getQueryCondition()));

		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		return pages;
	}

	public List<?> searchElectricReportInfoAD(ElectricReport electricReport,
			int pageNo, int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.electricReportDao.searchElectricReportInfoAD(
				electricReport, "", "", beginIndex, pageSize);
	}

	public int countPageElectricReportInfoPublic(ElectricReport electricReport,
			int pageSize) throws Exception {
		int total = this.electricReportDao.countPageElectricReportInfoPublic(
				electricReport, qcs.QCS(electricReport.getQueryCondition()));

		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		return pages;
	}

	public List<?> searchElectricReportInfoPublic(
			ElectricReport electricReport, int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.electricReportDao.searchElectricReportInfoPublic(
				electricReport, "", "", beginIndex, pageSize);
	}

}