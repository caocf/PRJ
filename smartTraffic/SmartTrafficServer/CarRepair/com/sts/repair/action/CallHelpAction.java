package com.sts.repair.action;

import java.sql.Date;

import com.sts.repair.model.CallHelp;
import com.sts.repair.service.CallHelpService;

public class CallHelpAction 
{
	String content;
	String location;
	CallHelpService callHelpService;

	String result;
	public String getResult() {
		return result;
	}
	public void setCallHelpService(CallHelpService callHelpService) {
		this.callHelpService = callHelpService;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String callHelp()
	{
		CallHelp c=new CallHelp();
		c.setContent(content);
		c.setLocation(location);
		c.setDate(new Date(new java.util.Date().getTime()));
		
		this.callHelpService.save(c);
		
		result="sucess";
		
		return "success";
	}
}
