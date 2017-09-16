package com.huzhouport.pushmsg.service.impl;

import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.pushmsg.dao.PushMsgDao;
import com.huzhouport.pushmsg.model.PushMsg;
import com.huzhouport.pushmsg.service.PushMsgService;

public class PushMsgServiceImpl extends BaseServiceImpl<PushMsg> implements
		PushMsgService {

	private PushMsgDao pushMsgDao;

	public void setPushMsgDao(PushMsgDao pushMsgDao) {
		this.pushMsgDao = pushMsgDao;
	}

	public void updatePushMsg(PushMsg msg) throws Exception {
		this.pushMsgDao.update(msg);
	}
	
	public void addPushMsg(PushMsg msg) throws Exception {
		this.pushMsgDao.save(msg);
	}
	
	public List<?> FindUnpushedMsg(int userid,int moduleid,int part) throws Exception {
		return this.pushMsgDao.FindUnpushedMsg(userid,moduleid,part);
	}

	public String Updatepushmsgstatus(PushMsg pushmsg) throws Exception {
		return this.pushMsgDao.Updatepushmsgstatus(pushmsg);
	}
	//修改消息状态 该已推送已读
	public String UpdatePushmsgstatusByInfor(PushMsg pushmsg) throws Exception{
		return this.pushMsgDao.UpdatePushmsgstatusByInfor(pushmsg);
	}
	// 查询某个用户未读的消息
	public List<?> FindUnpushedMsgByNoread(PushMsg pushmsg) throws Exception{
		return this.pushMsgDao.FindUnpushedMsgByNoread(pushmsg);
	}

	public List<?> getPushMsgDetail(PushMsg pushmsg) throws Exception {
		return this.pushMsgDao.getPushMsgDetail(pushmsg);
	}
	
	
	/**
	* 获得未推送未读消息列表, 获取所有未推送未读消息后，将消息设置为已推送未读
	* 供后台推送服务调用
	*/
	public List<Map<String, Object>> getUnPushedMsg(int moduler,int userid) {
		return this.pushMsgDao.getUnPushedMsg(moduler,userid);
	}

	/**
	 * 获得已推送未读消息数量
	 * 供9宫格调用显示是否有未读消息
	 */
	public int getPushedUnReadMsgCnt(int moduler,int userid) {
		return this.pushMsgDao.getPushedUnReadMsgCnt(moduler,userid);
	}
	
	/**
	 * 读取一条消息，如果消息不为已推送已读，将消息设置为已推送已读
	 * 供显示页显示使用
	 */
	public void readPushMsg(int moduler,int userid,int messageid){
		this.pushMsgDao.readPushMsg(moduler, userid, messageid);
	}
	
	/**
	 * 推送一条消息，将消息的状态从未推送（已读/未读）设置为已推送（已读/未读）
	 */
	public void pushPushMsg(int moduler,int userid,List<Integer> messageids){
		this.pushMsgDao.pushPushMsg(moduler, userid, messageids);
	}
	
	/**
	 * 获得某模块推送消息列表
	 */
	public Map<String,PushMsg> getPushMsgs(int moduler,int userid,List<Integer> messageids){
		return this.pushMsgDao.getPushMsgs(moduler, userid, messageids);
	}
	
	/**
	 * 获得某模块除ids外的推送消息
	 */
	public List<PushMsg> getPushMsgsExclude(int moduler,String userids, String messageids) {
		return this.pushMsgDao.getPushMsgsExclude(moduler, userids, messageids);
	}
}
