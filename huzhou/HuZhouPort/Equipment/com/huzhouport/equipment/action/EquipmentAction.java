package com.huzhouport.equipment.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;


import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.equipment.model.Equipment;
import com.huzhouport.equipment.model.EquipmentKind;
import com.huzhouport.equipment.model.Equipmentbean;
import com.huzhouport.equipment.service.EquipmentServer;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.leaveandovertime.service.LeaveAndOvertimeServer;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.user.model.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EquipmentAction  extends ActionSupport implements ServletRequestAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	

	//Map session = ActionContext.getContext().getSession(); // 获得session
public void setServletRequest(HttpServletRequest request) {

		this.request = request;
}
public void setRequest(HttpServletRequest request) {
	this.request = request;
}
	
	
	
	private PageModel pagemodel; //
	private int current;  //第几页
	private String page; //第几页
	private String actionname;
	private String content;//查找内容
	private LogsaveServer logsaveserver;
	
	public PageModel getPagemodel() {
		return pagemodel;
	}
	public void setPagemodel(PageModel pagemodel) {
		this.pagemodel = pagemodel;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}



	private EquipmentServer equipmentServer;
	private String equipmentID;
	private Equipmentbean equipmentbean;
	public List<Equipmentbean> equipmentlist;
	public List<EquipmentKind> equipmentkindlist;
	private Equipment equipment;
	
	
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public List<EquipmentKind> getEquipmentkindlist() {
		return equipmentkindlist;
	}
	public void setEquipmentkindlist(List<EquipmentKind> equipmentkindlist) {
		this.equipmentkindlist = equipmentkindlist;
	}
	public EquipmentServer getEquipmentServer() {
		return equipmentServer;
	}
	public void setEquipmentServer(EquipmentServer equipmentServer) {
		this.equipmentServer = equipmentServer;
		
	}
public String getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}
public Equipmentbean getEquipmentbean() {
		return equipmentbean;
	}
	public void setEquipmentbean(Equipmentbean equipmentbean) {
		this.equipmentbean = equipmentbean;
	}
public List<Equipmentbean> getEquipmentlist() {
		return equipmentlist;
	}
	public void setEquipmentlist(List<Equipmentbean> equipmentlist) {
		this.equipmentlist = equipmentlist;
	}
//显示
public 	String  showEquipment(){
	int currentPage=1; //当前页
	int maxPage=10 ;  // 一页有几条
	String action="showEquipment";
	if(null!=page){
		System.out.println("page1="+page);
		currentPage=Integer.parseInt(page);
	}
	actionname=action;
	current=currentPage;
	pagemodel=equipmentServer.findByScrollServer((currentPage-1)*maxPage, maxPage ,action);
	return "success";
	
}
//待审批显示
public 	String  AwaitingApprovalEquipment(){
	int currentPage=1; //当前页
	int maxPage=10 ;  // 一页有几条
	String action="showEquipment";
	if(null!=page){
		System.out.println("page1="+page);
		currentPage=Integer.parseInt(page);
	}
	actionname=action;
	current=currentPage;
	pagemodel=equipmentServer.findByScrollServer1((currentPage-1)*maxPage, maxPage ,action);
	return "success";
	
}

//查找显示
public 	String  selectEquipment(){
	int currentPage=1; //当前页
	int maxPage=10 ;  // 一页有几条
	String action="selectEquipment";
	if(null!=page){
		System.out.println("page1="+page);
		currentPage=Integer.parseInt(page);
	}
	actionname=action;
	current=currentPage;
	pagemodel=equipmentServer.findByScrollServer2((currentPage-1)*maxPage, maxPage ,action,content);
    return "success";
}

//查看
	public String showEquipmentApproval(){
		equipmentbean=equipmentServer.findByID(equipmentID);
		return "success";
	}
	
	//审批提交
	public  String EquipmentApproval(){
		 Date now = new Date();
		  DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
	      String declareTime = d8.format(now);
	      equipmentbean.setApprovalTime(declareTime);
	      
		equipmentServer.updateByID(equipmentbean);
		
		return "success";
	}

//由我审批
	public String equipmentApprovalbyMy(){
		equipmentlist=equipmentServer.equipmentApproval(equipmentID);
		return "success";
	}
	
	//由我申请
		public String equipmentApply(){
			equipmentlist=equipmentServer.equipmentApply(equipmentID);
			return "success";
		}
		
		//全部
			public String equipmentAll(){
				equipmentlist=equipmentServer.equipmentAll();
				return "success";
			}
			
			//由我审批
			public String equipmentApprovalbyMycontent(){
				equipmentlist=equipmentServer.equipmentApprovalbyMycontent(equipmentID,content);
				return "success";
			}	
			
			//全部
			public String equipmentkindAll(){
				equipmentkindlist=equipmentServer.equipmentkindAll();
				return "success";
			}
			
			public  String equipmentAdd(){
				 Date now = new Date();
				  DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
			      String declareTime = d8.format(now);
			      equipment.setEquipmentDate(declareTime);
			      
				equipmentServer.equipmentAdd(equipment);
				
				//logsaveserver.logsave((String) session.get("name"), "添加设备申请", GlobalVar.LOGSAVE);
				
				return "success";
			}
			
}
