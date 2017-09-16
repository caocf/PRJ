package com.huzhouport.electric.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electric.model.ElectricReport;
import com.huzhouport.electric.service.ElectricReportService;
import com.opensymphony.xwork2.ModelDriven;

public class ElectricReportAction extends BaseAction implements
		ModelDriven<ElectricReport> {
	private static final long serialVersionUID = 1L;

	private int totalPage;// 总页数
	private int allTotal;
	private ElectricReportService electricReportService;
	private ElectricReport electricReport = new ElectricReport();
	private List<?> list;

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

	public ElectricReport getElectricReport() {
		return electricReport;
	}

	public void setElectricReport(ElectricReport electricReport) {
		this.electricReport = electricReport;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public void setElectricReportService(
			ElectricReportService electricReportService) {
		this.electricReportService = electricReportService;
	}

	public ElectricReport getModel() {
		// TODO Auto-generated method stub
		return electricReport;
	}

	// 分页查询
	public String showElectricReportInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.electricReportService.countPageElectricReportInfo(
					electricReport, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.electricReportService.searchElectricReportInfo(
						electricReport, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 分页查询
	public String showElectricReportInfoAD() throws Exception {
		try {
			totalPage = this.electricReportService
					.countPageElectricReportInfoAD(electricReport,
							GlobalVar.PAGESIZE);
			if (totalPage != 0) {
				list = this.electricReportService.searchElectricReportInfoAD(
						electricReport, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	// 公众分页查询
	public String showElectricReportInfoPublic() throws Exception {
		try {
			totalPage = this.electricReportService
					.countPageElectricReportInfoPublic(electricReport,
							GlobalVar.PAGESIZE);
			if (totalPage != 0) {
				list = this.electricReportService.searchElectricReportInfoPublic(
						electricReport, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 增加
	public String addElectricReportInfo() throws Exception {
		try {
			this.electricReportService.addElectricReportInfo(electricReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 按ID
	public String showElectricReportID() throws Exception {
		try {
			list = this.electricReportService
					.seeElectricReportID(electricReport);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
}
