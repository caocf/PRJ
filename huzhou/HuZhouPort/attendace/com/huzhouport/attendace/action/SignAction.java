package com.huzhouport.attendace.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.attendace.model.Sign;
import com.huzhouport.attendace.service.SignService;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.opensymphony.xwork2.ModelDriven;

public class SignAction extends BaseAction implements ModelDriven<Sign> {
	private static final long serialVersionUID = 1L;
	private int totalPage;// 总页数
	private int allTotal;
	private SignService signService;
	private Sign sign = new Sign();
	private Location location = new Location();
	private List<?> list;
	private String info;
	// 分页查询
	public String showSignInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.signService.countPageSignInfo(sign, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.signService.searchSignInfo(sign, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 增加
	public String addSignInfo() throws Exception {
		try {
			this.signService.addSignInfo(sign, location);
		} catch (Exception e) {
			info=e.getMessage();
			return SUCCESS;
			//throw new Exception(e.getMessage());
		}
		info="签到成功";
		return SUCCESS;
	}
	// 增加
	public String addSignInfoBack() throws Exception {
		try {
			this.signService.addSignInfo(sign, location);
		} catch (Exception e) {
			info=e.getMessage();
			return SUCCESS;
			//throw new Exception(e.getMessage());
		}
		info="签退成功";
		return SUCCESS;
	}
	
	// ID
	public String seeSignInfoID() throws Exception {
		try {
			list = this.signService.seeSignInfoID(sign);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	public String showSignInfoById() throws Exception {
		list = this.signService.searchObjectDataByConditions(new Location(),"locationID", sign.getSignLocation());
		return SUCCESS;
	}

	public Sign getModel() {

		return sign;
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

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public void setSignService(SignService signService) {
		this.signService = signService;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
