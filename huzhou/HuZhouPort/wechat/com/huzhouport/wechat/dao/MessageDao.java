package com.huzhouport.wechat.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.wechat.model.Message;
public interface MessageDao extends BaseDao<Message>{
	
	//保存角色和它的权限
	public String addMessage(Message message)throws Exception;
	
	//查询信息
	public List<?> searchMessageInfo(Message message,String condition,String sequence,int startSet, int maxSet) throws Exception;
	//查询条数
	public int countPageMessageInfo(Message message, String condition)throws Exception;

}
