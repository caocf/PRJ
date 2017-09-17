package com.sts.suggstion.service;

import com.sts.suggstion.dao.AppSuggestionDao;
import com.sts.suggstion.model.AppSuggestion;


public class AppSuggestionService 
{
	AppSuggestionDao appSuggestionDao;
	
	public void setAppSuggestionDao(AppSuggestionDao appSuggestionDao) {
		this.appSuggestionDao = appSuggestionDao;
	}
	
	public void save(int userid,String title,String content)
	{
		AppSuggestion suggestion=new AppSuggestion();
		
		suggestion.setUserid(userid);
		suggestion.setContent(content);
		suggestion.setTitle(title);
		
		this.appSuggestionDao.save(suggestion);
	}
	
}
