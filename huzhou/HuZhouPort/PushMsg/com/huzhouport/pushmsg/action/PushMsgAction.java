package com.huzhouport.pushmsg.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.pushmsg.model.PushMsg;
import com.huzhouport.pushmsg.service.PushMsgService;
import com.opensymphony.xwork2.ModelDriven;

public class PushMsgAction extends BaseAction implements ModelDriven<PushMsg> {
	private static final long serialVersionUID = 1L;

	private PushMsg pushMsg = new PushMsg();
	private PushMsgService pushMsgService;
	
	public static final String PUBLIC="public";
	public static final String INNER="inner";
	private int userid;
	private int modulerid;
	private String ori;
	
	public void setModulerid(int modulerid) {
		this.modulerid = modulerid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setOri(String ori) {
		this.ori = ori;
	}
	private List<?> list;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public PushMsg getPushMsg() {
		return pushMsg;
	}

	public void setPushMsg(PushMsg pushMsg) {
		this.pushMsg = pushMsg;
	}

	public void setPushMsgService(PushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}

	public String FindUnpushedMsg() throws Exception {
		try {
			
			if(ori!=null && (ori.equals(PUBLIC) || ori.equals(INNER)) )
			{
				int part=ori.equals(PUBLIC)?0:1;
				
				System.out.println(modulerid);
				
				list = this.pushMsgService.FindUnpushedMsg(userid,modulerid,part);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//根据模块ID及messageid,查询pushmsg
	public String getPushMsgDetail(){
		try {
			list = this.pushMsgService.getPushMsgDetail(pushMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// 修改根据ID修改pushmsg的状态
	public String Updatepushmsgstatus() throws Exception {
		try {
			this.pushMsgService.Updatepushmsgstatus(pushMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public PushMsg getModel() {

		return pushMsg;
	}
	//修改消息状态 该已推送已读
	public String UpdatePushmsgstatusByInfor(){
		try {
			this.pushMsgService.UpdatePushmsgstatusByInfor(pushMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	// 查询某个用户未读的消息
	public String FindUnpushedMsgByNoread(){
		try {
			this.pushMsgService.FindUnpushedMsgByNoread(pushMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	private List<Map<String, Object>> pushmsgs;
	private boolean onlysize;
	
	public void setOnlysize(boolean onlysize) {
		this.onlysize = onlysize;
	}
	
	public List<Map<String, Object>> getPushmsgs() {
		return pushmsgs;
	}
	
	/**
	* 获得未推送未读消息列表, 获取所有未推送未读消息后，将消息设置为已推送未读
	* 供后台推送服务调用
	*/
	public String queryUnPushedMsg() 
	{
		pushmsgs = new ArrayList<Map<String, Object>>();
		/*List<Map<String, Object>> data = this.pushMsgService.getUnPushedMsg(pushMsg.getModulerid(),pushMsg.getUserid());
		
		if (onlysize) {
			Map<String, Object> size = new HashMap<String, Object>();
			if (data != null)
				size.put("size", data.size());
			else
				size.put("size", 0);
			pushmsgs.add(size);
		}
		else
			pushmsgs = data;*/
		//System.out.println(data.size());
		return SUCCESS;
	}
	
	/**
	 * 获得已推送未读消息数量
	 * 供9宫格调用显示是否有未读消息
	 */
	public String queryPushedUnReadMsgCnt(){
		pushmsgs = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> size = new HashMap<String, Object>();
		size.put("size", this.pushMsgService.getPushedUnReadMsgCnt(pushMsg.getModulerid(),pushMsg.getUserid()));
		pushmsgs.add(size);
		
		return SUCCESS;
	}
	
	private String userids;
	private String messageids;
	
	public String getPushMsgsExclude() {
		
		list = this.pushMsgService.getPushMsgsExclude(pushMsg.getModulerid(), userids, messageids);
		return SUCCESS;
	}
	
	public void setUserids(String userids) {
		this.userids = userids;
	}
	
	public void setMessageids(String messageids) {
		this.messageids = messageids;
	}
}
