package com.huzhouport.cruiselog.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.cruiselog.model.CruiseFile;
import com.huzhouport.cruiselog.model.CruiseIssue;
import com.huzhouport.cruiselog.model.CruiseLog;
import com.huzhouport.cruiselog.model.CruiseLogBean;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.cruiselog.model.CruiseLogLocationLink;
import com.huzhouport.cruiselog.model.CruiseRecord;
import com.huzhouport.cruiselog.model.CruiseTrack;
import com.huzhouport.cruiselog.model.IllegalBean;
import com.huzhouport.cruiselog.service.CruiseLogServer;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.user.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class CruiseLogAction extends ActionSupport 
{
	//Map session = ActionContext.getContext().getSession(); // 获得session
	private int current; // 第几页
	private String page; // 第几页
	private String content;// 查找内容
	private PageModel pagemodel; // 分页类
	private CruiseLogServer cruiseLogServer; //server接口
	private String cruiseLogID;
	private CruiseLogBean cruiselogbean;
	private List<IllegalBean> illegallist;
    private List<CruiseLogBean> cruiseLogBeanList;
    private List<Location> locationList;
    private String illid;   //违章id 日志添加的时候用
    private Location location;
    private String locationid;
    private LogsaveServer logsaveserver;
    
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getIllid() {
		return illid;
	}

	public void setIllid(String illid) {
		this.illid = illid;
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	public List<CruiseLogBean> getCruiseLogBeanList() {
		return cruiseLogBeanList;
	}

	public void setCruiseLogBeanList(List<CruiseLogBean> cruiseLogBeanList) {
		this.cruiseLogBeanList = cruiseLogBeanList;
	}

	public List<IllegalBean> getIllegallist() {
		return illegallist;
	}

	public void setIllegallist(List<IllegalBean> illegallist) {
		this.illegallist = illegallist;
	}

	public String getCruiseLogID() {
		return cruiseLogID;
	}

	public CruiseLogBean getCruiselogbean() {
		return cruiselogbean;
	}

	public void setCruiselogbean(CruiseLogBean cruiselogbean) {
		this.cruiselogbean = cruiselogbean;
	}

	public void setCruiseLogID(String cruiseLogID) {
		this.cruiseLogID = cruiseLogID;
	}

	public CruiseLogServer getCruiseLogServer() {
		return cruiseLogServer;
	}

	public void setCruiseLogServer(CruiseLogServer cruiseLogServer) {
		this.cruiseLogServer = cruiseLogServer;
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


	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	// 巡航日志显示
	public String showCruiseLog() {


		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		if (null != page) {
			currentPage = Integer.parseInt(page);
		}
		current = currentPage;
		pagemodel = cruiseLogServer.findByScrollServer((currentPage - 1)* maxPage, maxPage);
		return "success";
	}

	// 巡航日志查找
	public String selectCruiseLog() {

		List<User> list1=cruiseLogServer.findByUsername(content);
		List list= new ArrayList();
		for(int i=0;i<list1.size();i++){
		list.add(list1.get(i).getUserId());	
		}
		
		
		
		int currentPage = 1; // 当前页
		int maxPage = 10; // 一页有几条
		if (null != page) {
			currentPage = Integer.parseInt(page);
		}
		current = currentPage;
		pagemodel = cruiseLogServer.findByScrollServer1((currentPage - 1)
				* maxPage, maxPage, content,list);
		return "success";
	}
	
	//查看 
	public String showCruiseLogAndIllegal(){
		cruiselogbean=cruiseLogServer.findBycruiseLogID(cruiseLogID);
		illegallist=cruiseLogServer	.findIllegalList(cruiseLogID);
		
		return "success";
	}
	
	//通过巡航日志id 找到位置坐标 list
	public String showMap(){
		locationList=cruiseLogServer.showMap(cruiseLogID);
		return "success";		
	}
	
	
	
	
	// 巡航日志显示 手机端 未完成
	public String showCruiseLogUnfinish() {
		cruiseLogBeanList=cruiseLogServer.showCruiseLogUnfinish();
		return "success";
	}
	// 巡航日志显示 手机端 完成
	public String showCruiseLogFinish() {
		cruiseLogBeanList=cruiseLogServer.showCruiseLogFinish();
		return "success";
	}
	
	// 巡航日志显示 手机端 未完成 //搜索
	public String selectCruiseLogUnfinish() {
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		List<User> list1=cruiseLogServer.findByUsername(content);
		List list= new ArrayList();
		for(int i=0;i<list1.size();i++){
		list.add(list1.get(i).getUserId());	
		}
		cruiseLogBeanList=cruiseLogServer.selectCruiseLogUnfinish(content,list);
		return "success";
	}
	// 巡航日志显示 手机端 完成 //搜索
	public String selectCruiseLogFinish() {
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		List<User> list1=cruiseLogServer.findByUsername(content);
		List list= new ArrayList();
		for(int i=0;i<list1.size();i++){
		list.add(list1.get(i).getUserId());	
		}
		cruiseLogBeanList=cruiseLogServer.selectCruiseLogFinish(content,list);
		return "success";
	}
	
	//查看                       
	public String showCruiseLogIllegalList(){
	
		illegallist=cruiseLogServer	.findIllegalList1(illid);
		
		return "success";
	}
	
	//新建
	public String newCruiseLog(){
		
		
		String illidString=cruiselogbean.getCruiseLogUserName();  //违章的id
		String cruiseLogLoaction= cruiselogbean.getCruiseLogLoaction();
		String cruiseLogUser=cruiselogbean.getCruiseLogUser();
		String shipName= cruiselogbean.getShipName();
		String startTime= cruiselogbean.getStartTime();
		//String endTime= cruiselogbean.getEndTime();
		int state= cruiselogbean.getState();
		
		CruiseLog cruiselog=new CruiseLog();
		cruiselog.setCruiseLogLoaction(cruiseLogLoaction);
		cruiselog.setCruiseLogUser(cruiseLogUser);
		cruiselog.setShipName(shipName);
		cruiselog.setStartTime(startTime);
		//cruiselog.setEndTime(endTime);
		cruiselog.setState(state);
		cruiseLogID= cruiseLogServer.insert(cruiselog);  //保存且获得id
		
		if(illidString.length()==0){
			
		}else{
		  String[] sub=illidString.split(",");
			 
	 	    for(int i=0;i<sub.length ;i++){
                CruiseLogLink cruiseloglink=new CruiseLogLink();      
	 	    		cruiseloglink.setIllegalID( Integer.valueOf( sub[i]));
	 	    		cruiseloglink.setCruiseLogID(Integer.valueOf(cruiseLogID));
	 	    		cruiseLogServer.insert1(cruiseloglink);
	 	     }
		}
		
		/*if(locationid.length()==0){
			
		}else{
			String[] sub=locationid.split(",");
			 for(int i=0;i<sub.length ;i++){
				 CruiseLogLocationLink cruiseloglocationlink=new CruiseLogLocationLink();      
				 cruiseloglocationlink.setLocationID( Integer.valueOf( sub[i]));
				 cruiseloglocationlink.setCruiseLogID(Integer.valueOf(cruiseLogID));
		 	     cruiseLogServer.insert2(cruiseloglocationlink);
		 	     }
		}*/
		
	//	logsaveserver.logsave((String) session.get("name"), "添加巡航日志", GlobalVar.LOGSAVE);
		
		return "success";
	}
	
	
	public String updateCruiseLog()
	{	
		System.out.println("12345667");
		cruiseLogServer.updateCruiseLog(cruiselogbean);
		int cruiseid=cruiselogbean.getCruiseLogID();
		String illidString=cruiselogbean.getCruiseLogUserName();  //违章的id
	   
      if(illidString.length()==0){
			
		}else{
		  
		  String[] sub=illidString.split(",");
			 
	 	    for(int i=0;i<sub.length ;i++){
                CruiseLogLink cruiseloglink=new CruiseLogLink(); 
                   
                  List<CruiseLogLink>  listlink=cruiseLogServer.findCruiseLogLink(cruiseid,sub[i]);      //判断是否已经存在
                  if(listlink.size()==0){
                	cruiseloglink.setIllegalID( Integer.valueOf( sub[i]));
  	 	    		cruiseloglink.setCruiseLogID(Integer.valueOf(cruiseid));
  	 	    		cruiseLogServer.insert1(cruiseloglink); 
                  }
                
	 	    		
	 	     }
		}
      
      if(locationid.length()==0){
			
		}else{
			String[] sub=locationid.split(",");
			 for(int i=0;i<sub.length ;i++){
				 CruiseLogLocationLink cruiseloglocationlink=new CruiseLogLocationLink();      
				 cruiseloglocationlink.setLocationID( Integer.valueOf( sub[i]));
				 cruiseloglocationlink.setCruiseLogID(Integer.valueOf(cruiseid));
		 	     cruiseLogServer.insert2(cruiseloglocationlink);
		 	     }
			
		}
      
      return "success";
	}
	
	public String cruiseLoglocation(){
		locationid= cruiseLogServer.insertLocation(location);
		 CruiseLogLocationLink cruiseloglocationlink=new CruiseLogLocationLink();      
		 cruiseloglocationlink.setLocationID(Integer.valueOf(locationid));
		 cruiseloglocationlink.setCruiseLogID(Integer.valueOf(cruiseLogID));
 	     cruiseLogServer.insert2(cruiseloglocationlink);
		return "success";
	}
	public String updateCruiseLogByID(){
		cruiseLogServer.updateCruiseLogByID(cruiseLogID);
		return "success";
	}
	
	int pid;
	List dataList;
	
	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String findIssuesByPid()
	{
		dataList=cruiseLogServer.findIssuesByPid(pid);
		return "success";
	}
	
	public String findChannelsByType()
	{
		dataList=cruiseLogServer.findChannelsByType(pid);
		return "success";
	}
	
	CruiseRecord cruiseRecord;
	
	public CruiseRecord getCruiseRecord() {
		return cruiseRecord;
	}

	public void setCruiseRecord(CruiseRecord cruiseRecord) {
		this.cruiseRecord = cruiseRecord;
	}
	List<User> userids;
	

	public List getUserids() {
		return userids;
	}

	public void setUserids(List userids) {
		this.userids = userids;
	}
	
	List<CruiseTrack> tracks;
	

	public List<CruiseTrack> getTracks() {
		return tracks;
	}

	public void setTracks(List<CruiseTrack> tracks) {
		this.tracks = tracks;
	}

	public String commitCruise()
	{
		Set<User> users=new HashSet();
		for(User u:userids)
		{
			users.add(u);
		}
		cruiseRecord.setUserids(users);
		Set<CruiseTrack> track_set=new HashSet();
		/*for(CruiseTrack c:tracks)
		{
			track_set.add(c);
		}*/
		cruiseRecord.setUserids(users);
		cruiseRecord.setTacks(track_set);
		cruiseLogServer.commitCruise(cruiseRecord);
		return "success";
	}
	
	CruiseIssue cruiseIssue;
	
	
	public CruiseIssue getCruiseIssue() {
		return cruiseIssue;
	}

	public void setCruiseIssue(CruiseIssue cruiseIssue) {
		this.cruiseIssue = cruiseIssue;
	}
	List<File> issueFile;
	

	public List<File> getIssueFile() {
		return issueFile;
	}

	public void setIssueFile(List<File> issueFile) {
		this.issueFile = issueFile;
	}

	public String commitIssue()
	{
		cruiseLogServer.commitIssue(cruiseIssue);
		if(issueFile!=null)
		{
			for(File file:issueFile)
			{
				try
				{
					java.io.InputStream is = new java.io.FileInputStream(file);
					String relatePath=GlobalVar.FilePATH+"/"+new Date().getTime()+".jpg";
					String root = ServletActionContext.getServletContext().getRealPath(relatePath);
					
					java.io.OutputStream os;
					
					os = new java.io.FileOutputStream(root);
					
					byte buffer[] = new byte[8192];
					int count = 0;
					while ((count = is.read(buffer)) > 0) {
						os.write(buffer, 0, count);
					}
					os.close();
					is.close();
					//file.transferTo(targetFile);
					CruiseFile cruiseFiles= new CruiseFile();
					cruiseFiles.setIssueid(cruiseIssue.getId());
					cruiseFiles.setPath(relatePath);
					cruiseLogServer.save(cruiseFiles);
				}catch (Exception e)
				{
					
				}
				
			}
			
		}
		
		CruiseRecord cr=cruiseLogServer.getCruiseRecordByID(cruiseIssue.getRecordid());
		cr.setIssues(cr.getIssues()+1);
		cruiseLogServer.update(cr);
		
		return "success";
	}
	
	public String updateCruise()
	{
		Set<User> users=new HashSet();
		for(User u:userids)
		{
			users.add(u);
		}
		cruiseRecord.setUserids(users);
		Set<CruiseTrack> track_set=new HashSet();
		if(tracks!=null)
		{
			for(CruiseTrack c:tracks)
			{
				track_set.add(c);
			}
		}
		
		cruiseRecord.setTacks(track_set);
		CruiseRecord cr=cruiseLogServer.getCruiseRecordByID(cruiseRecord.getId());
		cruiseRecord.setIssues(cr.getIssues());
		cruiseLogServer.update(cruiseRecord);
		return "success";
	}
	
	public String findRecordsByUserid()
	{
		dataList=cruiseLogServer.findRecordsByUserid(pid);
		return "success";
	}
	
	public String findIssuesByRid()
	{
		dataList=cruiseLogServer.findIssuesByRid(pid);
		return "success";
	}
	
	public String findEvidenceByIssueID()
	{
		dataList=cruiseLogServer.findEvidenceByIssueID(pid);
		
		return "success";
	}
	
	public String updateIssue()
	{
		List<CruiseFile> list=(List<CruiseFile>) cruiseLogServer.findEvidenceByIssueID(cruiseIssue.getId());
		for(CruiseFile cf:list)
		{
			String path=ServletActionContext.getServletContext().getRealPath(cf.getPath());
			File file=new File(path);
			if(file!=null&&file.exists())file.delete();
			cruiseLogServer.delete(cf);
		}
		
		cruiseLogServer.update(cruiseIssue);
		if(issueFile!=null)
		{
			for(File file:issueFile)
			{
				try
				{
					java.io.InputStream is = new java.io.FileInputStream(file);
					String relatePath=GlobalVar.FilePATH+"/"+new Date().getTime()+".jpg";
					String root = ServletActionContext.getServletContext().getRealPath(relatePath);
					
					java.io.OutputStream os;
					
					os = new java.io.FileOutputStream(root);
					
					byte buffer[] = new byte[8192];
					int count = 0;
					while ((count = is.read(buffer)) > 0) {
						os.write(buffer, 0, count);
					}
					os.close();
					is.close();
					//file.transferTo(targetFile);
					CruiseFile cruiseFiles= new CruiseFile();
					cruiseFiles.setIssueid(cruiseIssue.getId());
					cruiseFiles.setPath(relatePath);
					cruiseLogServer.save(cruiseFiles);
				}catch (Exception e)
				{
					
				}
				
			}
			
		}
		return "success";
	}
	
	public String deleteIssue()
	{
		CruiseRecord cr=cruiseLogServer.getCruiseRecordByID(cruiseIssue.getRecordid());
		
		List<CruiseFile> list=(List<CruiseFile>) cruiseLogServer.findEvidenceByIssueID(cruiseIssue.getId());
		for(CruiseFile cf:list)
		{
			String path=ServletActionContext.getServletContext().getRealPath(cf.getPath());
			File file=new File(path);
			if(file!=null&&file.exists())file.delete();
			cruiseLogServer.delete(cf);
		}
		
		cruiseLogServer.delete(cruiseIssue);
		
		cr.setIssues(cr.getIssues()-1);
		cruiseLogServer.update(cr);
		return "success";
	}
	
	public String findIssuesByUser()
	{
		dataList=cruiseLogServer.findIssuesByUser(pid);
		
		return "success";
	}
	String tip;
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String findCruiseToolsByTip()
	{
		dataList=cruiseLogServer.findCruiseToolsByTip(tip);
		return "success";
	}

}
