package com.huzhouport.leaveandovertime.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApproval;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApprovalbean;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.leaveandovertime.service.LeaveAndOvertimeServer;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LeaveAndOvertimeAction extends ActionSupport {
	private PageModel pagemodel; //
	private int current; // 第几页
	private String page; // 第几页
	private String actionname;
	private String content;// 查找内容
	private LeaveAndOvertimeServer leaveAndOvertimeServer;

	private Map session = ActionContext.getContext().getSession(); // 获得session
	private LogsaveServer logsaveserver;

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	public void setLeaveAndOvertimeServer(
			LeaveAndOvertimeServer leaveAndOvertimeServer) {
		this.leaveAndOvertimeServer = leaveAndOvertimeServer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getActionname() {
		return actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

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

	// 请假加班显示
	public String showLeaveAndOvertime() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "showLeaveAndOvertime";
		// System.out.println("page="+page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		actionname = action;
		current = currentPage;
		pagemodel = leaveAndOvertimeServer.findByScrollServer((currentPage - 1)
				* maxPage, maxPage, action);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	// 待审批显示
	public String AwaitingApproval() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "AwaitingApproval";
		System.out.println(action);
		// System.out.println("page="+page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		actionname = action;
		current = currentPage;
		pagemodel = leaveAndOvertimeServer.findByScrollServer1(
				(currentPage - 1) * maxPage, maxPage, action, userid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 加班显示
	public String Overtime() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "Overtime";
		System.out.println(action);
		// System.out.println("page="+page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		actionname = action;
		current = currentPage;
		pagemodel = leaveAndOvertimeServer.findByScrollServer2(
				(currentPage - 1) * maxPage, maxPage, action);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 请假
	public String Leave() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "Leave";
		// System.out.println("page="+page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		actionname = action;
		current = currentPage;
		pagemodel = leaveAndOvertimeServer.findByScrollServer3(
				(currentPage - 1) * maxPage, maxPage, action);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 出差
	public String Travel() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "Travel";
		System.out.println(action);
		// System.out.println("page="+page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		actionname = action;
		current = currentPage;
		pagemodel = leaveAndOvertimeServer.findByScrollServer5(
				(currentPage - 1) * maxPage, maxPage, action);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	public String selectLeaveAndOvertime() {
		try{
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "selectLeaveAndOvertime";
		System.out.println(action);
		// System.out.println("page="+page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		actionname = action;
		current = currentPage;
		pagemodel = leaveAndOvertimeServer.findByScrollServer4(
				(currentPage - 1) * maxPage, maxPage, action, content);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 审批
	private int leaveOrOtID1;
	private LeaveOrOtKindbean leaveOrOtKindbean;

	public LeaveOrOtKindbean getLeaveOrOtKindbean() {
		return leaveOrOtKindbean;
	}

	public void setLeaveOrOtKindbean(LeaveOrOtKindbean leaveOrOtKindbean) {
		this.leaveOrOtKindbean = leaveOrOtKindbean;
	}

	public int getLeaveOrOtID1() {
		return leaveOrOtID1;
	}

	public void setLeaveOrOtID1(int leaveOrOtID1) {
		this.leaveOrOtID1 = leaveOrOtID1;
	}

	public String showLeaveAndOvertimeApproval() {
		try{
		leaveOrOtKindbean = leaveAndOvertimeServer
				.findByLeaveOrOtKindbean(leaveOrOtID1);

		String[] beginstring = leaveOrOtKindbean.getBeginDate().substring(0,
				leaveOrOtKindbean.getBeginDate().indexOf(":")).split(" ");
		String[] endstring = leaveOrOtKindbean.getEndDate().substring(0,
				leaveOrOtKindbean.getEndDate().indexOf(":")).split(" ");
		String begin = "";
		String end = "";
		if (beginstring[1].charAt(0) == 0) {
			begin = leaveOrOtKindbean.getBeginDate().substring(0,
					leaveOrOtKindbean.getBeginDate().indexOf(" "))
					+ " 上午";
		} else {
			if (Integer.valueOf(beginstring[1]) > 12) {
				begin = leaveOrOtKindbean.getBeginDate().substring(0,
						leaveOrOtKindbean.getBeginDate().indexOf(" "))
						+ " 下午";
			} else {
				begin = leaveOrOtKindbean.getBeginDate().substring(0,
						leaveOrOtKindbean.getBeginDate().indexOf(" "))
						+ " 上午";
			}
		}
		if (endstring[1].charAt(0) == 0) {
			end = leaveOrOtKindbean.getEndDate().substring(0,
					leaveOrOtKindbean.getEndDate().indexOf(" "))
					+ " 上午";
		} else {
			if (Integer.valueOf(beginstring[1]) > 12) {
				end = leaveOrOtKindbean.getEndDate().substring(0,
						leaveOrOtKindbean.getEndDate().indexOf(" "))
						+ " 下午";
			} else {
				end = leaveOrOtKindbean.getEndDate().substring(0,
						leaveOrOtKindbean.getEndDate().indexOf(" "))
						+ " 上午";
			}
		}
		leaveOrOtKindbean.setBeginDate(begin);
		leaveOrOtKindbean.setEndDate(end);

		if (session.size() != 0) {
			logsaveserver.logsave((String) session.get("name"), "查看考勤管理",
					GlobalVar.LOGSEE, GlobalVar.LOGPC,"");
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 审批提交
	private LeaveOrOt leaveOrOt;

	public LeaveOrOt getLeaveOrOt() {
		return leaveOrOt;
	}

	public void setLeaveOrOt(LeaveOrOt leaveOrOt) {
		this.leaveOrOt = leaveOrOt;
	}

	public String LeaveAndOvertimeApprovalAgree() {
		try{
		int leaveOrOtID = leaveOrOt.getLeaveOrOtID();
		int userid = leaveOrOt.getApprovalID1();
		int ApprovalResult1 = leaveOrOt.getApprovalResult1(); // 提交的选项 1通过 2驳回
		String ApprovalOpinion1 = leaveOrOt.getApprovalOpinion1(); // 提交的意见
		List<LeaveOrOt> list = leaveAndOvertimeServer.findByLeaveOrOt(
				leaveOrOtID, userid, 1); // approvalId+第3个差数 对比审批人是在那个位子
		if (list.size() == 0) {
			list = leaveAndOvertimeServer.findByLeaveOrOt(leaveOrOtID, userid,
					2);
			if (list.size() == 0) {
				leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
						ApprovalResult1, ApprovalOpinion1, 3); // update
				// approvalId3
				leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 4);
			} else {
				if (list.get(0).getApprovalResult2() == 0) {
					leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
							ApprovalResult1, ApprovalOpinion1, 2); // update
					// approvalId2
					if (list.get(0).getApprovalID3() == 0) {
						leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 4);
					}
				} else {
					leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
							ApprovalResult1, ApprovalOpinion1, 3); // update
					// approvalId3
					leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 4);
				}
			}

		} else {
			if (list.get(0).getApprovalResult1() == 0) {
				leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
						ApprovalResult1, ApprovalOpinion1, 1); // update
				// approvalId1
				if (list.get(0).getApprovalID2() == 0) {
					leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 4);
				} else {
					leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 2);
				}
			} else {
				list = leaveAndOvertimeServer.findByLeaveOrOt(leaveOrOtID,
						userid, 2);
				if (list.size() == 0) {
					leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
							ApprovalResult1, ApprovalOpinion1, 3); // update
					// approvalId3
					leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 4);
				} else {
					if (list.get(0).getApprovalResult2() == 0) {
						leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
								ApprovalResult1, ApprovalOpinion1, 2); // update
						// approvalId2
						if (list.get(0).getApprovalID3() == 0) {
							leaveAndOvertimeServer.updateLeaveOrOt1(
									leaveOrOtID, 4);
						}
					} else {
						leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,
								ApprovalResult1, ApprovalOpinion1, 3); // update
						// approvalId3
						leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 4);
					}

				}
			}
		}

		// leaveAndOvertimeServer.updateLeaveOrOt(leaveOrOtID,ApprovalResult1,ApprovalOpinion1,1);
		// //update 意见
		if (leaveOrOt.getApprovalResult1() == 2) {
			leaveAndOvertimeServer.updateLeaveOrOt1(leaveOrOtID, 3);// 状态
		} else {

		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";

	}

	// 新建

	public String newLeaveAndOvertime() {

		// LeaveOrOtKind
		// leaveOrOtKind=leaveAndOvertimeServer.findByKindID(leaveOrOtKindbean.getKindName());
		try {
			int kindID = Integer.parseInt(leaveOrOtKindbean.getKindName());
			int leaveOrOtUser = Integer.parseInt(leaveOrOtKindbean
					.getLeaveOrOtUser());
			String leaveOrOtReason = leaveOrOtKindbean.getLeaveOrOtReason();
			String beginDate = leaveOrOtKindbean.getBeginDate();
			String endDate = leaveOrOtKindbean.getEndDate();
			int lastDate = leaveOrOtKindbean.getLastDate();
			int approvalID1 = Integer.parseInt(leaveOrOtKindbean
					.getApprovalID1());
			int approvalID2 = Integer.parseInt(leaveOrOtKindbean
					.getApprovalID2());
			int approvalID3 = Integer.parseInt(leaveOrOtKindbean
					.getApprovalID3());
			int approvalResult = 1;

			Date now = new Date();
			DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
					DateFormat.MEDIUM); // 显示日期，时间（精确到分）
			String declareTime = d8.format(now);

			leaveOrOt = new LeaveOrOt();

			System.out.println("kindID===" + kindID);
			leaveOrOt.setLeaveOrOtKind(kindID);

			leaveOrOt.setLeaveOrOtUser(leaveOrOtUser);

			leaveOrOt.setLeaveOrOtReason(leaveOrOtReason);

			leaveOrOt.setLeaveOrOtDate(declareTime);

			leaveOrOt.setBeginDate(beginDate);

			leaveOrOt.setLastDate(lastDate);

			leaveOrOt.setEndDate(endDate);

			leaveOrOt.setApprovalID1(approvalID1);

			leaveOrOt.setApprovalID2(approvalID2);

			leaveOrOt.setApprovalID3(approvalID3);

			leaveOrOt.setApprovalResult(approvalResult);
			leaveOrOt.setAddress(leaveOrOtKindbean.getAddress());

			leaveAndOvertimeServer.saveLeaveOrOt(leaveOrOt);

			// logsaveserver.logsave((String) session.get("name"), "添加请假加班",
			// GlobalVar.LOGSAVE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";

	}

	// 两个日期之间的天数
	public static int getQuot(String time1, String time2) {
		int lastdate = 0;
		long quot = 0;
		long quot1 = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot1 = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24; // 计算几天
			quot1 = quot1 % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);// 计算差多少小时

			int q = (int) quot;
			int q1 = (int) quot;
			lastdate = q * 24 + q1;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return lastdate;
	}

	// 请假 显示自己的
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<LeaveOrOtKindbean> leavelist;

	public List<LeaveOrOtKindbean> getLeavelist() {
		return leavelist;
	}

	public void setLeavelist(List<LeaveOrOtKindbean> leavelist) {
		this.leavelist = leavelist;
	}

	public String Leave1() {
		try{
		leavelist = leaveAndOvertimeServer.findByuserid1(userid);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 加班 显示自己的
	public String Overtime1() {
		try{
		leavelist = leaveAndOvertimeServer.findByuserid2(userid);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 显示全部
	public String LeaveAndOvertiemALL() {
		try{
		leavelist = leaveAndOvertimeServer.findByuserid3();
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 查找请假加班类型
	private String kindType;
	public List<LeaveOrOtKind> leaveOrOtKind;

	public String getKindType() {
		return kindType;
	}

	public void setKindType(String kindType) {
		this.kindType = kindType;
	}

	public List<LeaveOrOtKind> getLeaveOrOtKind() {
		return leaveOrOtKind;
	}

	public void setLeaveOrOtKind(List<LeaveOrOtKind> leaveOrOtKind) {
		this.leaveOrOtKind = leaveOrOtKind;
	}

	public String LeaveAndOvertimeKindName() {
		// kindType="sjd";
		try{
		leaveOrOtKind = leaveAndOvertimeServer.findByKindName(kindType);
		}catch (Exception e) {
		 e.printStackTrace();
		}
		return "success";
	}

	// 自己审批的
	public String LeaveAndOvertimefinish() {
		try{
		leavelist = leaveAndOvertimeServer.leaveAndOvertimefinish(userid);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	public String LeaveAndOvertimeunfinish() {
		try{
		leavelist = leaveAndOvertimeServer.leaveAndOvertimeunfinish(userid);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	public String selectLeaveAndOvertimefinish() {
		try{
		leavelist = leaveAndOvertimeServer.selectLeaveAndOvertimefinish(userid,
				content);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	public String selectLeaveAndOvertimeunfinish() {
		try{
		leavelist = leaveAndOvertimeServer.selectLeaveAndOvertimeunfinish(
				userid, content);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 显示自己申请的全部
	public String LeaveAndOvertiemALLbymy() {
		try{
		leavelist = leaveAndOvertimeServer.leaveAndOvertiemALLbymy(userid);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 查找自己申请的全部
	public String selectLeaveAndOvertiemALLbymy() {
		try{
		leavelist = leaveAndOvertimeServer.selectleaveAndOvertiemALLbymy(
				userid, content);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	// 查找全部
	public String selectLeaveAndOvertiemALL() {
		try{
		leavelist = leaveAndOvertimeServer.selectLeaveAndOvertiemALL(content);
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

	private List<LeaveOrOtApprovalbean> approvalbean;

	private LeaveOrOtApproval approval;

	// 查找审批人
	public List<LeaveOrOtApprovalbean> getApprovalbean() {
		return approvalbean;
	}

	public void setApprovalbean(List<LeaveOrOtApprovalbean> approvalbean) {
		this.approvalbean = approvalbean;
	}

	public LeaveOrOtApproval getApproval() {
		return approval;
	}

	public void setApproval(LeaveOrOtApproval approval) {
		this.approval = approval;
	}

	public String LeaveOrOtApproval() {
		approvalbean = leaveAndOvertimeServer.leaveOrOtApproval(userid);
		return "success";
	}

	public String SaveLeaveOrOtApproval() {
		try{
		approvalbean = leaveAndOvertimeServer.leaveOrOtApproval(approval
				.getUserID()
				+ "");
		int approvalbeansize = approvalbean.size();
		if (approvalbeansize == 0) { // 不存在
			leaveAndOvertimeServer.saveLeaveOrOtApproval(approval);
		} else {
			leaveAndOvertimeServer.updateLeaveOrOtApproval(approval);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		return "success";
	}

}
