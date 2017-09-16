package com.huzhouport.wechat.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.wechat.model.Message;
import com.huzhouport.wechat.service.MessageService;
import com.opensymphony.xwork2.ModelDriven;

public class MessageAction extends BaseAction implements ModelDriven<Message>{
	
	private static final long serialVersionUID = 1L;
	private int totalPage;// 总页数
	private int allTotal;
	private Message message =new Message();
	private MessageService messageService;
	private List<?> list;
	
	public Message getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	//分页查询
	public String showMessageInfo() throws Exception{
		try{
			Map<String,Integer> map=new HashMap<String, Integer>();
			map = this.messageService.countPageMessageInfo(message,
				GlobalVar.PAGESIZE);
			totalPage=map.get("pages");
			allTotal=map.get("allTotal");
		if (totalPage != 0) {

			list = this.messageService.searchMessageInfo(message, Integer
					.valueOf(this.getCpage()), GlobalVar.PAGESIZE);
		
	}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
}
	
	//增加
	public String addMessageInfo() throws Exception{
		try{
		this.messageService.addMessageInfo(message);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
}