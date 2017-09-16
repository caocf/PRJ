package com.huzhouport.log.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.log.model.Logbean;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.log.service.Server;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class LogAction extends ActionSupport  {

	/**
	 * 
	 */
	private  Map<String, Object> session = ActionContext.getContext().getSession(); // 获得session
	private PageModel pagemodel;
	private int current;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public PageModel getPagemodel() {
		return pagemodel;
	}

	public void setPagemodel(PageModel pagemodel) {
		this.pagemodel = pagemodel;
	}

	private Logbean logbean;

	public Logbean getLogbean() {
		return logbean;
	}

	public void setLogbean(Logbean logbean) {
		this.logbean = logbean;
	}

	private Server server;
	private LogsaveServer logsaveserver;


	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	// 分页
	private String page;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	// 分页 js
	public String pageMedel() {
		//logsaveserver.logsave((String) session.get("name"), "查看日志", GlobalVar.LOGSEE);

		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "pageMedel";
		// if(null!=request.getParameter("currentPage")){
		// currentPage = Integer.parseInt(request.getParameter("currentPage"));
		// }
		if (null != page) {
			currentPage = Integer.parseInt(page);
		}
		// System.out.println("currentPage="+currentPage);
		//request.getSession().setAttribute("current", currentPage);
		current = currentPage;
		// System.out.println("currentPage2="+currentPage);
		// request.getSession().setAttribute("student",server.findByScrollServer((currentPage-1)*maxPage,
		// maxPage ,action));
		pagemodel = server.findByScrollServer((currentPage - 1) * maxPage,
				maxPage, action);
		return "success";
	}

	// 日志删除
	private String deleteLogid;

	public String getDeleteLogid() {
		return deleteLogid;
	}

	public void setDeleteLogid(String deleteLogid) {
		this.deleteLogid = deleteLogid;
	}

	public String delete1() {
		
		logsaveserver.logsave((String) session.get("name"), "删除日志", GlobalVar.LOGDELETE,GlobalVar.LOGPC,"");
		
		String id = deleteLogid;
		System.out.println("id==" + id);
		String[] names = id.split(",");
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
			server.delete(names[i]);

		}
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "pageMedel";
		System.out.println("page=" + page);
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}

		//request.getSession().setAttribute("current", currentPage);
		current = currentPage;
		pagemodel = server.findByScrollServer((currentPage - 1) * maxPage,
				maxPage, action);
		return "success";

	}

	// 日志查找
	public String selectLog() {
		String loguser = logbean.getLogUser();
		String logtime = logbean.getLogTime();
		String logcontent = logbean.getLogContent();
		String stylename = logbean.getStyleName();
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		String action = "selectLog1";
		if (null != page) {
			System.out.println("page1=" + page);
			currentPage = Integer.parseInt(page);
		}
		current = currentPage;
		pagemodel = server.findByScrollServer1((currentPage - 1) * maxPage,
				maxPage, action, loguser, logtime, logcontent, stylename);
		return "success";
	}
	
       public String logSave() {
    	   String name="";
    	   String content="";
    	   String bz="";
    	   
    	   try {
    		   name=new String(logbean.getLogUser().getBytes("ISO8859-1"),"UTF-8");
				content=new String(logbean.getLogContent().getBytes("ISO8859-1"),"UTF-8");
				bz=new String(logbean.getBz().getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		logsaveserver.logsave(name, content, logbean.getPartOfStyle(),logbean.getIsApp(),bz);
		return "success";
       }
}