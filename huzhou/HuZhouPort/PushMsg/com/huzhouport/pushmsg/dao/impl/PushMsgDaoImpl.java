package com.huzhouport.pushmsg.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.pushmsg.dao.PushMsgDao;
import com.huzhouport.pushmsg.model.PushMsg;

public class PushMsgDaoImpl extends BaseDaoImpl<PushMsg> implements PushMsgDao {

	// 查询所有未推送的消息
	public List<?> FindUnpushedMsg(int userid, int moduleid, int part)
			throws Exception {

		String hql = "from PushMsg as pm where (pm.msgstatus=1 or pm.msgstatus=3) and pm.modulerid="
				+ moduleid + " and (pm.userid=0";

		if (part == 0)
			hql += " or pm.userid=-2";
		else if (part == 1)
			hql += " or pm.userid=-1";

		if (userid > 0)
			hql += " or pm.userid=" + userid;
		hql += ")";

		return this.hibernateTemplate.find(hql);
	}

	// 根据ID修改消息状态
	public String Updatepushmsgstatus(PushMsg pushmsg) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("msgstatus", String.valueOf(pushmsg.getMsgstatus()));
		conditionMap.put("pushmsgid", String.valueOf(pushmsg.getPushmsgid()));
		this.modifyByConditions(pushmsg, map, conditionMap, null);
		return null;
	}

	// 修改消息状态 该已推送已读
	public String UpdatePushmsgstatusByInfor(PushMsg pushmsg) throws Exception {
		String hql = "update PushMsg p set p.msgstatus=4 where ";
		if (pushmsg.getModulerid() != 0) {
			hql += " p.modulerid=" + pushmsg.getPushmsgid() + " and";
		} else {
			hql += " (p.modulerid=1 or p.modulerid=4) and";
		}
		if (pushmsg.getPushmsgid() != 0) {
			hql += " p.pushmsgid=" + pushmsg.getPushmsgid();
		}
		if (pushmsg.getMessageid() != 0) {
			if (pushmsg.getPushmsgid() != 0)
				hql += "and";
			hql += " p.messageid=" + pushmsg.getMessageid();
		}
		if (pushmsg.getMsgstatus() != 0) {
			if (pushmsg.getPushmsgid() != 0 || pushmsg.getMessageid() != 0)
				hql += "and";
			hql += " p.msgstatus=" + pushmsg.getMsgstatus();
		}
		if (pushmsg.getUserid() != 0) {
			if (pushmsg.getPushmsgid() != 0 || pushmsg.getMessageid() != 0
					|| pushmsg.getMsgstatus() != 0)
				hql += "and";
			hql += " p.userid=" + pushmsg.getUserid();
		}
		this.hibernateTemplate.bulkUpdate(hql);
		return null;
	}

	// 查询某个用户未读的消息
	public List<?> FindUnpushedMsgByNoread(PushMsg pushmsg) throws Exception {
		String hql = "from PushMsg as pm where (pm.msgstatus=1 or pm.msgstatus=3) and (pm.userid="
				+ pushmsg.getUserid() + " and pm.userid=0)";
		return this.hibernateTemplate.find(hql);
	}

	public List<?> getPushMsgDetail(PushMsg pushmsg) throws Exception {
		String hql = "from PushMsg as pm where pm.modulerid="
				+ pushmsg.getModulerid() + " and pm.messageid="
				+ pushmsg.getMessageid() + " and pm.userid="
				+ pushmsg.getUserid();
		return this.hibernateTemplate.find(hql);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 获得未推送未读消息列表, 获取所有未推送未读消息后，将消息设置为已推送未读 供后台推送服务调用
	 */
	public List<Map<String, Object>> getUnPushedMsg(int moduler, int userid) {
		String hql = "";

		if (moduler == PushMsg.MODULERID_LEAVEANDOVERTIME) {
			hql += "select a,b from LeaveOrOt a, PushMsg b where a.leaveOrOtID = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_LEAVEANDOVERTIME;
		} else if (moduler == PushMsg.MODULERID_MEETING) {
			hql += "select a,b from ScheduleEvent a, PushMsg b where a.eventId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_MEETING;
		} else if (moduler == PushMsg.MODULERID_SCHEDULE) {
			hql += "select a,b from ScheduleEvent a, PushMsg b where a.eventId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_SCHEDULE;
		} else if (moduler == PushMsg.MODULERID_ILLEGAL) {
			hql += "select a,b from Illegal a, PushMsg b where a.illegalId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_ILLEGAL;
		} else
			return new ArrayList<Map<String, Object>>();

		hql += " and b.userid=" + userid;
		hql += " and b.msgstatus=" + PushMsg.MSGSTATUS_NOTPUSH_NOTREAD;

		/*Session session = this.hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		session.clear();
		session.close();*/
		List<?> list=hibernateTemplate.find(hql);

		// 设置为已推送已读
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			PushMsg msg = (PushMsg) objs[1];
			msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_NOTREAD);
			this.hibernateTemplate.update(msg);
		}
		

		return tomap(list);
	}

	/**
	 * 获得已推送未读消息数量 供9宫格调用显示是否有未读消息
	 */
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd"); 
	public int getPushedUnReadMsgCnt(int moduler, int userid) {
		String hql = "";

		if (moduler == PushMsg.MODULERID_LEAVEANDOVERTIME) {
			hql += "select a,b from LeaveOrOt a, PushMsg b where a.leaveOrOtID = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_LEAVEANDOVERTIME;
		} else if (moduler == PushMsg.MODULERID_MEETING) {
			hql += "select a,b from ScheduleEvent a, PushMsg b where a.eventId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_MEETING+" and a.eventTime like '"+simpleDateFormat.format(new Date())+"'";
		} else if (moduler == PushMsg.MODULERID_SCHEDULE) {
			hql += "select a,b from ScheduleEvent a, PushMsg b where a.eventId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_SCHEDULE;
		} else if (moduler == PushMsg.MODULERID_ILLEGAL) {
			hql += "select a,b from Illegal a, PushMsg b where a.illegalId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_ILLEGAL;
		} else
			return 0;

		hql += " and b.userid=" + userid;
		hql += " and b.msgstatus=" + PushMsg.MSGSTATUS_PUSHED_NOTREAD;

		/*Session session = this.hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		session.clear();
		session.close();*/
		
		List<?> list=hibernateTemplate.find(hql);

		return list.size();
	}

	/**
	 * 读取一条消息，如果消息不为已推送已读，将消息设置为已推送已读 供显示页显示使用
	 */
	public void readPushMsg(int moduler, int userid, int messageid) {
		String hql = "";

		if (moduler == PushMsg.MODULERID_LEAVEANDOVERTIME) {
			hql += "select a,b from LeaveOrOt a, PushMsg b where a.leaveOrOtID = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_LEAVEANDOVERTIME;
			hql += " and b.messageid=" + messageid;
		} else if (moduler == PushMsg.MODULERID_MEETING) {
			hql += "select a,b from ScheduleEvent a, PushMsg b where a.eventId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_MEETING;
			hql += " and b.messageid=" + messageid;
			hql += " and b.userid=" + userid;
		} else if (moduler == PushMsg.MODULERID_SCHEDULE) {
			hql += "select a,b from ScheduleEvent a, PushMsg b where a.eventId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_SCHEDULE;
			hql += " and b.messageid=" + messageid;
			hql += " and b.userid=" + userid;
		} else if (moduler == PushMsg.MODULERID_ILLEGAL) {
			hql += "select a,b from Illegal a, PushMsg b where a.illegalId = b.messageid";
			hql += " and b.modulerid=" + PushMsg.MODULERID_ILLEGAL;
			hql += " and b.messageid=" + messageid;
		} else
			return;

		/*Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		Query query = session.createQuery(hql);
		List<?> list = query.list();
		session.clear();
		session.close();*/
		List<?> list=hibernateTemplate.find(hql);
		

		if (list.size() > 0) {
			Object[] objs = (Object[]) list.get(0);
			PushMsg msg = (PushMsg) objs[1];
			if (msg.getMsgstatus() != PushMsg.MSGSTATUS_PUSHED_READ) {
				msg.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_READ);
				this.hibernateTemplate.update(msg);
			}
		}
		
	}

	/**
	 * 推送多条消息，将消息的状态从未推送（已读/未读）设置为已推送（已读/未读）
	 */
	public void pushPushMsg(int moduler, int userid, List<Integer> messageids) {

		if (messageids == null || messageids.size() == 0)
			return;

		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();

		String sql = "";
		String msgidsStr = "";

		for (int i = 0; i < messageids.size(); i++) {
			msgidsStr += messageids.get(i) + ",";
		}
		if (!msgidsStr.equals(""))
			msgidsStr = msgidsStr.substring(0, msgidsStr.length() - 1);

		if (moduler == PushMsg.MODULERID_LEAVEANDOVERTIME) {
			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_NOTREAD
					+ " where  a.modulerid = "
					+ PushMsg.MODULERID_LEAVEANDOVERTIME
					+ " and a.messageid in (" + msgidsStr + ")"
					+ " and a.msgstatus = " + PushMsg.MSGSTATUS_NOTPUSH_NOTREAD;

			session.createSQLQuery(sql).executeUpdate();

			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_READ + " where  a.modulerid = "
					+ PushMsg.MODULERID_LEAVEANDOVERTIME
					+ " and a.messageid in (" + msgidsStr + ")"
					+ " and a.msgstatus = " + PushMsg.MSGSTATUS_NOTPUSH_READ;
			session.createSQLQuery(sql).executeUpdate();

		} else if (moduler == PushMsg.MODULERID_MEETING) {
			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_NOTREAD
					+ " where  a.modulerid = " + PushMsg.MODULERID_MEETING
					+ " and a.messageid in (" + msgidsStr + ")"
					+ " and a.msgstatus = " + PushMsg.MSGSTATUS_NOTPUSH_NOTREAD
					+ " and a.userid = " + userid;
			session.createSQLQuery(sql).executeUpdate();

			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_READ + " where  a.modulerid = "
					+ PushMsg.MODULERID_MEETING + " and a.messageid in ("
					+ msgidsStr + ")" + " and a.msgstatus = "
					+ PushMsg.MSGSTATUS_NOTPUSH_READ + " and a.userid = "
					+ userid;
			session.createSQLQuery(sql).executeUpdate();
		} else if (moduler == PushMsg.MODULERID_SCHEDULE) {
			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_NOTREAD
					+ " where  a.modulerid = " + PushMsg.MODULERID_SCHEDULE
					+ " and a.messageid in (" + msgidsStr + ")"
					+ " and a.msgstatus = " + PushMsg.MSGSTATUS_NOTPUSH_NOTREAD
					+ " and a.userid = " + userid;

			session.createSQLQuery(sql).executeUpdate();

			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_READ + " where  a.modulerid = "
					+ PushMsg.MODULERID_SCHEDULE + " and a.messageid in ("
					+ msgidsStr + ")" + " and a.msgstatus = "
					+ PushMsg.MSGSTATUS_NOTPUSH_READ + " and a.userid = "
					+ userid;
			session.createSQLQuery(sql).executeUpdate();
		} else if (moduler == PushMsg.MODULERID_ILLEGAL) {
			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_NOTREAD
					+ " where  a.modulerid = " + PushMsg.MODULERID_ILLEGAL
					+ " and a.messageid in (" + msgidsStr + ")"
					+ " and a.msgstatus = " + PushMsg.MSGSTATUS_NOTPUSH_NOTREAD;

			session.createSQLQuery(sql).executeUpdate();

			sql = "update mm_pushmsg a set a.msgstatus = "
					+ PushMsg.MSGSTATUS_PUSHED_READ + " where  a.modulerid = "
					+ PushMsg.MODULERID_ILLEGAL + " and a.messageid in ("
					+ msgidsStr + ")" + " and a.msgstatus = "
					+ PushMsg.MSGSTATUS_NOTPUSH_READ;
			session.createSQLQuery(sql).executeUpdate();
		}

		session.clear();
		session.close();
	}

	/**
	 * 获得某模块推送消息列表
	 */
	public Map<String, PushMsg> getPushMsgs(int moduler, int userid,
			List<Integer> messageids) {
		if (messageids == null || messageids.size() == 0)
			return new HashMap<String, PushMsg>();

		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();

		String hql = "select b from PushMsg b where";
		String msgidsStr = "";

		for (int i = 0; i < messageids.size(); i++) {
			msgidsStr += messageids.get(i) + ",";
		}
		if (!msgidsStr.equals(""))
			msgidsStr = msgidsStr.substring(0, msgidsStr.length() - 1);

		if (moduler == PushMsg.MODULERID_LEAVEANDOVERTIME) {
			hql += " b.modulerid=" + PushMsg.MODULERID_LEAVEANDOVERTIME;
			hql += " and b.messageid in(" + msgidsStr + ")";
		} else if (moduler == PushMsg.MODULERID_MEETING) {
			hql += " b.modulerid=" + PushMsg.MODULERID_MEETING;
			hql += " and b.messageid in(" + msgidsStr + ")";
		} else if (moduler == PushMsg.MODULERID_SCHEDULE) {
			hql += " b.modulerid=" + PushMsg.MODULERID_SCHEDULE;
			hql += " and b.messageid in(" + msgidsStr + ")";
		} else if (moduler == PushMsg.MODULERID_ILLEGAL) {
			hql += " b.modulerid=" + PushMsg.MODULERID_ILLEGAL;
			hql += " and b.messageid in(" + msgidsStr + ")";
		} else if (moduler == PushMsg.MODULERID_INDUSTRYINFO) {
			hql += " b.modulerid=" + PushMsg.MODULERID_INDUSTRYINFO;
			hql += " and b.messageid in(" + msgidsStr + ")";
		} else
			return new HashMap<String, PushMsg>();

		@SuppressWarnings("unchecked")
		List<PushMsg> msgs = session.createQuery(hql).list();

		session.clear();
		session.close();

		Map<String, PushMsg> mapMsgs = new HashMap<String, PushMsg>();
		for (int i = 0; i < msgs.size(); i++) {
			mapMsgs.put("" + msgs.get(i).getMessageid(), msgs.get(i));
		}
		return mapMsgs;
	}

	/**
	 * 获得某模块除ids外的推送消息
	 */
	public List<PushMsg> getPushMsgsExclude(int moduler, String userids,
			String messageids) {

		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();

		String hql = "select b from PushMsg b where";
		
		hql += " b.modulerid=" + moduler;

		if (userids != null && !userids.equals(""))
			hql += " and b.userid in (" + userids + ")";
			
		if (messageids != null && !messageids.equals(""))
			hql += " and b.messageid not in(" + messageids + ")";
		
		
		@SuppressWarnings("unchecked")
		List<PushMsg> msgs = session.createQuery(hql).list();

		session.clear();
		session.close();

		return msgs;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> tomap(List<?> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (Object[] objects : (List<Object[]>) list) {
			Map<String, Object> temp = new HashMap<String, Object>();

			temp.put("leaveorot", objects[0]);
			temp.put("pushmsg", objects[1]);

			result.add(temp);
		}
		return result;
	}

}
