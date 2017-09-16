package com.huzhouport.knowledge.service;

import java.util.Date;
import java.util.List;

import com.huzhouport.knowledge.model.Knowledge;
import com.huzhouport.knowledge.model.KnowledgeKind;

import com.huzhouport.log.model.PageModel;

public interface KnowLedgeServer {

	public PageModel<KnowledgeKind> findByScrollServer(int currentPage,
			int maxPage, String action); // 知识库显示

	public PageModel<KnowledgeKind> findByScrollServer1(int currentPage,
			int maxPage, String action, String content); // 知识库显示

	public List<Knowledge> findByKindID(int kindID);

	public List<Knowledge> findByKindIDpublic(int kindID);

	public List<Knowledge> findByKindID1(int kindID);

	public PageModel<Knowledge> showKnowLedgedian(int currentPage, int maxPage,
			String action, int kindID, List list,int userid); // 知识dian显示

	public PageModel<Knowledge> selectKnowLedgedian(int currentPage,
			int maxPage, String action, int kindID, String content, List list,int userid); // 知识dian显示

	public Knowledge findByknowledgeID(int knowledgeID);

	public void addknowledge(KnowledgeKind knowledgekind);

	public void updateknowledge(KnowledgeKind knowledgekind);

	public List Listid(int kindID);

	public void knowledgeAdd(Knowledge knowledge);

	public void knowledgeUpdate(Knowledge knowledge);

	public void knowledgeDelete(int knowledgeID);

	public List<KnowledgeKind> newknowledgelist(int kindID);
	
	public List<KnowledgeKind> newknowledgelistpublic(int kindID);

	public List<Knowledge> selectknowledgelist(String content);

	public List<Knowledge> selectknowledgelistinner(String content);

	public List Listiddelete(int kindID);

	public void deleteknowledgekind(int kindID, List list);

	public void deleteknowledgekind(int kindID);
	
	public boolean hasSameKnowledgeName(String name,int kindid);
	
	
	public List<KnowledgeKind> queryFolderStructure(int id);
	
	public List<?> queryNotifyMsgList(int userid,int moduleid,int part,String date,String content);
	
	public List<?> queryNonUserOriNotifyListAll(int userid,int part,String date);
	
	public int queryUserOriUnreadNotifyCnt(int userid,int moduleid,int part,String date);
	
	public int queryUserOriUnreadAndUnPushNotifyCnt(int userid,int moduleid,int part,String date);
	
	public void addAllMessageToReaded(int userid,int moduleid);
	public List<?> newsByTitle(String title);
	public List<?> findAllIDs();
	public List<Integer> countforders(List forderids);
}
