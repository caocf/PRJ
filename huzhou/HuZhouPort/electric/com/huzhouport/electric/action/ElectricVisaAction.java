package com.huzhouport.electric.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.electric.model.ElectricVisa;
import com.huzhouport.electric.service.ElectricVisaService;
import com.opensymphony.xwork2.ModelDriven;

public class ElectricVisaAction extends BaseAction implements
		ModelDriven<ElectricVisa> {
	private static final long serialVersionUID = 1L;

	private int totalPage;// 总页数
	private int allTotal;
	private ElectricVisaService electricVisaService;
	private ElectricVisa electricVisa = new ElectricVisa();
	private List<?> list;

	public ElectricVisa getModel() {
		return electricVisa;
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

	public ElectricVisa getElectricVisa() {
		return electricVisa;
	}

	public void setElectricVisa(ElectricVisa electricVisa) {
		this.electricVisa = electricVisa;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public void setElectricVisaService(ElectricVisaService electricVisaService) {
		this.electricVisaService = electricVisaService;
	}

	// 分页查询
	public String showElectricVisaInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.electricVisaService.countPageElectricVisaInfo(
					electricVisa, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.electricVisaService.searchElectricVisaInfo(
						electricVisa, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	
	// 分页查询
	public String showElectricVisaInfoAD() throws Exception {
		try {
			
			totalPage = this.electricVisaService.countPageElectricVisaInfoAD(
					electricVisa, GlobalVar.PAGESIZE);		
			if (totalPage != 0) {

				list = this.electricVisaService.searchElectricVisaInfoAD(
						electricVisa, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 增加
	public String addElectricVisaInfo() throws Exception {
		try {
			this.electricVisaService.addElectricVisaInfo(electricVisa);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 按ID
	public String showElectricVisaID() throws Exception {
		try {
			list = this.electricVisaService.seeElectricVisaID(electricVisa);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	public String updateElectricVisaInfo() throws Exception {
		String visaStatus=electricVisa.getVisaStatus();
		String visaContent=electricVisa.getVisaContent();
		list = this.electricVisaService.searchDataByConditions(electricVisa,
				"visaID", electricVisa.getVisaID());
		int userId=(Integer) this.session.get("userId");
		String name=(String) this.session.get("name");
		electricVisa=(ElectricVisa) list.get(0);
		electricVisa.setVisaUser(userId);
		electricVisa.setVisaUserName(name);
		electricVisa.setVisaStatus(visaStatus);
		electricVisa.setVisaContent(visaContent);
		this.electricVisaService.modify(electricVisa);
		return SUCCESS;
	}
	
	public String updateElectircVisaByAdInfo() throws Exception{
		String visaStatus=electricVisa.getVisaStatus();
		String visaContent=electricVisa.getVisaContent();
		list = this.electricVisaService.searchDataByConditions(electricVisa,
				"visaID", electricVisa.getVisaID());
		int userId=electricVisa.getVisaUser();
		String username=electricVisa.getVisaUserName();
		electricVisa=(ElectricVisa) list.get(0);
		electricVisa.setVisaUser(userId);
		electricVisa.setVisaUserName(username);
		electricVisa.setVisaStatus(visaStatus);
		electricVisa.setVisaContent(visaContent);
		this.electricVisaService.modify(electricVisa);
		return SUCCESS;
	}
}
