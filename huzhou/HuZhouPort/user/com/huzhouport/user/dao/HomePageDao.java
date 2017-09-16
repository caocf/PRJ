package com.huzhouport.user.dao;

import java.util.List;
import java.util.Map;

import com.huzhouport.user.model.User;

public interface HomePageDao {
	public List<Map<String, Object>> findHomePageInfo(User user) throws Exception;

	public List<Map<String, Object>> findHomePageInfoPrompt(User user)throws Exception;
}

