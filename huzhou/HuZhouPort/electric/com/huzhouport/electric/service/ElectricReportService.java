package com.huzhouport.electric.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.electric.model.ElectricReport;

public interface ElectricReportService extends BaseService<ElectricReport> {

	// 增加
	public String addElectricReportInfo(ElectricReport electricReport)
			throws Exception;

	// 查询信息
	public List<?> searchElectricReportInfo(ElectricReport electricReport,
			int pageNo, int pageSize) throws Exception;

	// 查询条数
	public Map<String, Integer> countPageElectricReportInfo(
			ElectricReport electricReport, int pageSize) throws Exception;

	// 按ID
	public List<?> seeElectricReportID(ElectricReport electricReport)
			throws Exception;

	public int countPageElectricReportInfoAD(ElectricReport electricReport,
			int pageSize) throws Exception;

	public List<?> searchElectricReportInfoAD(ElectricReport electricReport,int pageNo, int pageSize)
			throws Exception;
	public int countPageElectricReportInfoPublic(ElectricReport electricReport,
			int pageSize) throws Exception;

	public List<?> searchElectricReportInfoPublic(ElectricReport electricReport,int pageNo, int pageSize)
			throws Exception;
}
