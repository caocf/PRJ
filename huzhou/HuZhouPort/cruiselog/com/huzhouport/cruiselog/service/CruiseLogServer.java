package com.huzhouport.cruiselog.service;



import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.cruiselog.model.CruiseIssue;
import com.huzhouport.cruiselog.model.CruiseLog;
import com.huzhouport.cruiselog.model.CruiseLogBean;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.cruiselog.model.CruiseLogLocationLink;
import com.huzhouport.cruiselog.model.CruiseRecord;
import com.huzhouport.cruiselog.model.IllegalBean;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.user.model.User;
public interface CruiseLogServer 
{
	public PageModel<CruiseLogBean> findByScrollServer(int currentPage,int maxPage ); //巡航日志显示
	public PageModel<CruiseLogBean> findByScrollServer1(int currentPage,int maxPage ,String content,List list); //危险品申报显示
	public List<User> findByUsername(String content); //通过名字 找出userid //模糊
	public CruiseLogBean findBycruiseLogID(String cruiseLogID);
	public List<IllegalBean> findIllegalList(String cruiseLogID); 
	public List<CruiseLogBean> showCruiseLogUnfinish();
	public List<CruiseLogBean> showCruiseLogFinish();
	public List<Location> showMap(String cruiseLogID);
	public List<CruiseLogBean> selectCruiseLogUnfinish(String content,List list);
	public List<CruiseLogBean> selectCruiseLogFinish(String content,List list);
	public List<IllegalBean> findIllegalList1(String illid); 
	public String insert(CruiseLog cruiselog ); //新建 返回id
	public void insert1(CruiseLogLink cruiseloglink ); //新建  关联表  c i
	public void insert2(CruiseLogLocationLink cruiseloglocationlink);
	public void updateCruiseLog(CruiseLogBean cruiselogbean);
	public List<CruiseLogLink> findCruiseLogLink(int cruiseid,String illid);
	public List<CruiseLogLocationLink> findCruiseLogLocationLink(int cruiseid,String locaid);
	public String insertLocation(Location location ); //新建 返回id
	public void updateCruiseLogByID(String cruiseLogID);
	public List<?> findIssuesByPid(int pid);
	public List<?> findChannelsByType(int sfgg);	
	public void commitCruise(CruiseRecord cruiseRecord);
	public void commitIssue(CruiseIssue cruiseIssue);
	public void save(Object obj);	
	public void update(Object obj);
	public List<?> findRecordsByUserid(int userid);
	public List<?> findIssuesByRid(int rid) ;
	public List<?> findEvidenceByIssueID(int rid);
	public void delete(Object obj);
	public List<?> findIssuesByUser(int userid) ;
	public CruiseRecord getCruiseRecordByID(int id);
	public List<?> findCruiseToolsByTip(String tip);
}
