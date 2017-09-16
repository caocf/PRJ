package com.huzhouport.apply.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.apply.model.Apply;
import com.huzhouport.apply.service.ApplyService;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.opensymphony.xwork2.ModelDriven;

public class ApplyAction extends BaseAction implements
		ModelDriven<Apply> {
	private static final long serialVersionUID = 1L;
	
	private int totalPage;// 总页数
	private int allTotal;
	private ApplyService applyService;
	private Apply apply = new Apply();
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
	public Apply getApply() {
		return apply;
	}
	public void setApply(Apply apply) {
		this.apply = apply;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}
	public Apply getModel() {
		// TODO Auto-generated method stub
		return apply;
	}
	
	// 分页查询
	public String showApplyInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.applyService.countPageApplyInfo(
					apply, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.applyService.searchApplyInfo(
						apply, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 增加
	public String addApplyInfo() throws Exception {
		try {
			this.applyService.addApplyInfo(apply);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 按ID
	public String showApplyID() throws Exception {
		try {
			list = this.applyService.seeApplyID(apply);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
}

