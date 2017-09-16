package com.huzhouport.electricreport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.model.QueryCondition;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electricreport.model.BoatmanKind;
import com.huzhouport.electricreport.service.BoatmanKindService;
import com.opensymphony.xwork2.ModelDriven;

public class BoatmanKindAction extends BaseAction implements
		ModelDriven<QueryCondition> {
	private static final long serialVersionUID = 1L;
	private BoatmanKind boatmanKind = new BoatmanKind();
	private BoatmanKindService boatmanKindService;

	private List<?> list = null;
	private int totalPage;// 总页数
	private int allTotal;
	private QueryCondition queryCondition = new QueryCondition();

	public void setBoatmanKindService(BoatmanKindService boatmanKindService) {
		this.boatmanKindService = boatmanKindService;
	}

	public QueryCondition getQueryCondition() {
		return queryCondition;
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

	public QueryCondition getModel() {
		return queryCondition;
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

	public BoatmanKind getBoatmanKind() {
		return boatmanKind;
	}

	public void setBoatmanKind(BoatmanKind boatmanKind) {
		this.boatmanKind = boatmanKind;
	}

	// 船员职位列表
	public String ShowBoatmanKindList() {
		try {
			list = this.boatmanKindService.ShowBoatmanKindList(boatmanKind);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 船员职位列表
	public String ShowBoatmanKindListByPage() {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.boatmanKindService.CountBoatmanKindListByPage(
					queryCondition, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.boatmanKindService.ShowBoatmanKindListByPage(
						queryCondition, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 船员职位删除
	public String DeleteBoatmanKind() throws Exception {
		try {
			this.boatmanKindService.DeleteBoatmanKind(boatmanKind);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 船员职位修改
	public String UpdateBoatmanKind() {
		try {

			Boolean check = this.boatmanKindService
					.UpdateBoatmanKind(boatmanKind);
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

	// 船员职位新增
	public String AddBoatmanKind() {
		try {
			Boolean check =this.boatmanKindService.AddBoatmanKind(boatmanKind);
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
