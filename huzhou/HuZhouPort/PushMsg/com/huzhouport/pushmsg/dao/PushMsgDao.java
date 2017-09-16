package com.huzhouport.pushmsg.dao;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.pushmsg.model.PushMsg;

public interface PushMsgDao extends BaseDao<PushMsg> {

	// 增加

	public List<?> FindUnpushedMsg(int userid,int moduleid,int part) throws Exception;

	public String Updatepushmsgstatus(PushMsg pushmsg) throws Exception;
	//修改消息状态 该已推送已读
	public String UpdatePushmsgstatusByInfor(PushMsg pushmsg) throws Exception;
	// 查询某个用户未读的消息
	public List<?> FindUnpushedMsgByNoread(PushMsg pushmsg) throws Exception;

	public List<?> getPushMsgDetail(PushMsg pushmsg) throws Exception;
	
	 /**
	 * 获得未推送未读消息列表, 获取所有未推送未读消息后，将消息设置为已推送未读
	 * 供后台推送服务调用
	 */
	public List<Map<String, Object>> getUnPushedMsg(int moduler,int userid);
	
	
	/**
	 * 获得已推送未读消息数量
	 * 供9宫格调用显示是否有未读消息
	 */
	public int getPushedUnReadMsgCnt(int moduler,int userid);
	
	/**
	 * 读取一条消息，如果消息不为已推送已读，将消息设置为已推送已读
	 * 供显示页显示使用
	 */
	public void readPushMsg(int moduler,int userid,int messageid);
	
	/**
	 * 推送一条消息，将消息的状态从未推送（已读/未读）设置为已推送（已读/未读）
	 */
	public void pushPushMsg(int moduler,int userid,List<Integer> messageids);
	
	/**
	 * 获得某模块推送消息列表
	 */
	public Map<String,PushMsg> getPushMsgs(int moduler,int userid,List<Integer> messageids);
	
	/**
	 * 获得某模块除ids外的推送消息
	 */
	public List<PushMsg> getPushMsgsExclude(int moduler,String userids, String messageids);
}
