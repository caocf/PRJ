package com.huzhouport.dangerousgoodsportln.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclare;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.dangerousgoodsportln.model.Goods;
import com.huzhouport.dangerousgoodsportln.model.GoodsKind;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.dangerousgoodsportln.service.DangerousGoodsPortlnServer;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.organization.model.Department;
import com.huzhouport.user.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class DangerousGoodsPortlnAction extends ActionSupport implements
		ServletRequestAware {

	public void setServletRequest(HttpServletRequest arg0) {

	}

	// Map session = ActionContext.getContext().getSession(); // 获得session
	private int current; // 第几页
	private String page; // 第几页
	private String content;// 查找内容
	private PageModel pagemodel; // 分页类
	private DangerousGoodsPortlnServer dangerousGoodsPortlnServer; // server
	private String declareID; // id
	private DangerousArrivalDeclareBean dangerousArrivalDeclareBean; // 封装类
	private String userid; // 用户id
	private String reviewResult;// 审批结果
	private String reviewOpinion;// 审批意见
	private String shipName; // 船名号
	private List<GoodsKind> goodskindlist;
	private List<GoodsKind> goodskindlist1;
	private List<Goods> goodslist;
	private String goodsKindID;
	private LogsaveServer logsaveserver;

	public String getReviewOpinion() {
		return reviewOpinion;
	}

	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PageModel getPagemodel() {
		return pagemodel;
	}

	public void setPagemodel(PageModel pagemodel) {
		this.pagemodel = pagemodel;
	}

	public DangerousGoodsPortlnServer getDangerousGoodsPortlnServer() {
		return dangerousGoodsPortlnServer;
	}

	public void setDangerousGoodsPortlnServer(
			DangerousGoodsPortlnServer dangerousGoodsPortlnServer) {
		this.dangerousGoodsPortlnServer = dangerousGoodsPortlnServer;
	}

	public String getDeclareID() {
		return declareID;
	}

	public void setDeclareID(String declareID) {
		this.declareID = declareID;
	}

	public DangerousArrivalDeclareBean getDangerousArrivalDeclareBean() {
		return dangerousArrivalDeclareBean;
	}

	public void setDangerousArrivalDeclareBean(
			DangerousArrivalDeclareBean dangerousArrivalDeclareBean) {
		this.dangerousArrivalDeclareBean = dangerousArrivalDeclareBean;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public List<GoodsKind> getGoodskindlist() {
		return goodskindlist;
	}

	public void setGoodskindlist(List<GoodsKind> goodskindlist) {
		this.goodskindlist = goodskindlist;
	}

	public List<Goods> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<Goods> goodslist) {
		this.goodslist = goodslist;
	}

	public String getGoodsKindID() {
		return goodsKindID;
	}

	public void setGoodsKindID(String goodsKindID) {
		this.goodsKindID = goodsKindID;
	}

	public List<GoodsKind> getGoodskindlist1() {
		return goodskindlist1;
	}

	public void setGoodskindlist1(List<GoodsKind> goodskindlist1) {
		this.goodskindlist1 = goodskindlist1;
	}

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	// 危险品申报显示
	public String showDangerousGoodsPortln() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		if (null != page) {
			currentPage = Integer.parseInt(page);
		}
		current = currentPage;
		pagemodel = dangerousGoodsPortlnServer.findByScrollServer(
				(currentPage - 1) * maxPage, maxPage);

		// logsaveserver.logsave((String) session.get("name"), "查看危险品申报",
		// GlobalVar.LOGSEE);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品申报查找 dangerousgoodsportln
	public String selectDangerousGoodsPortln() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		if (null != page) {
			currentPage = Integer.parseInt(page);
		}
		current = currentPage;
		pagemodel = dangerousGoodsPortlnServer.findByScrollServer1(
				(currentPage - 1) * maxPage, maxPage, content);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 查看
	public String showDangerousGoodsPortlnApproval() {
		try{
		dangerousArrivalDeclareBean = dangerousGoodsPortlnServer
				.findByID(declareID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 审批提交
	public String DangerousGoodsPortlnApproval() {
		try {
			dangerousGoodsPortlnServer.updateByID(declareID, userid,
					reviewResult, reviewOpinion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	private List<Port> list1;

	public List<Port> getList1() {
		return list1;
	}

	public void setList1(List<Port> list1) {
		this.list1 = list1;
	}

	// 新建
	public String newDangerousGoodsPortln() {
		try{
		String shipName = dangerousArrivalDeclareBean.getShipName();
		// Date now = new Date();
		// DateFormat d8 =
		// DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		// //显示日期，时间（精确到分）
		// String declareTime = d8.format(now);
		// System.out.println("declareTime==="+declareTime);

		String declareTime = dangerousArrivalDeclareBean.getDeclareTime();
		String portTime = dangerousArrivalDeclareBean.getPortTime();

		int startingPort = 0;
		int arrivalPort = 0;
		list1 = dangerousGoodsPortlnServer
				.findStartingPortName(dangerousArrivalDeclareBean
						.getStartingPortName()); // 通过起运港name来找id list
		if (list1.size() == 0) {
			System.out.println(dangerousArrivalDeclareBean
					.getStartingPortName());
			Port port = new Port();
			port.setPortName(dangerousArrivalDeclareBean.getStartingPortName());

			System.out.println(dangerousArrivalDeclareBean
					.getStartingPortName());
			dangerousGoodsPortlnServer.savePort(port);
			list1 = dangerousGoodsPortlnServer
					.findStartingPortName(dangerousArrivalDeclareBean
							.getStartingPortName());
			Port p1 = list1.get(0);
			startingPort = p1.getPortID();

		} else {
			Port p1 = list1.get(0);
			startingPort = p1.getPortID();
		}

		list1 = dangerousGoodsPortlnServer
				.findStartingPortName(dangerousArrivalDeclareBean
						.getArrivalPortName());
		if (list1.size() == 0) {
			Port port1 = new Port();
			port1.setPortName(dangerousArrivalDeclareBean.getArrivalPortName());
			dangerousGoodsPortlnServer.savePort(port1);
			list1 = dangerousGoodsPortlnServer
					.findStartingPortName(dangerousArrivalDeclareBean
							.getArrivalPortName());
			Port p1 = list1.get(0);
			arrivalPort = p1.getPortID();

		} else {
			Port p1 = list1.get(0);
			arrivalPort = p1.getPortID();
		}

		String cargoTypes = dangerousArrivalDeclareBean.getCargoTypes();
		String dangerousLevel = dangerousArrivalDeclareBean.getDangerousLevel();
		int carrying = dangerousArrivalDeclareBean.getCarrying();
		DangerousArrivalDeclare dangerousArrivalDeclare = new DangerousArrivalDeclare();
		dangerousArrivalDeclare.setShipName(shipName);
		dangerousArrivalDeclare.setDeclareTime(declareTime);
		dangerousArrivalDeclare.setPortTime(portTime);
		dangerousArrivalDeclare.setStartingPort(startingPort);
		dangerousArrivalDeclare.setArrivalPort(arrivalPort);
		dangerousArrivalDeclare.setCargoTypes(cargoTypes);
		dangerousArrivalDeclare.setDangerousLevel(dangerousLevel);
		dangerousArrivalDeclare.setCarrying(carrying);
		dangerousArrivalDeclare.setReviewUser(0);
		dangerousArrivalDeclare.setReviewResult(0);
		dangerousGoodsPortlnServer.insert(dangerousArrivalDeclare);

		// logsaveserver.logsave((String) session.get("name"), "添加危险品进港申报",
		// GlobalVar.LOGSAVE);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	private List<DangerousArrivalDeclareBean> list;

	public List<DangerousArrivalDeclareBean> getList() {
		return list;
	}

	public void setList(List<DangerousArrivalDeclareBean> list) {
		this.list = list;
	}

	// 危险品申报显示 手机端
	public String showDangerousGoodsPortln1() {
		try{
		list = dangerousGoodsPortlnServer.findList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品申报查找 手机端
	public String selectDangerousGoodsPortln1() {
		try{
		list = dangerousGoodsPortlnServer.findList1(content);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品申报显示 手机端 船户版
	public String showDangerousGoodsPortln2() {
		try{
		list = dangerousGoodsPortlnServer.findListByname(userid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品申报查找 手机端 船户版
	public String selectDangerousGoodsPortln2() {
		try{
		list = dangerousGoodsPortlnServer.findListByname1(content, userid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	private List<Department> listdepartment;
	private List<User> listuser;
	private String departmentId;

	public List<Department> getListdepartment() {
		return listdepartment;
	}

	public void setListdepartment(List<Department> listdepartment) {
		this.listdepartment = listdepartment;
	}

	public List<User> getListuser() {
		return listuser;
	}

	public void setListuser(List<User> listuser) {
		this.listuser = listuser;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	// 新通讯录列表
	public String newaddresslist() {
		try{
		listdepartment = dangerousGoodsPortlnServer
				.newaddresslistdepartment(departmentId);
		listuser = dangerousGoodsPortlnServer.newaddresslistuser(departmentId);		
		
		//List<User> o=listuser;
		//System.out.println(o);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String newaddresslistmeeting() {
		try{
		listdepartment = dangerousGoodsPortlnServer
				.newaddresslistdepartment(departmentId);
		listuser = dangerousGoodsPortlnServer.newaddresslistuser(departmentId);
		if(listuser==null||listuser.size()<=0)
		{
			//dangerousGoodsPortlnServer.newaddresslistuserp(departmentId);
			if(listdepartment!=null&&listdepartment.size()>0)
			{
				for(Department dep:listdepartment)
				{
					List<User> list = dangerousGoodsPortlnServer.newaddresslistuser(String.valueOf(dep.getDepartmentId()));
					listuser.addAll(list);
				}
			}
		}
		
		//List<User> o=listuser;
		//System.out.println(o);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String newaddresslistInfo() {
		try{
		listdepartment = dangerousGoodsPortlnServer
				.newaddresslistdepartmentInfo(departmentId, userid);
		listuser = dangerousGoodsPortlnServer.newaddresslistuserInfo(
				departmentId, userid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String selectnewaddresslist() {
		try{
		listuser = dangerousGoodsPortlnServer.selectnewaddresslist(content);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 货物种类全部
	public String GoodsKindAll() {
		try{
		goodskindlist = dangerousGoodsPortlnServer.GoodsKindAll();// 有子节点
		goodskindlist1 = dangerousGoodsPortlnServer.GoodsKindAll1();// 没有子节点
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 货物 通过种类id
	public String GoodsAll() {
		try{
		goodslist = dangerousGoodsPortlnServer.GoodsAll(goodsKindID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

}
