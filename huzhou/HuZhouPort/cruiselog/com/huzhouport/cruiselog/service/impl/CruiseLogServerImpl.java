package com.huzhouport.cruiselog.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.cruiselog.dao.Dao;
import com.huzhouport.cruiselog.model.CruiseIssue;
import com.huzhouport.cruiselog.model.CruiseLog;
import com.huzhouport.cruiselog.model.CruiseLogBean;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.cruiselog.model.CruiseLogLocationLink;
import com.huzhouport.cruiselog.model.CruiseRecord;
import com.huzhouport.cruiselog.model.IllegalBean;
import com.huzhouport.cruiselog.service.CruiseLogServer;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.user.model.User;

public class CruiseLogServerImpl implements CruiseLogServer 
{
	private Dao dao;
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public PageModel<CruiseLogBean> findByScrollServer(int currentPage, int maxPage) { //

		//String hql="from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID order by l.approvalResult";
	  //   String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort order by d.reviewResult ";
		String hql="from CruiseLog c where c.state=1 order by c.startTime desc";
		System.out.println("hql="+hql); 		
		return this.dao.findByPageScroll(hql, currentPage, maxPage);
	}
	public PageModel<CruiseLogBean> findByScrollServer1(int currentPage, int maxPage,String content,List list) { //
       String hql="from CruiseLog c where ( c.cruiseLogLoaction like '%"+content+"%' ";
       for(int i=0;i<list.size();i++){
    	   hql=hql+" or ";
    	   String userid=list.get(i).toString(); 
    	   hql=hql+" c.cruiseLogUser like '%"+userid+"%' ";
       }
       hql=hql+") and c.state=1 order by c.startTime desc"; 	
		return this.dao.findByPageScroll1(hql, currentPage, maxPage,list,content);
	}
	public List<User> findByUsername(String content){
		String hql="from User where name like '%"+content+"%' ";
		System.out.println("hql="+hql); 
		return this.dao.findByUsername(hql);
	}
	
	public CruiseLogBean findBycruiseLogID(String cruiseLogID){
		String hql="from CruiseLog c where c.cruiseLogID="+cruiseLogID;
		System.out.println("hql="+hql);
		return this.dao.findBycruiseLogID(hql);
	}
	public List<IllegalBean> findIllegalList(String cruiseLogID){
		String hql="from Illegal i ,CruiseLogLink cl , Location l where i.illegalId=cl.illegalID and i.illegalLocation=l.locationID and cl.cruiseLogID="+cruiseLogID;
		return this.dao.findIllegalList(hql);
	}
	
