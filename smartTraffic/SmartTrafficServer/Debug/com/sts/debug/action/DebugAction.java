package com.sts.debug.action;

import java.util.List;

import com.sts.debug.model.Debug;
import com.sts.debug.service.DebugService;

public class DebugAction 
{
	Debug debug;
	DebugService debugService;
	int type;
	String title;
	String content;
	String contact;
	
	boolean saveResult;
	
	private List<Debug> debugs;
		
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Debug getDebug() {
		return debug;
	}

	public void setDebug(Debug debug) {
		this.debug = debug;
	}

	public List<Debug> getDebugs() {
		return debugs;
	}

	public void setDebugs(List<Debug> debugs) {
		this.debugs = debugs;
	}

	public void setDebugService(DebugService debugService) {
		this.debugService = debugService;
	}

	public boolean isSaveResult() {
		return saveResult;
	}

	public void setSaveResult(boolean saveResult) {
		this.saveResult = saveResult;
	}

	public String SaveDebug()
	{
		debug=new Debug();
		debug.setContact(contact);
		debug.setType(type);
		debug.setContent(content);
		debug.setTitle(title);
		
		saveResult=debugService.save(debug);
		
		return "success";
	}
	
	public String GetDebugs()
	{
		debugs=debugService.getALLDebugs();
		return "success";
	}
}
