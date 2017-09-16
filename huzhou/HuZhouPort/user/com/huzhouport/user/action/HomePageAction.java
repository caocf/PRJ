package com.huzhouport.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.user.model.User;
import com.huzhouport.user.service.HomePageService;
import com.opensymphony.xwork2.ModelDriven;

public class HomePageAction extends BaseAction implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private HomePageService homePageService;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	public String findHomePageInfo() throws Exception {
		try{
		int userId=(Integer) session.get("userId");
		user.setUserId(userId);
		list=this.homePageService.findHomePageInfo(user);
		}catch (Exception e) {
			throw new Exception("连接失败，请从新登录！");
			//e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findHomePageInfoPrompt() throws Exception {
		int userId=(Integer) session.get("userId");
		user.setUserId(userId);
		list=this.homePageService.findHomePageInfoPrompt(user);
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setHomePageService(HomePageService homePageService) {
		this.homePageService = homePageService;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public User getModel() {
		return user;
	}

}
