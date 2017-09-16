package com.huzhouport.knowledge.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huzhouport.knowledge.dao.Dao;
import com.huzhouport.knowledge.model.Knowledge;
import com.huzhouport.knowledge.model.KnowledgeKind;

import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;
import com.huzhouport.pushmsg.model.PushMsg;

public class DaoImpl extends HibernateDaoSupport implements Dao {

	public PageModel findByPageScroll(String hql, int firstPage, int maxPage,
			String action) {
		PageModel pm = new PageModel();
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);

		ArrayList list = (ArrayList) query.list();
		Iterator iterator1 = list.iterator();
		KnowledgeKind st;
		List<KnowledgeKind> list1 = new ArrayList<KnowledgeKind>();

		while (iterator1.hasNext()) {
			st = new KnowledgeKind();
			Object object = iterator1.next();
			KnowledgeKind k = (KnowledgeKind) object;
			st.setKindID(k.getKindID());
			st.setKindName(k.getKindName());
			st.setKindIndex(k.getKindIndex());
			list1.add(st);
		}
		pm.setRecordsDate(list1);
		pm.setAction(action);
		pm.setTotal(((Long) QueryCount.getQueryCount(hql, session)).intValue());
		pm.setCurrentPage(firstPage);
		pm.setManPage(maxPage);
		// System.out.println("333");

