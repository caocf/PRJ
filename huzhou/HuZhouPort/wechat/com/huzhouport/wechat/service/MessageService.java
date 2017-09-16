package com.huzhouport.wechat.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.wechat.model.Message;

public interface MessageService extends BaseService<Message>{
	
	//增加
	public String addMessageInfo(Message message) throws Exception;
	
	//查询Message信息
	public List<?> searchMessageInfo(Message message,int pageNo, int pageSize) throws Exception;
	
	//查询条数
	public Map<String,Integer> countPageMessageInfo(Message message,int pageSize)throws Exception;

}
