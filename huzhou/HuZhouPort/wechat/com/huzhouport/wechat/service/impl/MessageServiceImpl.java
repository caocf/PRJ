package com.huzhouport.wechat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.wechat.dao.MessageDao;
import com.huzhouport.wechat.model.Message;
import com.huzhouport.wechat.service.MessageService;

public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService{
	private MessageDao messageDao;
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public String addMessageInfo(Message message) throws Exception {
		this.messageDao.addMessage(message);
		return null;
	}

	public Map<String, Integer> countPageMessageInfo(Message message,
			int pageSize) throws Exception {
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.messageDao.countPageMessageInfo(message, qcs
				.QCS(message.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchMessageInfo(Message message, int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.messageDao.searchMessageInfo(message, qcs.QCS(message
				.getQueryCondition()), qcs.Sequence(message
				.getQueryCondition()), beginIndex, pageSize);

	}

}
