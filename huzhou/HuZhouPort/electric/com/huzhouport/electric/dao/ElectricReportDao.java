package com.huzhouport.electric.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.electric.model.ElectricReport;
import com.huzhouport.electric.model.ElectricVisa;

public interface ElectricReportDao  extends BaseDao<ElectricReport> {
	
	//查询条数
	public int countPageElectricReportInfo(ElectricReport electricReport, String condition)throws Exception;
	
	//查询信息
	public List<?> searchElectricReportInfo(ElectricReport electricReport,String condition,String sequence,int startSet, int maxSet) throws Exception;
	
	//增加
	public String addElectricReportInfo(ElectricReport electricReport) throws Exception;
	
	//按ID
	public List<?> seeElectricReportID(ElectricReport electricReport)throws Exception;
	
	
	public int countPageElectricReportInfoAD(ElectricReport electricReport,
			String condition) throws Exception;

	public List<?> searchElectricReportInfoAD(ElectricReport electricReport,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception;
	public int countPageElectricReportInfoPublic(ElectricReport electricReport,
			String condition) throws Exception;

	public List<?> searchElectricReportInfoPublic(ElectricReport electricReport,
			String condition, String sequence, int startSet, int maxSet)
			throws Exception;
}
