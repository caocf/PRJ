package com.huzhouport.statistic.action;

import java.util.List;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;
import com.huzhouport.statistic.service.StatisticService;

public class StatisticAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private StatisticService statisticService;
	private ReportModel reportModel = new ReportModel();
	private DepartmentReport departmentReport=new DepartmentReport();
	private List<?> list;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public ReportModel getReportModel() {
		return reportModel;
	}

	public void setReportModel(ReportModel reportModel) {
		this.reportModel = reportModel;
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public DepartmentReport getDepartmentReport() {
		return departmentReport;
	}

	public void setDepartmentReport(DepartmentReport departmentReport) {
		this.departmentReport = departmentReport;
	}

	// 违章报表
	public String IllegalReport() {

		try {
			list = this.statisticService.IllegalReportDate(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 电子报表
	public String ElectReport() {
		try {
			list = this.statisticService.ElectReportDate(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String ElectByYear() {
		try {
			list = this.statisticService.ElectByYear();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;

	}

	// 定期签证
	public String DateOfFixReport() {
		try {
			list = this.statisticService.DateOfFixReport(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String FixByYear() {
		try {
			list = this.statisticService.FixByYear();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;

	}

	// 考勤管理
	public String DateOfLeaveReport() {
		try {
			if(session.get("name")!=null){
			list = this.statisticService.DateOfLeaveReport(reportModel,Integer.parseInt(session.get("userId").toString()),(String)session.get("name"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}
	// 考勤管理部门
	public String DateOfLeaveReportByDepartment() {
		try {
			departmentReport = this.statisticService.DateOfLeaveReportByDepartment(departmentReport);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String LeaveByTime() {
		try {
			list = this.statisticService.LeaveByTime();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;

	}

	// 打印
	public String printPersonData() throws Exception {
		list = this.statisticService.printPersonData(reportModel);
		return SUCCESS;
	}

	// 油耗
	public String DateOfDraftReport() {
		try {
			list = this.statisticService.DateOfDraftReport(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}
	// 油耗的船舶列表
	public String DraftByBoat() {
		try {
			list = this.statisticService.DraftByBoat();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;

	}
}
