package com.huzhouport.statistic.service;

import java.io.InputStream;
import java.util.List;

import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;

public interface StatisticService {
	// 违章excel
	public InputStream IllegalReport(ReportModel reportModel) throws Exception;

	// 违章数据
	public List<?> IllegalReportDate(ReportModel reportModel) throws Exception;

	// 违章图片
	public InputStream IllegalImage(ReportModel reportModel) throws Exception;

	// 船舶电子报港年份
	public List<?> ElectByYear() throws Exception;

	// 船舶电子报港excel
	public InputStream ElectReport(ReportModel reportModel) throws Exception;

	// 船舶电子报港数据
	public List<?> ElectReportDate(ReportModel reportModel) throws Exception;

	// 船舶电子报港图片
	public InputStream ElectImage(ReportModel reportModel) throws Exception;

	// 定期签证年份
	public List<?> FixByYear() throws Exception;

	// 定期签证excel
	public InputStream FixReport(ReportModel reportModel) throws Exception;

	// 定期签证数据
	public List<?> DateOfFixReport(ReportModel reportModel) throws Exception;

	// 定期签证图片
	public InputStream FixImage(ReportModel reportModel) throws Exception;

	// 考勤管理时间段
	public List<?> LeaveByTime() throws Exception;

	// 考勤管理excel
	public InputStream LeaveReport(ReportModel reportModel,int userId,String userName) throws Exception;

	// 部门考勤表
	public InputStream LeaveReportByDepartment(DepartmentReport departmentReport) throws Exception;
	
	// 考勤管理数据
	public List<?> DateOfLeaveReport(ReportModel reportModel,int userId,String userName) throws Exception;
	
	// 考勤管理部门
	public DepartmentReport DateOfLeaveReportByDepartment(DepartmentReport departmentReport) throws Exception;

	// 考勤管理图片
	public InputStream LeaveImage(ReportModel reportModel,int userId,String username) throws Exception;
	
	//部门考勤表图片
	public InputStream LeaveImageByDepartment(DepartmentReport departmentReport) throws Exception;
	
	// 打印
	public List<?> printPersonData(ReportModel reportModel) throws Exception;

	// 油耗数据
	public List<?> DateOfDraftReport(ReportModel reportModel) throws Exception;

	// 油耗的船舶列表
	public List<?> DraftByBoat() throws Exception;

	// 油耗excel
	public InputStream DraftReport(ReportModel reportModel) throws Exception;

	// 油耗图片
	public InputStream DraftImage(ReportModel reportModel) throws Exception;

}