		session.clear();
		session.close();
		return pm;
	}

	public List<Knowledge> findByKindID(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();

		Query query = session.createQuery(hql);
		List list = query.list();
		session.clear();
		session.close();

		return list;

	}

	public PageModel showKnowLedgedian(String hql, int firstPage, int maxPage,
			String action) {
		System.out.println("hql=" + hql);
		PageModel pm = new PageModel();
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		System.out.println("hq3=" + hql);
		Query query = session.createQuery(hql);
		query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList list = (ArrayList) query.list();
		Iterator iterator1 = list.iterator();
		System.out.println("size=" + list.size());
		List<Knowledge> list1 = new ArrayList<Knowledge>();
		// for (int i=0;i<list.size();i++){
		while (iterator1.hasNext()) {
			Object object = iterator1.next();
			Knowledge k = (Knowledge) object;
			list1.add(k);
		}
		pm.setRecordsDate(list1);
		pm.setAction(action);

		Query q = session.createQuery(hql);

		try {
			pm.setTotal(q.list().size());
		} catch (Exception e) {
			pm.setTotal(0);
		}

		pm.setCurrentPage(firstPage);
		pm.setManPage(maxPage);
		session.clear();
		session.close();
		return pm;
	}

	public Knowledge findByknowledgeID(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List list = query.list();
		Knowledge knowledge = (Knowledge) list.get(0);
		session.clear();
		session.close();
		return knowledge;
	}

	public void addknowledge(KnowledgeKind knowledgekind) {
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(knowledgekind);
		session.getTransaction().commit();

		session.clear();
		session.close();
	}

	public List Listid(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<KnowledgeKind> list = query.list();

		List list1 = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			list1.add(list.get(i).getKindID());
			list1 = Listid1(list.get(i).getKindID(), list1, session);
		}

		session.clear();
		session.close();
		return list1;
	}

	public List<Integer> IdList(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<Integer> list = query.list();
		session.clear();
		session.close();
		return list;
	}

	//
	public List Listid1(int kindID, List list1, Session session) {
		String hql = "from KnowledgeKind k where k.kindIndex=" + kindID;
		// Session
		// session=this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		List<KnowledgeKind> list = query.list();
		for (int i = 0; i < list.size(); i++) {
			list1.add(list.get(i).getKindID());
			list1 = Listid1(list.get(i).getKindID(), list1, session);
		}
		return list1;
	}

	public void knowledgeAdd(Knowledge knowledge) 
	{
		knowledge.setDate(new Date());

		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(knowledge);
		session.getTransaction().commit();

		// add by Grond Start

		if (knowledge.getUserids() != null
				&& !knowledge.getUserids().equals("")) {
			String[] userids = knowledge.getUserids().split(",");

			for (String s : userids) {
				PushMsg pushMsg = new PushMsg();
				pushMsg.setPushmsgtime(new Date());
				pushMsg.setMsgstatus(1);
				pushMsg.setModulerid(2);
				pushMsg.setMessageid(knowledge.getKnowledgeID());

				System.out.println(s);
				int t = Integer.valueOf(s);
				pushMsg.setUserid(t);

				savapushMsgObject(pushMsg, session);
			}
		} else {

			PushMsg pushMsg = new PushMsg();
			pushMsg.setPushmsgtime(new Date());
			pushMsg.setMsgstatus(1);// 消息状态自定义：1未推送未读；2未推送已读，3已推送未读；4已推送已读；消息轮询主要是未推送的。
			pushMsg.setModulerid(2);// 模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息
			pushMsg.setMessageid(knowledge.getKnowledgeID());// 消息内容ID，是日程安排表、请假申请表、通知公告信息表的外键

			// 0表示推送所有的 -1表示推送内部 -2表示推送外部
			int kind = -knowledge.getIsLinkInfo();
			pushMsg.setUserid(kind);

			// pushMsg.setUserid(11);
			savapushMsgObject(pushMsg, session);
			// add by Grond End
			
			
		}
		session.clear();
		session.close();
	}

	// add by Grond Start

	public void savapushMsgObject(PushMsg pushMsg, Session session) {

		session.beginTransaction();
		session.save(pushMsg);
		session.getTransaction().commit();

	}

	// add by Grond End

	public void knowledgeUpdate(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
	}

	public void knowledgeDelete(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
	}

	public List<KnowledgeKind> newknowledgelist(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List list = query.list();
		session.clear();
		session.close();
		return list;
	}

	public List Listiddelete(String hql, int kindID) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		kinddelete(kindID, session);
		Query query = session.createQuery(hql);
		List<KnowledgeKind> list = query.list();
		List list1 = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			list1.add(list.get(i).getKindID());
			kinddelete(list.get(i).getKindID(), session);
			list1 = Listiddelete1(list.get(i).getKindID(), list1, session);
		}
		session.clear();
		session.close();
		return list1;
	}

	//
	public List Listiddelete1(int kindID, List list1, Session session) {
		String hql = "from KnowledgeKind k where k.kindIndex=" + kindID;
		Query query = session.createQuery(hql);
		List<KnowledgeKind> list = query.list();
		for (int i = 0; i < list.size(); i++) {
			list1.add(list.get(i).getKindID());
			kinddelete(list.get(i).getKindID(), session);
			list1 = Listiddelete1(list.get(i).getKindID(), list1, session);
		}
		return list1;
	}

	public void kinddelete(int id, Session session) {
		String hql = "delete Knowledge k where k.knowledgeID= " + id;
		// Session session =
		// this.getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public List<?> queryNotifyMsgList(int userid, int moduleid, int part,
			String date, String content) {
		String hql = "select pm.pushmsgid,pm.userid,pm.messageid,pm.modulerid,pm.msgstatus,pm.pushmsgtime,k.knowledgeID,k.knowledgeName,k.knowledgeIndex,k.partOfKind,k.isLink,k.isLinkInfo,k.knowledgeContent from Knowledge k,PushMsg pm where (pm.messageid=k.knowledgeID  and k.partOfKind="
				+ moduleid + " )";

		hql+=" and pm.modulerid=2 ";
		
		if (part == 0)
			hql += " and (k.isLinkInfo=2 or k.isLinkInfo=0)";
		else if (part == 1)
			hql += " and (k.isLinkInfo=1 or k.isLinkInfo=0)";

		hql += " and  (pm.userid=0";

		if (part == 0)
			hql += " or pm.userid=-2";
		else if (part == 1)
			hql += " or pm.userid=-1";

		if (userid > 0)
			hql += " or pm.userid=" + userid;

		if (content != null && !content.equals("")) {
			hql += ") and (k.knowledgeName like '%" + content
					+ "%' or k.knowledgeContent like '%" + content + "%'";
		}

		hql += ") ";
		if (date != null && !date.equals(""))
			hql += " and pm.pushmsgtime > '" + date + "'";
		hql += " order by pm.pushmsgtime desc";

		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		session.clear();
		session.close();
		List<Map<String, Object>> resutlList = toHashMap(list);

		for (Map<String, Object> o : resutlList) {
			if (o.get("msgstatus").toString().equals("1")
					&& Integer.valueOf(o.get("userid").toString()) > 0) {
				String updateHql = "update PushMsg pm set pm.msgstatus=3 where pm.pushmsgid="
						+ Integer.valueOf(o.get("pushmsgid").toString());

				update(updateHql);
			}
		}

		return resutlList;
	}

	public int queryUserOriUnreadNotifyCnt(int userid, int moduleid, int part,
			String date) {
		String hql = "select k from Knowledge k,PushMsg pm where (pm.messageid=k.knowledgeID and k.partOfKind="
				+ moduleid + " )  ";// (pm.userid=0";

		hql+=" and pm.modulerid=2 ";
		
		if (part == 0)
			hql += " and (k.isLinkInfo=2 or k.isLinkInfo=0)";
		else if (part == 1)
			hql += " and (k.isLinkInfo=1 or k.isLinkInfo=0)";

		// if (part == 0)
		// hql += " or pm.userid=-2";
		// else if (part == 1)
		// hql += " or pm.userid=-1";

		if (userid > 0)
			hql += " and ( pm.userid=" + userid;

		hql += ") and (pm.msgstatus=1 or pm.msgstatus=3)";
		if (date != null && !date.equals(""))
			hql += " and pm.pushmsgtime > '" + date + "'";
		hql += " order by pm.pushmsgtime desc";

		System.out.println(hql);

		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<?> list = query.list();

		int i = list.size();
		session.clear();
		session.close();
		return i;
	}

	public int queryUserOriUnreadAndUnPushNotifyCnt(int userid, int moduleid,
			int part, String date) {
		String hql = "select pm from Knowledge k,PushMsg pm where (pm.messageid=k.knowledgeID and k.partOfKind="
				+ moduleid + " ) ";// (pm.userid=0";

		hql+=" and pm.modulerid=2 ";
		
		if (part == 0)
			hql += " and (k.isLinkInfo=2 or k.isLinkInfo=0)";
		else if (part == 1)
			hql += " and (k.isLinkInfo=1 or k.isLinkInfo=0)";

		// if (part == 0)
		// hql += " or pm.userid=-2";
		// else if (part == 1)
		// hql += " or pm.userid=-1";

		if (userid > 0)
			hql += " and ( pm.userid=" + userid;

		hql += ") and (pm.msgstatus=1)";
		if (date != null && !date.equals(""))
			hql += " and pm.pushmsgtime > '" + date + "'";
		hql += " order by pm.pushmsgtime desc";

		System.out.println(hql);

		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<PushMsg> list = query.list();

		for (PushMsg p : list) {
			if (p.getMsgstatus() == 1 && p.getUserid() > 0) {
				p.setMsgstatus(3);
				update(p);

				System.out.println(p.getMessageid());
			}
		}

		int i = list.size();

		session.clear();
		session.close();
		return i;
	}

	public void update(PushMsg pushMsg) {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		Session session = hibernateTemplate.getSessionFactory().openSession();
		hibernateTemplate.update(pushMsg);
		session.clear();
		session.close();
	}

	public void update(String hql) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		session.clear();
		session.close();

	}

	public List<?> queryNonUserOriNotifyListAll(int userid, int part,
			String date) {
		String hql = "select pm.pushmsgid,pm.userid,pm.messageid,pm.modulerid,pm.msgstatus,pm.pushmsgtime,k.knowledgeID,k.knowledgeName,k.knowledgeIndex,k.partOfKind,k.isLink,k.isLinkInfo,k.knowledgeContent from Knowledge k,PushMsg pm where (pm.messageid=k.knowledgeID )";

		hql+=" and pm.modulerid=2 ";
		
		if (part == 0)
			hql += " and (k.isLinkInfo=2 or k.isLinkInfo=0)";
		else if (part == 1)
			hql += " and (k.isLinkInfo=1 or k.isLinkInfo=0)";

		hql += " and  (pm.userid=0";

		if (part == 0)
			hql += " or pm.userid=-2";
		else if (part == 1)
			hql += " or pm.userid=-1";

		if (userid > 0)
			hql += " or pm.userid=" + userid;

		hql += ") ";
		if (date != null && !date.equals(""))
			hql += " and pm.pushmsgtime > '" + date + "'";

		hql += " order by pm.pushmsgtime desc ";

		System.out.println(hql);

		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		session.clear();
		session.close();
		return toHashMap(list);

	}

	private List<Map<String, Object>> toHashMap(List<?> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (Object[] objects : (List<Object[]>) list) {
			Map<String, Object> temp = new HashMap<String, Object>();

			temp.put("pushmsgid", objects[0]);
			temp.put("userid", objects[1]);
			temp.put("messageid", objects[2]);
			temp.put("modulerid", objects[3]);
			temp.put("msgstatus", objects[4]);
			temp.put("pushmsgtime", objects[5]);

			temp.put("knowledgeID", objects[6]);
			temp.put("knowledgeName", objects[7]);
			temp.put("knowledgeIndex", objects[8]);
			temp.put("partOfKind", objects[9]);
			temp.put("isLink", objects[10]);
			temp.put("isLinkInfo", objects[11]);
			temp.put("knowledgeContent", objects[12]);

			result.add(temp);
		}

		return result;
	}

	public void addAllMessageToReaded(int userid, int moduleid) {
		
		String hql="update PushMsg pm set pm.msgstatus=4 where pm.userid="+userid+" and pm.modulerid="+moduleid;
		
		update(hql);
	}

	@Override
	public List<?> newsByTitle(String title) {
		
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		String hql="select k from Knowledge k where k.knowledgeName='"+title+"'";
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		session.clear();
		session.close();
		
		return list;
	}

	@Override
	public List<?> findAllIDs()
	{
		String hql="select u.userId from User u";
		return this.getHibernateTemplate().find(hql);
		
	}

	@Override
	public List<Integer> countforders(List forderids) 
	{
		List<Integer> countlist=new ArrayList();
		for(int i=0;i<forderids.size();i++)
		{
			String hql="select k from Knowledge k,PushMsg pm where pm.messageid=k.knowledgeID and k.partOfKind="+forderids.get(i)+" and (pm.userid=0 or pm.userid=-1)";
			List<?> list=this.getHibernateTemplate().find(hql);
			countlist.add(list.size());
		}
		
		return countlist;
	}
}
