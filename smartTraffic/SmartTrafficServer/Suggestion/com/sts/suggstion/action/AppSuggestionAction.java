package com.sts.suggstion.action;

import com.sts.common.model.Message;
import com.sts.suggstion.service.AppSuggestionService;

public class AppSuggestionAction 
{
	AppSuggestionService appSuggestionService;
	public void setAppSuggestionService(
			AppSuggestionService appSuggestionService) {
		this.appSuggestionService = appSuggestionService;
	}
	
	/*--------------------------------------------------*/
	int userid;
	String title;
	String content;
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/*--------------------------------------------*/
	Message message;
	public Message getMessage() {
		return message;
	}
	
	/*----------------------------------------------*/
	
	public String addAppSuggestion()
	{
		if(userid==0)
			message=new Message(-1, "用户id不能为空");	
		else if(title==null || title.equals(""))
			message=new Message(-2,"标题不能为空");
		else if(content==null || content.equals(""))
		    message=new Message(-3,"内容不能为空");
		else 
		{
			this.appSuggestionService.save(userid, title, content);
			message=new Message(1,"添加成功");
		}
		
		return "success";
	}
	
	
}
