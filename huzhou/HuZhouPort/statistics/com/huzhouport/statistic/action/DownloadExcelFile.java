package com.huzhouport.statistic.action;

import java.io.InputStream;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;
import com.huzhouport.statistic.service.StatisticService;

public class DownloadExcelFile extends BaseAction {
	private static final long serialVersionUID = 1L;
	private ReportModel reportModel = new ReportModel();
	private DepartmentReport departmentReport = new DepartmentReport();
	private StatisticService statisticService;
	private InputStream illegalImage;

	public ReportModel getReportModel() {
		return reportModel;
	}

	public void setReportModel(ReportModel reportModel) {
		this.reportModel = reportModel;
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public void setIllegalImage(InputStream illegalImage) {
		this.illegalImage = illegalImage;
	}

	public DepartmentReport getDepartmentReport() {
		return departmentReport;
	}

	public void setDepartmentReport(DepartmentReport departmentReport) {
		this.departmentReport = departmentReport;
	}

	// 导出用户数据
	public String downloadIllegalExcel() {
		return SUCCESS;
	}

	public InputStream getDownloadIllegalExcel() {
		InputStream in = null;

		try {
			in = this.statisticService.IllegalReport(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return in;
	}

	// 导出违章图片
	public String illegalImage() {
		return SUCCESS;
	}

	public InputStream getIllegalImage() {
		try {
			illegalImage = this.statisticService.IllegalImage(reportModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return illegalImage;
	}
	// 导出电子报港图片
	public String electImage() {
		return SUCCESS;
	}

	public InputStream getElectImage() {
		try {
			illegalImage = this.statisticService.ElectImage(reportModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return illegalImage;
	}
	// 导出电子报港Excel
	public String downloadElectExcel() {
		return SUCCESS;
	}

	public InputStream getDownloadElectExcel() {
		InputStream in = null;

		try {
			in = this.statisticService.ElectReport(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return in;
	}
	
	// 导出定期签证图片
	public String fixImage() {
		return SUCCESS;
	}

	public InputStream getFixImage() {
		try {
			illegalImage = this.statisticService.FixImage(reportModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return illegalImage;
	}
	// 导出定期签证Excel
	public String downloadFixExcel() {
		return SUCCESS;
	}

	public InputStream getDownloadFixExcel() {
		InputStream in = null;

		try {
			in = this.statisticService.FixReport(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return in;
	}
	// 请假加班图片
	public String leaveImage() {
		return SUCCESS;
	}

	public InputStream getLeaveImage() {
		try {
			if(session.get("name")!=null){
				illegalImage = this.statisticService.LeaveImage(reportModel,Integer.parseInt(session.get("userId").toString()),(String)session.get("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return illegalImage;
	}
	//部门考勤表图片
	public String leaveImageByDepartment() {
		return SUCCESS;
	}

	public InputStream getLeaveImageByDepartment() {
		try {
			departmentReport.setDeparmentName(new String(departmentReport.getDeparmentName().getBytes("8859_1"),"UTF-8"));
			illegalImage = this.statisticService.LeaveImageByDepartment(departmentReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return illegalImage;
	}
	// 请假加班Excel
	public String downloadLeaveExcel() {
		return SUCCESS;
	}

	public InputStream getDownloadLeaveExcel() {
		InputStream in = null;

		try {
			if(session.get("name")!=null){
			in = this.statisticService.LeaveReport(reportModel,Integer.parseInt(session.get("userId").toString()),(String)session.get("name"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return in;
	}
	// 部门考勤表
	public String downloadLeaveByDepartment() {
		return SUCCESS;
	}

	public InputStream getDownloadLeaveByDepartment() {
		InputStream in = null;

		try {
			departmentReport.setDeparmentName(new String(departmentReport.getDeparmentName().getBytes("8859_1"),"UTF-8"));
			in = this.statisticService.LeaveReportByDepartment(departmentReport);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return in;
	}
	// 导出油耗图片
	public String draftImage() {
		return SUCCESS;
	}

	public InputStream getDraftImage() {
		try {
			reportModel.setOneName(new String(reportModel.getOneName().getBytes("8859_1"),"UTF-8"));
			illegalImage = this.statisticService.DraftImage(reportModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return illegalImage;
	}
	// 导出油耗Excel
	public String downloadDraftExcel() {
		return SUCCESS;
	}

	public InputStream getDownloadDraftExcel() {
		InputStream in = null;

		try {
			reportModel.setOneName(new String(reportModel.getOneName().getBytes("8859_1"),"UTF-8"));
			in = this.statisticService.DraftReport(reportModel);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return in;
	}
}
