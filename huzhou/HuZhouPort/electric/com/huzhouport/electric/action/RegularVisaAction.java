package com.huzhouport.electric.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electric.model.RegularVisa;
import com.huzhouport.electric.service.RegularVisaService;
import com.opensymphony.xwork2.ModelDriven;

public class RegularVisaAction extends BaseAction implements ModelDriven<RegularVisa>{
	
	private static final long serialVersionUID = 1L;
	private RegularVisa regularVisa = new RegularVisa();
	
	private int totalPage;// 总页数
	private int allTotal;
	private RegularVisaService regularVisaService;
	private List<?> list;
	
	public List<?> getList() {
		return list;
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


	public void setList(List<?> list) {
		this.list = list;
	}


	public RegularVisa getRegularVisa() {
		return regularVisa;
	}


	public void setRegularVisa(RegularVisa regularVisa) {
		this.regularVisa = regularVisa;
	}


	public void setRegularVisaService(RegularVisaService regularVisaService) {
		this.regularVisaService = regularVisaService;
	}


	public RegularVisa getModel() {
		// TODO Auto-generated method stub
		return regularVisa;
	}

	public String addRegularVisaInfo() throws Exception {
		this.regularVisaService.addRegularVisaInfo(regularVisa);
		return SUCCESS;
	}
	public String findRegularVisaInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.regularVisaService.countPageRegularVisaInfo(
					regularVisa, Integer.valueOf(this.getCpage()));
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.regularVisaService.searchRegularVisaInfo(
						regularVisa, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	// 按ID
	public String showRegularVisaID() throws Exception {
		try {
			list = this.regularVisaService.seeRegularVisaID(regularVisa);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
}
