package com.huzhouport.knowledge.dao;



import java.util.Date;
import java.util.List;

import com.huzhouport.knowledge.model.Knowledge;
import com.huzhouport.knowledge.model.KnowledgeKind;
import com.huzhouport.log.model.PageModel;

public interface Dao 
{
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage,String action);  //f分页
	public List<Knowledge> findByKindID(String hql);
	public PageModel showKnowLedgedian(String hql,int firstPage,int maxPage ,String action); 
	public Knowledge findByknowledgeID(String  hql);
	public void addknowledge(KnowledgeKind knowledgekind);
	public List Listid(String hql);
	public List<Integer> IdList(String hql) ;
	 public void knowledgeAdd(Knowledge knowledge);
	 public void knowledgeUpdate(String hql);
	 public void knowledgeDelete(String hql);
	 public List<KnowledgeKind> newknowledgelist(String  hql);
	 public List Listiddelete(String hql,int kindID);
	 
	 public List<?> queryNotifyMsgList(int userid, int moduleid, int part,String date,String content);
	 public int queryUserOriUnreadNotifyCnt(int userid,int moduleid,int part,String date);
	 public List<?> queryNonUserOriNotifyListAll(int userid,int part,String date);
	 
	 public int queryUserOriUnreadAndUnPushNotifyCnt(int userid,int moduleid,int part,String date);
	 
	 public void addAllMessageToReaded(int userid,int moduleid);
	 public List<?> newsByTitle(String title);
	 public List<?> findAllIDs();
	 public List<Integer> countforders(List forderids);
}
