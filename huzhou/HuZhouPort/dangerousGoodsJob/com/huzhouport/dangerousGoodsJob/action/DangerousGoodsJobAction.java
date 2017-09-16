package com.huzhouport.dangerousGoodsJob.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclare;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclareBean;
import com.huzhouport.dangerousGoodsJob.model.WharfJobNum;
import com.huzhouport.dangerousGoodsJob.service.DangerousGoodsJobServer;
import com.huzhouport.dangerousgoodsportln.model.Port;

import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.opensymphony.xwork2.ActionSupport;

public class DangerousGoodsJobAction extends ActionSupport implements
		ServletRequestAware {

	public void setServletRequest(HttpServletRequest arg0) {

	}

	// Map session = ActionContext.getContext().getSession(); // 获得session
	private int current; // 第几页
	private String page; // 第几页
	private String content;// 查找内容
	private String wharf;
	private PageModel pagemodel; // 分页类
	private DangerousGoodsJobServer dangerousGoodsJobServer; // server
	private String declareID; // id
	private DangerousWorkDeclareBean dangerousWorkDeclareBean; // 封装类
	private String userid; // 用户id
	private String reviewResult;// 审批结果
	private String reviewOpinion;// 审批意见
	private String shipName; // 船名号
	private LogsaveServer logsaveserver;

	public void setWharf(String wharf) {
		this.wharf = wharf;
	}
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

	public DangerousGoodsJobServer getDangerousGoodsJobServer() {
		return dangerousGoodsJobServer;
	}

	public void setDangerousGoodsJobServer(
			DangerousGoodsJobServer dangerousGoodsJobServer) {
		this.dangerousGoodsJobServer = dangerousGoodsJobServer;
	}

	public DangerousWorkDeclareBean getDangerousWorkDeclareBean() {
		return dangerousWorkDeclareBean;
	}

	public void setDangerousWorkDeclareBean(
			DangerousWorkDeclareBean dangerousWorkDeclareBean) {
		this.dangerousWorkDeclareBean = dangerousWorkDeclareBean;
	}

	public String getDeclareID() {
		return declareID;
	}

	public void setDeclareID(String declareID) {
		this.declareID = declareID;
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

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	// 危险品作业显示
	public String showDangerousGoodsJob() {
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = dangerousGoodsJobServer.findByScrollServer(
					(currentPage - 1) * maxPage, maxPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品作业查找
	public String selectDangerousGoodsJob() {
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = dangerousGoodsJobServer.findByScrollServer1(
					(currentPage - 1) * maxPage, maxPage, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 查看
	public String showDangerousGoodsJobApproval() {
		try {
			dangerousWorkDeclareBean = dangerousGoodsJobServer
					.findByID(declareID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 审批提交
	public String DangerousGoodsJobApproval() {
		try {
			dangerousGoodsJobServer.updateByID(declareID, userid, reviewResult,
					reviewOpinion);
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
	public String newDangerousGoodsJob() {
		try {
			String shipName = dangerousWorkDeclareBean.getShipName();
			String declareTime = dangerousWorkDeclareBean.getDeclareTime();

			int startingPort = 0;
			int arrivalPort = 0;
			list1 = dangerousGoodsJobServer
					.findStartingPortName(dangerousWorkDeclareBean
							.getStartingPortName()); // 通过起运港name来找id list
			if (list1.size() == 0) {
				System.out.println(dangerousWorkDeclareBean
						.getStartingPortName());
				Port port = new Port();
				port
						.setPortName(dangerousWorkDeclareBean
								.getStartingPortName());
				System.out.println(dangerousWorkDeclareBean
						.getStartingPortName());
				dangerousGoodsJobServer.savePort(port);
				list1 = dangerousGoodsJobServer
						.findStartingPortName(dangerousWorkDeclareBean
								.getStartingPortName());
				Port p1 = list1.get(0);
				startingPort = p1.getPortID();
			} else {
				Port p1 = list1.get(0);
				startingPort = p1.getPortID();
			}
			list1 = dangerousGoodsJobServer
					.findStartingPortName(dangerousWorkDeclareBean
							.getArrivalPortName());
			if (list1.size() == 0) {
				Port port1 = new Port();
				port1
						.setPortName(dangerousWorkDeclareBean
								.getArrivalPortName());
				dangerousGoodsJobServer.savePort(port1);
				list1 = dangerousGoodsJobServer
						.findStartingPortName(dangerousWorkDeclareBean
								.getArrivalPortName());
				Port p1 = list1.get(0);
				arrivalPort = p1.getPortID();
			} else {
				Port p1 = list1.get(0);
				arrivalPort = p1.getPortID();
			}
			String cargoTypes = dangerousWorkDeclareBean.getCargoTypes();
			String dangerousLevel = dangerousWorkDeclareBean
					.getDangerousLevel();
			String wharfID = dangerousWorkDeclareBean.getWharfID();
			String workTIme = dangerousWorkDeclareBean.getWorkTIme();
			int carrying = dangerousWorkDeclareBean.getCarrying();
			DangerousWorkDeclare dangerousWorkDeclare = new DangerousWorkDeclare();
			dangerousWorkDeclare.setShipName(shipName);
			dangerousWorkDeclare.setDeclareTime(declareTime);
			dangerousWorkDeclare.setStartingPort(startingPort);
			dangerousWorkDeclare.setArrivalPort(arrivalPort);
			dangerousWorkDeclare.setCargoTypes(cargoTypes);
			dangerousWorkDeclare.setDangerousLevel(dangerousLevel);
			dangerousWorkDeclare.setWharfID(wharfID);
			dangerousWorkDeclare.setWorkTIme(workTIme);
			dangerousWorkDeclare.setCarrying(carrying);
			dangerousGoodsJobServer.insert(dangerousWorkDeclare);

			// logsaveserver.logsave((String) session.get("name"), "添加危险品进港申报",
			// GlobalVar.LOGSAVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	private List<DangerousWorkDeclareBean> list;

	public List<DangerousWorkDeclareBean> getList() {
		return list;
	}

	public void setList(List<DangerousWorkDeclareBean> list) {
		this.list = list;
	}

	// 危险品码头作业申报显示 手机端
	public String showDangerousGoodsJob1() {
		try {
			list = dangerousGoodsJobServer.findList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品码头作业申报查找 手机端
	public String selectDangerousGoodsJob1() {
		try {
			list = dangerousGoodsJobServer.findList1(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品码头作业申报显示 手机端 船户版
	public String showDangerousGoodsJob2() {
		try {
			list = dangerousGoodsJobServer.findListByname(shipName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 危险品码头作业申报查找 手机端 船户版
	public String selectDangerousGoodsJob2() {
		try {
			list = dangerousGoodsJobServer.findListByname1(content, shipName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	
	
	
	
	
	
	
	/*-------------------------------------*/
	
	
	
	// 危险品码头作业申报显示 手机端 码头版
	public String showDangerousGoodsJobByWharf() {
		try {
			list = dangerousGoodsJobServer.findListForWharf(wharf,content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	
	
	private List<WharfJobNum> list2;

	public List<WharfJobNum> getList2() {
		return list2;
	}

	public void setList2(List<WharfJobNum> list2) {
		this.list2 = list2;
	}

	public String findWharfJobNum() {
		try {
			list2 = dangerousGoodsJobServer.findWharfJobNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String selectWharfJobNum() {
		try {
			list2 = dangerousGoodsJobServer.selectWharfJobNum(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
