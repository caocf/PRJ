package com.huzhouport.user.service.impl;

import java.util.List;
import java.util.Map;

import com.huzhouport.user.dao.HomePageDao;
import com.huzhouport.user.model.User;
import com.huzhouport.user.service.HomePageService;

public class HomePageServiceImpl implements HomePageService{
	private HomePageDao homePageDao;
	

	public void setHomePageDao(HomePageDao homePageDao) {
		this.homePageDao = homePageDao;
	}


	public List<Map<String, Object>> findHomePageInfo(User user) throws Exception {
		
		return this.homePageDao.findHomePageInfo(user);
	}


	public List<Map<String, Object>> findHomePageInfoPrompt(User user) throws Exception{
		return this.homePageDao.findHomePageInfoPrompt(user);
	}

}