	public List<CruiseLogBean> showCruiseLogUnfinish(){
		String hql="from CruiseLog c where c.state=0 order by c.startTime desc";
		return this.dao.showCruiseLogUnfinish(hql);
	}	
	public List<CruiseLogBean> showCruiseLogFinish(){
		String hql="from CruiseLog c where c.state=1 order by c.startTime desc";
		return this.dao.showCruiseLogUnfinish(hql);
	}
	public List<Location> showMap(String cruiseLogID){
		String hql="from CruiseLogLocationLink c , Location l where c.locationID=l.locationID and c.cruiseLogID="+cruiseLogID +"order by l.locationID";
		return this.dao.showMap(hql);
	}
	public List<CruiseLogBean> selectCruiseLogUnfinish(String content,List list){
		  String hql="from CruiseLog c where ( c.cruiseLogLoaction like '%"+content+"%' ";
	       for(int i=0;i<list.size();i++){
	    	   hql=hql+" or ";
	    	   String userid=list.get(i).toString(); 
	    	   hql=hql+" c.cruiseLogUser like '%"+userid+"%' ";
	       }
	       hql=hql+" ) and c.state=0 order by c.startTime desc";
	       System.out.println("hql="+hql);
	       return this.dao.showCruiseLogUnfinish1(hql,list,content);
	}
	public List<CruiseLogBean> selectCruiseLogFinish(String content,List list){
		  String hql="from CruiseLog c where ( c.cruiseLogLoaction like '%"+content+"%' ";
	       for(int i=0;i<list.size();i++){
	    	   hql=hql+" or ";
	    	   String userid=list.get(i).toString(); 
	    	   hql=hql+" c.cruiseLogUser like '%"+userid+"%' ";
	       }
	       hql=hql+" ) and c.state=1 order by c.startTime desc";
	       return this.dao.showCruiseLogUnfinish1(hql,list,content);
	}
	public List<IllegalBean> findIllegalList1(String illid){
		
		String hql="from Illegal i  , Location l where  i.illegalLocation=l.locationID and ( ";
		
	    String[] sub=illid.split(",");
	 
 	    for(int i=0;i<sub.length ;i++){

 	    	 if(i==0){
 	    		hql=hql+"i.illegalId="+sub[i];
 	    	 }else{
 	    	    hql=hql+" or i.illegalId="+sub[i];
 	    	 }
 	    	
 	     }
 	   hql=hql+") order by i.illegalId" ;	
		return this.dao.findIllegalList1(hql);
	}
	public String insert(CruiseLog cruiselog ){
		return dao.insert(cruiselog);
	}
	public void insert1(CruiseLogLink cruiseloglink ){
		dao.insert1(cruiseloglink);
	}
	public void insert2(CruiseLogLocationLink cruiseloglocationlink){
		dao.insert2(cruiseloglocationlink);
	}
	public void updateCruiseLog(CruiseLogBean cruiselogbean){
		//String hql="update User set Individuation="+Individuation+"where UserId="+userid;
		String hql="update CruiseLog c set c.cruiseLogUser='"+cruiselogbean.getCruiseLogUser()+"' ,c.cruiseLogLoaction='"+cruiselogbean.getCruiseLogLoaction()+"' ,c.shipName='"+cruiselogbean.getShipName()+"' ,c.startTime='"+cruiselogbean.getStartTime()+"' ,c.endTime='"+cruiselogbean.getEndTime()+"' ,c.state="+cruiselogbean.getState()+ " where c.cruiseLogID="+cruiselogbean.getCruiseLogID();
		dao.updateCruiseLog(hql);
	}
	public List<CruiseLogLink> findCruiseLogLink(int cruiseid,String illid){
		String hql="from CruiseLogLink c where c.cruiseLogID="+cruiseid +" and c.illegalID="+illid;
		return dao.findCruiseLogLink(hql);
	}
	public List<CruiseLogLocationLink> findCruiseLogLocationLink(int cruiseid,String locaid){
		String hql="from CruiseLogLocationLink c where c.cruiseLogID="+cruiseid +" and c.locationID="+locaid;
		return dao.findCruiseLogLocationLink(hql);
	}
	
	public String insertLocation(Location location ){
		return dao.insertLocation(location);
	}
	public void updateCruiseLogByID(String cruiseLogID){
		Date now = new Date();
		DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
	    String declareTime = d8.format(now);
	String hql="update CruiseLog c set c.endTime='"+declareTime+"' ,c.state=1 where c.cruiseLogID="+cruiseLogID;
	dao.updateCruiseLog(hql);
	}






	@Override
	public List<?> findIssuesByPid(int pid) {
		// TODO Auto-generated method stub
		return dao.findIssuesByPid(pid);
	}






	@Override
	public List<?> findChannelsByType(int sfgg) 
	{
		// TODO Auto-generated method stub
		return dao.findChannelsByType(sfgg);
	}


	@Override
	public void commitCruise(CruiseRecord cruiseRecord) {
		// TODO Auto-generated method stub
		dao.commitCruise(cruiseRecord);
	}


	@Override
	public void commitIssue(CruiseIssue cruiseIssue) {
		dao.commitIssue(cruiseIssue);
		
	}


	@Override
	public void save(Object obj) 
	{
		dao.save(obj);
		
	}


	@Override
	public void update(Object obj) {
		dao.update(obj);
		
	}

	@Override
	public List<?> findRecordsByUserid(int userid) {
		
		return dao.findRecordsByUserid(userid);
	}

	@Override
	public List<?> findIssuesByRid(int rid)
	{
		// TODO Auto-generated method stub
		return dao.findIssuesByRid(rid);
	}

	@Override
	public List<?> findEvidenceByIssueID(int rid) {
		// TODO Auto-generated method stub
		return dao.findEvidenceByIssueID(rid);
	}

	@Override
	public void delete(Object obj) {
		dao.delete(obj);
		
	}

	@Override
	public List<?> findIssuesByUser(int userid) {
		// TODO Auto-generated method stub
		return dao.findIssuesByUser(userid);
	}

	@Override
	public CruiseRecord getCruiseRecordByID(int id) 
	{
		// TODO Auto-generated method stub
		return dao.getCruiseRecordByID(id);
	}

	@Override
	public List<?> findCruiseToolsByTip(String tip) 
	{
		// TODO Auto-generated method stub
		return dao.findCruiseToolsByTip(tip);
	}
}
