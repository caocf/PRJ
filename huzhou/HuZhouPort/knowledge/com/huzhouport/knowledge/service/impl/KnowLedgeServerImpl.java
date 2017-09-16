package com.huzhouport.knowledge.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.knowledge.dao.Dao;
import com.huzhouport.knowledge.model.Knowledge;
import com.huzhouport.knowledge.model.KnowledgeKind;
import com.huzhouport.knowledge.service.KnowLedgeServer;
import com.huzhouport.log.model.PageModel;

public class KnowLedgeServerImpl implements KnowLedgeServer {
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public PageModel<KnowledgeKind> findByScrollServer(int currentPage,
			int maxPage, String action) { // 知识库显示
		String hql = "select k from KnowledgeKind k where k.kindName!='其他公告' order by k.kindIndex desc";
		return this.dao.findByPageScroll(hql, currentPage, maxPage, action);
	}

	@SuppressWarnings("unchecked")
	public PageModel<KnowledgeKind> findByScrollServer1(int currentPage,
			int maxPage, String action, String content) {

		String hql = "from KnowledgeKind k  where kindName like '%" + content
				+ "%' order by k.kindIndex";
		return this.dao.findByPageScroll(hql, currentPage, maxPage, action);
	}

	public List<Knowledge> findByKindID(int kindID) {
		String hql = "from Knowledge k where k.isLinkInfo!=2 and partOfKind="
				+ kindID;
		return this.dao.findByKindID(hql);
	}

	public List<Knowledge> findByKindIDpublic(int kindID) {
		String hql = "from Knowledge k where k.isLinkInfo!=1 and partOfKind="
				+ kindID;
		return this.dao.findByKindID(hql);
	}

	public List<Knowledge> findByKindID1(int kindID) {
		String hql = "from Knowledge k where partOfKind=" + kindID
				+ " and isLink=1";
		System.out.println("hql=" + hql);
		return this.dao.findByKindID(hql);
	}

	public PageModel<Knowledge> showKnowLedgedian(int currentPage, int maxPage,
			String action, int kindID, List list, int userid) {
		String hql = "select distinct k from Knowledge k,PushMsg p where (k.partOfKind="
				+ kindID;
		for (int i = 0; i < list.size(); i++) {
			String kindid = list.get(i).toString();
			hql = hql + " or partOfKind=" + kindid;
		}

		if (userid == -1)
			hql += ")";
		else
			hql += " ) and (p.userid=" + userid + " or p.userid<=0)";

		hql += " and p.messageid=k.knowledgeID";

		hql += " order by k.knowledgeID desc";
		return this.dao.showKnowLedgedian(hql, currentPage, maxPage, action);
	}

	public PageModel<Knowledge> selectKnowLedgedian(int currentPage,
			int maxPage, String action, int kindID, String content, List list,
			int userid) {
		String hql = "select k from Knowledge k,PushMsg p where (k.knowledgeName like '%"
				+ content
				+ "%' or k.knowledgeIndex like '%"
				+ content
				+ "%') and (partOfKind=" + kindID;
		for (int i = 0; i < list.size(); i++) {
			String kindid = list.get(i).toString();
			hql = hql + " or partOfKind=" + kindid;
		}
		
		if (userid == -1)
			hql += ")";
		else
			hql += " ) and (p.userid=" + userid + " or p.userid<=0)";

		hql += " and p.messageid=k.knowledgeID order by k.date desc";

		return this.dao.showKnowLedgedian(hql, currentPage, maxPage, action);
	}
	
	public boolean hasSameKnowledgeName(String name,int kindid)
	{
		String hql="from KnowledgeKind k where k.kindName='"+name+"'";
		if(kindid!=0){
			hql+=" and k.kindID!="+kindid;
		}
		List<?> dateList=this.dao.newknowledgelist(hql);
		
		if(dateList==null)
			return false;
		else if(dateList.size()>0)
			return true;
		
		return false;
	}

	

	public Knowledge findByknowledgeID(int knowledgeID) {
		String hql = "from Knowledge k where   k.knowledgeID=" + knowledgeID;
		return dao.findByknowledgeID(hql);
	}

	public void addknowledge(KnowledgeKind knowledgekind) {
		dao.addknowledge(knowledgekind);
	}

	public void updateknowledge(KnowledgeKind knowledgekind) {
		String hql = "update KnowledgeKind k set k.kindName='"
				+ knowledgekind.getKindName() + "' , k.kindIndex='"
				+ knowledgekind.getKindIndex() + "' where k.kindID="
				+ knowledgekind.getKindID();
		dao.knowledgeUpdate(hql);
	}

	public List Listid(int kindID) {
		String hql = "from KnowledgeKind k where k.kindIndex=" + kindID;
		return this.dao.Listid(hql);
	}

	public void knowledgeAdd(Knowledge knowledge) {
		dao.knowledgeAdd(knowledge);
	}

