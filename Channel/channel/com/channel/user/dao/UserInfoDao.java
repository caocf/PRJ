package com.channel.user.dao;

import com.channel.model.user.CXtUser;
import com.common.dao.BaseQueryRecords;

public interface UserInfoDao {
	public BaseQueryRecords<Object> queryUserInfo(int id);

	public void addUserInfo(CXtUser user);

	public BaseQueryRecords<CXtUser> queryUserInfoByName(int department, String content, int page, int rows);

}
