package com.huzhouport.electric.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.service.BaseService;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electric.model.FixedTermReport;
import com.huzhouport.electric.model.RegularVisa;
import com.huzhouport.electric.service.FixedTermReportService;
import com.opensymphony.xwork2.ModelDriven;

public class FixedTermReportAction extends BaseAction implements
		ModelDriven<FixedTermReport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FixedTermReport fixedTermReport = new FixedTermReport();

	private FixedTermReportService fixedTermReportService;

	private List<?> list = null;
	private int totalPage;// 总页数
	private int allTotal;

	public String addFixedTermReportInfo() throws Exception {
		this.fixedTermReportService.add(fixedTermReport);
		return SUCCESS;
	}

	public String modifyFixedTermReportInfo() throws Exception {
		this.fixedTermReportService.modify(fixedTermReport);
		return SUCCESS;
	}

	public String deleteFixedTermReportInfo() throws Exception {

		this.fixedTermReportService.deleteFixedTermReportInfo(fixedTermReport);

		return SUCCESS;
	}

	public String findFixedTermReportInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.fixedTermReportService.countPageFixedTermReportInfo(
					fixedTermReport, Integer.valueOf(this.getCpage()));
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.fixedTermReportService.searchFixedTermReportInfo(
						fixedTermReport, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	public String findFixedReportInfoById() throws Exception {
		list = this.fixedTermReportService.searchDataByConditions(
				fixedTermReport, "fixedTermID", fixedTermReport
						.getFixedTermID());
		return SUCCESS;
	}

	public String findFixedTermReportInfoById() throws Exception {
		list = this.fixedTermReportService.searchDataByConditions(
				fixedTermReport, "shipName", fixedTermReport.getShipName());
		return SUCCESS;
	}

	public String findRegularVisaByIdAD() throws Exception {

		list = this.fixedTermReportService
				.findRegularVisaByIdCodition(fixedTermReport);
		return SUCCESS;
	}

	public String findRegularVisaById() throws Exception {
		try {
			if (null == fixedTermReport.getCondition()) {
				fixedTermReport.setCondition("");
			}
			list = this.fixedTermReportService
					.findRegularVisaByIdCodition(fixedTermReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public FixedTermReport getModel() {

		return fixedTermReport;
	}

	public FixedTermReport getFixedTermReport() {
		return fixedTermReport;
	}

	public void setFixedTermReport(FixedTermReport fixedTermReport) {
		this.fixedTermReport = fixedTermReport;
	}

	public void setFixedTermReportService(
			FixedTermReportService fixedTermReportService) {
		this.fixedTermReportService = fixedTermReportService;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

}
