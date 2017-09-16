package com.huzhouport.wechat.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.wechat.dao.MessageDao;
import com.huzhouport.wechat.model.Message;

public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao{

	public String addMessage(Message message) throws Exception {
		this.hibernateTemplate.save(message);
		return null;
	}

	public int countPageMessageInfo(Message message, String condition)
			throws Exception {
		String hql = "select count(*) from Message m ";
		if (condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(message, hql);
	}

	public List<?> searchMessageInfo(Message message, String condition,
			String sequence, int startSet, int maxSet) throws Exception {
		String hql = " from Message m " ;
		if (condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(message, hql, startSet, maxSet);
		return list;
	}

}
