package com.huzhouport.statistic.dao;

import java.util.List;

import com.huzhouport.illegal.model.IllegalReason;
import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;

public interface StatisticDao {
	// ---------------------违章取证----------------------------
	public List<IllegalReason> CountReason() throws Exception;

	public List<Object[]> CountIllegal(ReportModel reportModel)
			throws Exception;

	// ----------------------船舶航行电子报告----------------------------
	public List<?> ElectByYear() throws Exception;

	public int CountElectByQuarter(int quarter, ReportModel reportModel)
			throws Exception;

	public int CountElectByMonth(int month, ReportModel reportModel)
			throws Exception;

	// ----------------------定期签证----------------------------
	public List<?> FixByYear() throws Exception;

	public int CountFixByQuarter(int quarter, ReportModel reportModel)
			throws Exception;

	public int CountFixByMonth(int month, ReportModel reportModel)
			throws Exception;

	// ----------------------考勤管理----------------------------
	public List<?> LeaveByPartTime() throws Exception;

	public List<Object[]> CountLeaveDate(ReportModel reportModel,int userId) throws Exception;
	public List<Object[]> CountLeaveDateByDepartment(DepartmentReport departmentReport)throws Exception;
	
	// 打印
	public List<?> printPersonData(ReportModel reportModel) throws Exception;

	// ----------------------油耗----------------------------
	// 油耗的船舶列表
	public List<?> DraftByBoat() throws Exception;

	public List<?> CountDraftDate(ReportModel reportModel) throws Exception;
}