	public void knowledgeUpdate(Knowledge knowledge) {
		String hql = "update Knowledge k set k.knowledgeName='"
				+ knowledge.getKnowledgeName() + "' ,k.knowledgeIndex='"
				+ knowledge.getKnowledgeIndex() + "' ,k.partOfKind="
				+ knowledge.getPartOfKind() + ", k.isLink="
				+ knowledge.getIsLink() + ", k.knowledgeContent='"
				+ knowledge.getKnowledgeContent() + "' where k.knowledgeID="
				+ knowledge.getKnowledgeID();
		dao.knowledgeUpdate(hql);
	}

	public void knowledgeDelete(int knowledgeID) {
		String hql = "delete Knowledge k where k.knowledgeID= " + knowledgeID;
		dao.knowledgeDelete(hql);
	}

	public List<KnowledgeKind> newknowledgelist(int kindID) {
		String hql = "from KnowledgeKind k where k.kindName!='其他公告' and k.kindIndex="
				+ kindID;
		return this.dao.newknowledgelist(hql);
	}

	public List<KnowledgeKind> queryFolderStructure(int id) {
		String hql;

		hql = "from KnowledgeKind k where k.kindIndex=" + id;
		return this.dao.newknowledgelist(hql);
	}

	public List<KnowledgeKind> newknowledgelistall(int kindID) {

		String hql = "from KnowledgeKind k where k.kindName!='其他公告' and k.kindIndex="
				+ kindID;
		return this.dao.newknowledgelist(hql);

	}

	public List<KnowledgeKind> newknowledgelistpublic(int kindID) {
		String hql = "from KnowledgeKind k where  k.kindIndex=" + kindID;
		return this.dao.newknowledgelist(hql);
	}

	public List<Knowledge> selectknowledgelist(String content) {
		try {
			content = new String(content.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from Knowledge k where k.isLinkInfo != 1 and (k.knowledgeName like '%"
				+ content + "%' or k.knowledgeIndex like '%" + content + "%')";
		return this.dao.findByKindID(hql);
	}

	public List<Knowledge> selectknowledgelistinner(String content) {
		try {
			content = new String(content.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from Knowledge k where k.isLinkInfo != 2 and (k.knowledgeName like '%"
				+ content + "%' or k.knowledgeIndex like '%" + content + "%')";
		return this.dao.findByKindID(hql);
	}

	// k.isLinkInfo!=2 and
	public List Listiddelete(int kindID) {
		String hql = "from KnowledgeKind k where k.kindIndex=" + kindID;

		return this.dao.Listiddelete(hql, kindID);
	}

	public void deleteknowledgekind(int kindID, List list) {
		String hql = "delete KnowledgeKind k where ( k.kindID= " + kindID;
		for (int i = 0; i < list.size(); i++) {
			String kindid = list.get(i).toString();
			hql = hql + " or kindID=" + kindid;
		}
		hql = hql + ")";
		dao.knowledgeDelete(hql);
	}
	private List<Integer> IDlist;
	public void FindNextKind(List<Integer> did){
		String hql="select k.kindID from KnowledgeKind k where k.kindIndex="+did.get(0);
		for(int i=1;i<did.size();i++){
			hql+=" or k.kindIndex="+did.get(i);
		}
		List<Integer> list=dao.IdList(hql);
		if(list!=null){
			IDlist.addAll(list);
			if(list.size()>0){
				FindNextKind(list);
			}
		}
		
	}	
	public void deleteknowledgekind(int kindID) {
		IDlist=new ArrayList<Integer>();
		IDlist.add(kindID);
		FindNextKind(IDlist);
		String hql1 = "delete Knowledge k where k.partOfKind=" + IDlist.get(0);
		String content="";
		for(int i=1;i<IDlist.size();i++){
			hql1+=" or k.partOfKind= "+IDlist.get(i);
			content+=" or k.kindID= "+IDlist.get(i);
		}
		dao.knowledgeDelete(hql1);
		String hql = "delete KnowledgeKind k where k.kindID= " + IDlist.get(0)+content;
		dao.knowledgeDelete(hql);
	}

	public List<?> queryNotifyMsgList(int userid, int moduleid, int part,
			String date,String content) {

		return dao.queryNotifyMsgList(userid, moduleid, part, date,content);
	}

	public int queryUserOriUnreadNotifyCnt(int userid, int moduleid, int part,
			String date) {

		return dao.queryUserOriUnreadNotifyCnt(userid, moduleid, part, date);
	}

	public List<?> queryNonUserOriNotifyListAll(int userid, int part,
			String date) {
		return dao.queryNonUserOriNotifyListAll(userid, part, date);
	}

	public int queryUserOriUnreadAndUnPushNotifyCnt(int userid,int moduleid,int part,String date)
	{
		return dao.queryUserOriUnreadAndUnPushNotifyCnt( userid, moduleid, part, date);
	}

	public void addAllMessageToReaded(int userid, int moduleid) {
		this.dao.addAllMessageToReaded(userid, moduleid);
	}

	@Override
	public List<?> newsByTitle(String title) 
	{
		return dao.newsByTitle(title);
		
	}

	@Override
	public List<?> findAllIDs()
	{
		// TODO Auto-generated method stub
		return dao.findAllIDs();
	}

	@Override
	public List<Integer> countforders(List forderids) {
		// TODO Auto-generated method stub
		return dao.countforders(forderids);
	}
}
