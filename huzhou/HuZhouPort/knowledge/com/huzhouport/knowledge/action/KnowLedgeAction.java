package com.huzhouport.knowledge.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huzhouport.illegal.service.impl.AbnormalpushService;
import com.huzhouport.illegal.service.impl.TestCLS;
import com.huzhouport.knowledge.model.Knowledge;
import com.huzhouport.knowledge.model.KnowledgeKind;
import com.huzhouport.knowledge.service.KnowLedgeServer;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.opensymphony.xwork2.ActionSupport;

public class KnowLedgeAction extends ActionSupport
{
	// Map session = ActionContext.getContext().getSession(); // 获得session

	private PageModel pagemodel; //
	private int current; // 第几页
	private String page; // 第几页
	private String actionname;
	private String content;// 查找内容
	private LogsaveServer logsaveserver;

	private int userid;
	private AbnormalpushService pushservice; 
	

	public AbnormalpushService getPushservice() {
		return pushservice;
	}
	public void setPushservice(AbnormalpushService pushservice) {
		this.pushservice = pushservice;
	}
	public void setUserid(int userid) {
		this.userid = userid;
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

	private KnowLedgeServer knowledgeServer;

	public KnowLedgeServer getKnowledgeServer() {
		return knowledgeServer;
	}

	public void setKnowledgeServer(KnowLedgeServer knowledgeServer) {
		this.knowledgeServer = knowledgeServer;
	}

	private int maxPage; // 一页有几条

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	// 显示知识库类别
	public String showKnowLedge() {
		try {
			if (maxPage == 0) {
				maxPage = 10000;
			} else {

			}
			// System.out.println(maxPage);
			int currentPage = 1; // 当前页
			// maxPage=10 ; // 一页有几条
			String action = "showKnowLedge";
			System.out.println(action);
			// System.out.println("page="+page);
			if (null != page) {
				System.out.println("page1=" + page);
				currentPage = Integer.parseInt(page);
			}
			actionname = action;
			current = currentPage;
			pagemodel = knowledgeServer.findByScrollServer((currentPage - 1)
					* maxPage, maxPage, action);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	// 查找知识库类别
	public String selectKnowLedge() {
		try {
			System.out.println(content + "  content");
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			String action = "selectKnowLedge";
			System.out.println(action);
			// System.out.println("page="+page);
			if (null != page) {
				System.out.println("page1=" + page);
				currentPage = Integer.parseInt(page);
			}
			actionname = action;
			current = currentPage;
			System.out.println(action);
			pagemodel = knowledgeServer.findByScrollServer1((currentPage - 1)
					* maxPage, maxPage, action, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 显示知识库知识点
	public String showKnowLedgedian() {
		try {
			if (maxPage == 0) {
				maxPage = 10;
			} else {

			}

			int currentPage = 1; // 当前页

			String action = "showKnowLedgedian";

			if (null != page) {
				currentPage = Integer.parseInt(page);
			}

			// 获得此部门的子部门id
			List knowledgeakind = knowledgeServer.Listid(kindID);

			actionname = action;
			current = currentPage;
			pagemodel = knowledgeServer.showKnowLedgedian((currentPage - 1)
					* maxPage, maxPage, action, kindID, knowledgeakind, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	// 查找知识库知识点
	public String selectKnowLedgedian() {
		try {
			if (maxPage == 0) {
				maxPage = 10;
			} else {

			}

			int currentPage = 1; // 当前页

			String action = "showKnowLedgedian";
			System.out.println(action);

			if (null != page) {
				System.out.println("page1=" + page);
				currentPage = Integer.parseInt(page);
			}
			// 获得此部门的子部门id
			List knowledgeakind = knowledgeServer.Listid(kindID);

			actionname = action;
			current = currentPage;
			pagemodel = knowledgeServer.selectKnowLedgedian((currentPage - 1)
					* maxPage, maxPage, action, kindID, content,
					knowledgeakind, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	// 通过 knowledgeid 查看
	private int knowledgeID;
	private Knowledge knowledge;

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public int getKnowledgeID() {
		return knowledgeID;
	}

	public void setKnowledgeID(int knowledgeID) {
		this.knowledgeID = knowledgeID;
	}

	public String KnowLedgeView() {
		try {
			knowledge = knowledgeServer.findByknowledgeID(knowledgeID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 通过kindid查找list
	private int kindID;
	private int isinner;

	public int getIsinner() {
		return isinner;
	}

	public void setIsinner(int isinner) {
		this.isinner = isinner;
	}

	private List<Knowledge> list;

	public List<Knowledge> getList() {
		return list;
	}

	public void setList(List<Knowledge> list) {
		this.list = list;
	}

	public int getKindID() {
		return kindID;
	}

	public void setKindID(int kindID) {
		this.kindID = kindID;
	}

	public String KnowLedgeShow() {
		try {
			list = knowledgeServer.findByKindID(kindID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 是否是连接
	private int islink;
	private String link;

	public int getIslink() {
		return islink;
	}

	public void setIslink(int islink) {
		this.islink = islink;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String KnowLedgeIsLink() {
		try {

			islink = knowledgeServer.findByKindID1(kindID).size();
			if (islink > 0) {
				link = knowledgeServer.findByKindID1(kindID).get(0)
						.getKnowledgeContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	boolean hasSameName;
	public boolean isHasSameName() {
		return hasSameName;
	}
	

	// 新建知识库类别
	private KnowledgeKind knowledgekind;

	public KnowledgeKind getKnowledgekind() {
		return knowledgekind;
	}

	public void setKnowledgekind(KnowledgeKind knowledgekind) {
		this.knowledgekind = knowledgekind;
	}

	public String addknowledge() {
		try {
			hasSameName=this.knowledgeServer.hasSameKnowledgeName(knowledgekind.getKindName(),0);
			if(!hasSameName)
				knowledgeServer.addknowledge(knowledgekind);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String updateknowledge() {
		try {
			hasSameName=this.knowledgeServer.hasSameKnowledgeName(knowledgekind.getKindName(),knowledgekind.getKindID());
			if(!hasSameName)
				knowledgeServer.updateknowledge(knowledgekind);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	

	// 新建知识点
	public String knowledgeAdd() {
		try {
			knowledgeServer.knowledgeAdd(knowledge);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String s=JSONObject.toJSONString(knowledge);
		//推送
		JSONObject msg=new JSONObject();
		
		JSONArray ids=new JSONArray();
	
		String sids=knowledge.getUserids();
		System.out.println(sids);
		if("".equals(sids)||sids==null)
		{
			switch(knowledge.getIsLinkInfo())
			{
			case 0://全体				
			case 1:
				List<?> list=knowledgeServer.findAllIDs();//内部
				for(int i=0;i<list.size();i++)
				{
					int id=(Integer) list.get(i);
					ids.add(id);
				}
				
				break;
			case 2://外部
				
				break;
			}
			
			
		}
		else
		{
			String[] userids = knowledge.getUserids().split(",");

			for (String ss : userids)
			{				
				int t = Integer.valueOf(ss);
				ids.add(t);
			}
		}
		msg.put("ids", ids);
		msg.put("title", "您有新的通知公告");
		msg.put("tip", knowledge.getKnowledgeIndex());
		msg.put("mstype", 3);//通知公告
		msg.put("status", -1);			
		msg.put("type", -1);//给申请者				
		msg.put("id",-1);//不用
		System.out.println(pushservice);
		pushservice.pushAbnormal(msg.toJSONString());
		
		return "success";

	}

	// 编辑知识点
	public String knowledgeUpdate() {
		try {
			knowledgeServer.knowledgeUpdate(knowledge);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	// 删除知识点
	public String knowledgeDelete() {
		try {
			knowledgeServer.knowledgeDelete(knowledgeID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	private List<KnowledgeKind> knowledgekindlist;

	public List<KnowledgeKind> getKnowledgekindlist() {
		return knowledgekindlist;
	}

	public void setKnowledgekindlist(List<KnowledgeKind> knowledgekindlist) {
		this.knowledgekindlist = knowledgekindlist;
	}

	public String newknowledgelist() {
		try {
			knowledgekindlist = knowledgeServer.newknowledgelist(kindID);
			list = knowledgeServer.findByKindID(kindID); // 分类下的知识点
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public List<Knowledge> getKnowledges(int kindID, boolean isinner) {
		List<Knowledge> lst = new ArrayList<Knowledge>();
		try {

			if (isinner)
				lst = knowledgeServer.findByKindID(kindID);
			else
				lst = knowledgeServer.findByKindIDpublic(kindID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public List<Knowledge> getKnowledgesAll(int kindID, boolean isinner) {
		List<Knowledge> lst = new ArrayList<Knowledge>();
		try {
			// 获得当前节点上的通知
			lst = getKnowledges(kindID, isinner);
			List<KnowledgeKind> lstKinds = knowledgeServer
					.newknowledgelist(kindID);
			// 遍历子节点，获得子节点上的通知
			for (int i = 0; i < lstKinds.size(); i++) {
				KnowledgeKind kind = lstKinds.get(i);
				List<Knowledge> sublst = getKnowledgesAll(kind.getKindID(),
						isinner);
				lst.addAll(sublst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public String newknowledgelistall() {
		try {
			if (isinner == 1)
				list = getKnowledgesAll(kindID, true); // 分类下的知识点
			else
				list = getKnowledgesAll(kindID, false); // 分类下的知识点
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String newknowledgelistpublic() {
		try {
			knowledgekindlist = knowledgeServer.newknowledgelistpublic(kindID);
			list = knowledgeServer.findByKindIDpublic(kindID); // 分类下的知识点
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String selectknowledgelist() {
		try {
			list = knowledgeServer.selectknowledgelist(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String selectknowledgelistinner() {
		try {
			list = knowledgeServer.selectknowledgelistinner(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 删除知识库分类
	public String deleteknowledgekind() {
		try {
				knowledgeServer.deleteknowledgekind(kindID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/*-----------------------------------------------------*/
	
	public static final String PUBLIC = "public";
	public static final String INNER = "inner";

	String ori;
	int folderid;
	List<?> result;

	public void setOri(String ori) {
		this.ori = ori;
	}

	public void setFolderid(int folderid) {
		this.folderid = folderid;
	}

	public List<?> getResult() {
		return result;
	}

	/*
	 * 
	 * 获得内部/外部推送信息列表 queryNotifyMsgList 输入： 内部/外部 ori("public/inner")
	 * 用户ID（-1表示未登录）userid ， 目录ID folderid 输出： 该目录下的所有消息，不带递归， knowledge+pushmsg
	 * list
	 * <map(pushmsgid,userid,messageid....,KnowledgeID,KnowledgeName,KnowledgeInde
	 * ,Par...)>
	 */
	public String queryNotifyMsgList() {
		if (ori != null && !ori.equals("")) {
			int part = ori.equals(PUBLIC) ? 0 : 1;
			result = this.knowledgeServer.queryNotifyMsgList(userid, folderid,
					part,getLastMonthDate(),"");
		}

		return "success";
	}

	/*
	 * 获得内部/外部推送信息列表 queryNotifyMsgListAll 输入： 内部/外部 ori("public/inner")
	 * 用户ID（-1表示未登录）userid ， 目录ID folderid 输出： 该目录下的所有消息，带递归， knowledge+pushmsg
	 */
	public String queryNotifyMsgListAll() {
		if (ori != null && !ori.equals("")) {
			int part = ori.equals(PUBLIC) ? 0 : 1;
			result = queryNotifyMsgListAll(userid, folderid, part,getLastMonthDate(),"");
			sort((List<Map<String,Object>>)result);
		}

		return "success";
	}
	
	public String queryNotifyMsgListAllByContent()
	{
		if (ori != null && !ori.equals("")) {
			int part = ori.equals(PUBLIC) ? 0 : 1;
			
			try {
				content = new String(content.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = queryNotifyMsgListAll(userid, folderid, part,getLastMonthDate(),content);
			sort((List<Map<String,Object>>)result);
		}
		
		return "success";
	}
	
	/*
	 * 获得内部/外部推送信息列表 queryNonUserOriNotifyMsgAll 输入： 内部/外部 ori("public/inner")
	 *  目录ID folderid 输出： 该目录下的所有消息，带递归， knowledge+pushmsg
	 */
	public String queryNonUserOriNotifyMsgAll()
	{
		if(ori!=null && !ori.equals(""))
		{
			int part = ori.equals(PUBLIC) ? 0 : 1;
			result = queryNotifyMsgListAll(-1, folderid, part,getLastMonthDate(),"");
			sort((List<Map<String,Object>>)result);
		}
		
		return "success";
	}

	/**
	 * 获取推送消息递归
	 * @param userid
	 * @param kindID
	 * @param part
	 * @return
	 */
	public List<Object> queryNotifyMsgListAll(int userid, int kindID, int part,String date,String content) {
		List<Object> lst = null;
		try {

			lst = (List<Object>) this.knowledgeServer.queryNotifyMsgList(
					userid, kindID, part,date,content);
			List<KnowledgeKind> lstKinds = knowledgeServer
					.queryFolderStructure(kindID);

			for (int i = 0; i < lstKinds.size(); i++) {
				KnowledgeKind kind = lstKinds.get(i);
				List<?> sublst = queryNotifyMsgListAll(userid,
						kind.getKindID(), part,date,content);
				lst.addAll(sublst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	/*
	 * 获得目录结构 queryFolderStructure 输入： 父目录ID folderid 输出： 父节点下的子节点列表，不带递归
	 * list<knowledge_kind>
	 */

	List<KnowledgeKind> knowledgeKinds;

	public List<KnowledgeKind> getKnowledgeKinds() {
		return knowledgeKinds;
	}

	public String queryFolderStructure() {
		knowledgeKinds = queryFolderStructure(folderid);
		return "success";
	}

	public List<KnowledgeKind> queryFolderStructure(int id) {
		return this.knowledgeServer.queryFolderStructure(id);
	}

	int unreadcnt;

	public int getUnreadcnt() {
		return unreadcnt;
	}

	/*
	 * 获得目录下面向用户的未读消息数量 queryUserOriUnreadNotifyCnt 输入： 父目录ID folderid，
	 * 内部/外部ori("public/inner")，用户ID userid 输出： 该 目录下递归的所有面向用户的未读消息数量。 unreadcnt
	 */

	public String queryUserOriUnreadNotifyCnt() 
	{		
		if(userid==-1)
			unreadcnt=0;
		else if (ori != null && !ori.equals(""))
		{
			int part = ori.equals(PUBLIC) ? 0 : 1;
			unreadcnt = queryUserOriUnreadNotifyCnt(userid, folderid, part,getLastMonthDate());
		}

		return "success";
	}

	public String queryUserOriUnreadAndUnPushNotifyCnt() 
	{		
		if(userid==-1)
			unreadcnt=0;
		else if (ori != null && !ori.equals("")) {
			int part = ori.equals(PUBLIC) ? 0 : 1;
			unreadcnt = queryUserOriUnreadAndUnpushNotifyCnt(userid, folderid, part,getLastMonthDate());
		}

		return "success";
	}
	
	int moduleid;
	
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}
	
	
	public String addAllMessageToReaded()
	{
		if(userid>0)
			this.knowledgeServer.addAllMessageToReaded(userid, moduleid);
		
		return "success";
	}
	
	/**
	 * 未读消息递归查询
	 * @param userid
	 * @param kindID
	 * @param part
	 * @return
	 */
	public int queryUserOriUnreadAndUnpushNotifyCnt(int userid, int kindID, int part,String date) {
		int num = 0;
		try {
			num = this.knowledgeServer.queryUserOriUnreadAndUnPushNotifyCnt(userid,
					kindID, part,date);
			List<KnowledgeKind> lstKinds = knowledgeServer
					.queryFolderStructure(kindID);
			// 遍历子节点，获得子节点上的通知
			for (int i = 0; i < lstKinds.size(); i++) {
				KnowledgeKind kind = lstKinds.get(i);
				int r = queryUserOriUnreadAndUnpushNotifyCnt(userid, kind.getKindID(),
						part,date);
				num += r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 未读消息递归查询
	 * @param userid
	 * @param kindID
	 * @param part
	 * @return
	 */
	public int queryUserOriUnreadNotifyCnt(int userid, int kindID, int part,String date) 
	{
		int num = 0;
		try
		{
			num = this.knowledgeServer.queryUserOriUnreadNotifyCnt(userid,kindID, part,date);
			List<KnowledgeKind> lstKinds = knowledgeServer.queryFolderStructure(kindID);
			// 遍历子节点，获得子节点上的通知
			for (int i = 0; i < lstKinds.size(); i++)
			{
				KnowledgeKind kind = lstKinds.get(i);
				int r = queryUserOriUnreadNotifyCnt(userid, kind.getKindID(),part,date);
				num += r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	/*
	 * 获得用户 非面向用户的消息列表 queryNonUserOriNotifyListAll
	 * 输入：内部/外部ori("public/inner")，用户ID userid,时间 starttime 输出： 该
	 * 目录下面非面向用户的消息列表，
	 * 可直接从pushmsg表中查list<map(pushmsgid,userid,messageid....,KnowledgeID
	 * ,KnowledgeName,KnowledgeInde,Par...)>
	 */
	public String queryNonUserOriNotifyListAll() {
		if (ori != null && !ori.equals("")) {

			int part = ori.equals(PUBLIC) ? 0 : 1;
			result = this.knowledgeServer.queryNonUserOriNotifyListAll(-1,
					part, getLastMonthDate());
		}
		return "success";
	}

	/**
	 * 获取上个月时间
	 * @return
	 */
	public String getLastMonthDate() {
		
		return "";
//		Calendar calendar = Calendar.getInstance();
//
//		calendar.add(Calendar.MONTH, -1);
//		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar
//				.getTime());
	}
	
	public void sort(List<Map<String, Object>> l)
	{
//		 Collections.sort(l, new DateComparator());
	}
	
	List<Integer> forderids;
	
	public List<Integer> getForderids() {
		return forderids;
	}
	public void setForderids(List<Integer> forderids) {
		this.forderids = forderids;
	}
	public String countforders()
	{
		forderids=knowledgeServer.countforders(forderids);
		return "success";
	}
	
	public String hhz() throws Exception
	{
	    String mainurlstring="http://www.hzgh.gov.cn/hzgh/zwgk/tzgg/hxtg/index_rs_news_1.page?timestamp="+new Date().getTime();//
	
	    Document doc= Jsoup.connect(mainurlstring).get();
	    Element body=doc.body();
	
	    Elements list=body.getElementsByTag("li");
	    for(Element element:list)
	    {
	        Element a=element.child(0);
	        String href=a.attr("href");
	        String title=a.text();
	        String time=element.child(1).text();
	
	        //hhzs(href,title,time);
	    }
	    
	    return "success";
	}
	SimpleDateFormat simpledate=new SimpleDateFormat("yyyy-MM-dd");
	private void  hhzs(String href,String title,String time) throws IOException
    {
        String mainurlstring="http://www.hzgh.gov.cn/"+href;//"/2017/04/05/38138.shtml";
        Document doc= Jsoup.connect(mainurlstring).get();
        Element body=doc.body();
        Element div= body.getElementById("content");
        String content=div.html();
       
        List<?> list=knowledgeServer.newsByTitle(title);
        System.out.println("2222");
        if(list!=null&&list.size()>0)return;
        System.out.println("3333");
        knowledge=new Knowledge();
        try {
			knowledge.setDate(simpledate.parse(time));
			 //System.out.println("4444");
		} catch (ParseException e) {
			knowledge.setDate(new Date());
			e.printStackTrace();
			 System.out.println("5555");
		}
   
        knowledge.setKnowledgeName(title);
        knowledge.setKnowledgeIndex("");
        knowledge.setKnowledgeContent(content);
        knowledge.setIsLink(2);
        knowledge.setPartOfKind(2);
        knowledge.setUsernames("港航内部全部用户");
        knowledge.setUserids("");
        knowledge.setIsLinkInfo(1);
        System.out.println("6666");
        knowledgeAdd();
        System.out.println("7777");
        System.out.println(content);
    }
	
	public String testtest()
	{
		JSONObject msg=new JSONObject();
		
		JSONArray ids=new JSONArray();
		String sids=knowledge.getUserids();
		System.out.println(sids);
		if("".equals(sids)||sids==null)
		{
			switch(knowledge.getIsLinkInfo())
			{
			case 0://全体				
			case 1:
				List<?> list=knowledgeServer.findAllIDs();//内部
				for(int i=0;i<list.size();i++)
				{
					int id=(Integer) list.get(i);
					ids.add(id);
				}
				
				break;
			case 2://外部
				
				break;
			}
			
			
		}
		else
		{
			String[] userids = knowledge.getUserids().split(",");

			for (String ss : userids)
			{				
				int t = Integer.valueOf(ss);
				ids.add(t);
			}
		}
		msg.put("ids", ids);
		msg.put("title", "您有新的通知公告");
		msg.put("tip", "test");
		msg.put("mstype", 3);//通知公告
		msg.put("status", -1);			
		msg.put("type", -1);//给申请者				
		msg.put("id",-1);//不用
		System.out.println(pushservice);
		pushservice.pushAbnormal(msg.toJSONString());
		
		return "success";
		
	}
	
	public String commonee()
	{
		return "success";
	}
}

class DateComparator implements Comparator<Map<String, Object>>{

    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	
    	Date d1;
    	Date d2;
		try {
			d1 = (new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss").parse(o1.get("pushmsgtime").toString()));
			d2=(new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss").parse(o2.get("pushmsgtime").toString()));
			
			if(d1.after(d2))
	    		return 1;
			else
				return -1;
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			System.out.println(e);
		}
    	
    	return 0;
    }	
}
