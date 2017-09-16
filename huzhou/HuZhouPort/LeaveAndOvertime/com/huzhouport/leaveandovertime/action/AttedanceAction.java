package com.huzhouport.leaveandovertime.action;
/*
 * 沈丹丹 2015-01-31
 * 考勤管理的部分方法
 * */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.service.AttedanceService;
import com.opensymphony.xwork2.ModelDriven;

public class AttedanceAction extends BaseAction implements ModelDriven<LeaveOrOt> {
	private static final long serialVersionUID = 1L;
	private LeaveOrOt leaveOrOt = new LeaveOrOt();
	private AttedanceService attedanceService;

	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	public LeaveOrOt getModel() {
		return leaveOrOt;
	}
	public LeaveOrOt getLeaveOrOt() {
		return leaveOrOt;
	}
	public void setLeaveOrOt(LeaveOrOt leaveOrOt) {
		this.leaveOrOt = leaveOrOt;
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
	public void setAttedanceService(AttedanceService attedanceService) {
		this.attedanceService = attedanceService;
	}
	//根据权限显示请加加班的列表并分页
	public String FindAttedanceByPermission(){
		try{
			//1.pc 2.app
			if(leaveOrOt.getLeaveOrOtUser()==0&&session.get("username")!=null){
				leaveOrOt.setLeaveOrOtUser(Integer.parseInt(session.get("userId").toString()));
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.attedanceService.CountAttedanceByPermission(leaveOrOt, GlobalVar.PAGESIZE);
			if(map!=null){
				totalPage = map.get("pages");
				allTotal = map.get("allTotal");
				if (totalPage != 0) {
					list = this.attedanceService.SearchAttedanceByPermission(leaveOrOt, Integer
							.valueOf(this.getCpage()), GlobalVar.PAGESIZE);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//自己的申请
	public String FindAttedanceByMy(){
		try{
			//1.pc 2.app
			if(leaveOrOt.getLeaveOrOtUser()==0&&session.get("username")!=null){
				leaveOrOt.setLeaveOrOtUser(Integer.parseInt(session.get("userId").toString()));
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.attedanceService.CountAttedanceByMy(leaveOrOt, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.attedanceService.SearchAttedanceByMy(leaveOrOt, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//app显示全部
	public String FindAttedanceByPermission_APP(){
		try{
			list = this.attedanceService.FindAttedanceByPermission_APP(leaveOrOt);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
}
