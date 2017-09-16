package com.huzhouport.electricreport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electricreport.model.Certificate;
import com.huzhouport.electricreport.service.BoatcardService;
import com.opensymphony.xwork2.ModelDriven;

public class BoatcardAction extends BaseAction implements
		ModelDriven<Certificate> {
	private static final long serialVersionUID = 1L;
	private Certificate certificate = new Certificate();
	private BoatcardService boatcardService;
	private List<?> list = null;
	private int totalPage;// 总页数
	private int allTotal;
	private QueryCondition queryCondition = new QueryCondition();

	public QueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setBoatcardService(BoatcardService boatcardService) {
		this.boatcardService = boatcardService;
	}

	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	public Certificate getModel() {
		// 
		return certificate;
	}
	

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}


	// 证书列表
	public String ShowBoatcardList() {
		try {
			list = this.boatcardService.ShowBoatcardList(certificate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 证书列表分页
	public String ShowBoatcardListByPage() {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.boatcardService.CountBoatcardListByPage(certificate, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.boatcardService.ShowBoatcardListByPage(
						certificate, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 证书删除
	public String DeleteBoatCard() throws Exception {
		try {
			this.boatcardService.DeleteBoatCard(certificate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 证书修改
	public String UpdateBoatCard() {
		try {

			Boolean check = this.boatcardService
					.UpdateBoatCard(certificate);
			if (check == true) {
				totalPage = 0;
			} else {
				totalPage = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 证书新增
	public String AddBoatCard() {
		try {
			Boolean check =this.boatcardService.AddBoatCard(certificate);
			if (check == true) {
				totalPage = 0;
			} else {
				totalPage = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
