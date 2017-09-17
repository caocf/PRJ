package com.sts.complain.action;

import java.net.URLDecoder;
import java.util.Date;

import com.sts.complain.model.Complain;
import com.sts.complain.service.ComplainService;

public class ComplainAction 
{
	Complain complain;
	ComplainService complainService;
	int type;
	String title;
	String content;
	String contact;
	Date date;
	
	boolean saveResult;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public void setComplainService(ComplainService complainService) {
		this.complainService = complainService;
	}

	public boolean isSaveResult() {
		return saveResult;
	}

	public void setSaveResult(boolean saveResult) {
		this.saveResult = saveResult;
	}

	public static final String DEFAULT_CHARSET="UTF-8";
	
	public String SaveComplain()
	{
//		try
//		{
//			contact=URLDecoder.decode(contact, DEFAULT_CHARSET);
//			content=URLDecoder.decode(content, DEFAULT_CHARSET);
//			title=URLDecoder.decode(title, DEFAULT_CHARSET);
//		}
//		catch (Exception e) {
//		}
		complain=new Complain();
		complain.setContact(contact);
		complain.setType(type);
		complain.setContent(content);
//		complain.setDate(date);
		complain.setTitle(title);
		
		saveResult=complainService.save(complain);
		
		return "success";
	}
}
