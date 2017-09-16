package com.huzhouport.electric.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electric.dao.ChannelDao;
import com.huzhouport.electric.model.Channel;

public class ChannelDaoImpl extends BaseDaoImpl<Channel> implements ChannelDao {

	public int countPageChannelInfo(Channel channel, String condition)
			throws Exception {
		String hql = "select count(*) from Channel c ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		return this.countEForeignKey(channel, hql);
	}

	public List<?> searchChannelInfo(Channel channel, String condition,
			String sequence, int startSet, int maxSet) throws Exception {
		String hql = "  from Channel c ";
		if (null != condition && condition != "") {
			hql += "where (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(channel, hql, startSet,
				maxSet);
		return list;
	}

}
