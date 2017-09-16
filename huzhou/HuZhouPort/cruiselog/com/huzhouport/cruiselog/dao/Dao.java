package com.huzhouport.cruiselog.dao;

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

public interface Dao 
{	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage);  //
	public PageModel findByPageScroll1(String hql,int firstPage,int maxPage,List list,String content);  
	public List<User> findByUsername(String hql);
	public CruiseLogBean findBycruiseLogID(String hql);
	public List<IllegalBean> findIllegalList(String hql); 
	public List<CruiseLogBean> showCruiseLogUnfinish(String hql);
	public List<CruiseLogBean> showCruiseLogUnfinish1(String hql,List list,String content);
	public List<Location> showMap(String hql);
	public List<IllegalBean> findIllegalList1(String hql); 
	public String insert(CruiseLog cruiselog ); 
	public void insert1(CruiseLogLink cruiseloglink );
	public void insert2(CruiseLogLocationLink cruiseloglocationlink);
	public void updateCruiseLog(String hql);
	public List<CruiseLogLink> findCruiseLogLink(String hql);
	public List<CruiseLogLocationLink> findCruiseLogLocationLink(String hql);
	public String insertLocation(Location location );
	public List<?> findIssuesByPid(int pid);
	public List<?> findChannelsByType(int sfgg);
	public void commitCruise(CruiseRecord cruiseRecord);
	public void commitIssue(CruiseIssue cruiseIssue);
	public void save(Object obj);
	public void update(Object obj);	
	public List<?> findRecordsByUserid(int userid);
	public List<?> findIssuesByRid(int rid);	
	public List<?> findEvidenceByIssueID(int rid);
	public void delete(Object obj);
	public List<?> findIssuesByUser(int userid);
	public CruiseRecord getCruiseRecordByID(int id);
	public List<?> findCruiseToolsByTip(String tip);

}
