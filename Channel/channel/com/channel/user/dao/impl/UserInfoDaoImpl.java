package com.channel.user.dao.impl;

import org.springframework.stereotype.Repository;
import com.channel.model.user.CXtUser;
import com.channel.user.dao.UserInfoDao;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;

@Repository("userinfodao")
public class UserInfoDaoImpl extends BaseDaoDB implements UserInfoDao {

	@Override
	public BaseQueryRecords<Object> queryUserInfo(int id) {
		// TODO Auto-generated method stub
		String hql = "from CXtUser u,CZdAppattribute t,CZdUserjstatus j,CXtDpt d where u.title=t.id and u.jstatus=j.id and u.department=d.id and u.id=?";
		return (BaseQueryRecords<Object>) super.find(new HQL(hql, id));
	}

	@Override
	public void addUserInfo(CXtUser user) {
		// TODO Auto-generated method stub
		super.save(user);
	}

	@Override
	public BaseQueryRecords<CXtUser> queryUserInfoByName(int department,
			String content,int page,int rows) {
		// TODO Auto-generated method stub
		String hql = "from CXtUser where department=? and (username like'%?%' or name like'%?%')";
		return (BaseQueryRecords<CXtUser>) super.find(new HQL(hql, department,
				content, content),page,rows);
	}

}
