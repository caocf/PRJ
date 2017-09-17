package com.channel.user.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.channel.bean.Constants;
import com.channel.model.user.CXtUser;
import com.channel.user.dao.UserDao;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;

import java.util.List;

@Repository("userdao")
public class UserDaoImpl extends BaseDaoDB implements UserDao {

    @Override
    public CXtUser queryUserByUsername(String username) {
        // TODO Auto-generated method stub
        CXtUser user = null;
        String hql = "from CXtUser where username='?' and ustatus in(?,?)";
        BaseQueryRecords<CXtUser> users = (BaseQueryRecords<CXtUser>) super
                .find(new HQL(hql, username, Constants.USTATUS_ENABLE,
                        Constants.USTATUS_DISABLE));
        if (users.getData().size() > 0) {
            user = users.getData().get(0);
        }
        return user;
    }

    @Override
    public CXtUser queryUserById(int id) {
        // TODO Auto-generated method stub
        CXtUser user = null;
        user = (CXtUser) super.findUnique(new CXtUser(), "id", id);
        return user;
    }

    @Override
    public BaseQueryRecords<CXtUser> queryUserByDpt(int id, int page, int rows) {
        return (BaseQueryRecords<CXtUser>) super.find(new CXtUser(),
                "department", id, page, rows);
    }

    @Override
    public long countUserByDpt(int id) {
        String hql = "select count(u) from CXtUser u where u.ustatus in(?,?) and u.department =?";
        return super.count(new HQL(hql, Constants.USTATUS_ENABLE,
                Constants.USTATUS_DISABLE, id));
    }

    @Override
    public BaseQueryRecords<CXtUser> queryUserByIds(String ids, int page,
                                                    int rows) {
        // TODO Auto-generated method stub
        String hql = "from CXtUser where department in(?) and ustatus=?";
        return (BaseQueryRecords<CXtUser>) super.find(new HQL(hql, ids,
                Constants.USTATUS_ENABLE), page, rows);
    }

    @Override
    public BaseQueryRecords<Object[]> queryUser(String department,
                                                String content, int page, int rows) {
        // TODO Auto-generated method stub
        String hql = "from CXtUser u,CXtDpt d,CZdAppattribute t,CZdUserjstatus j where u.title=t.id and u.jstatus=j.id and u.department=d.id and u.ustatus in(?,?) and u.department in(?)";
        if (!"".equals(content) && content != null) {
            hql += "  and (u.username like'%?%' or u.name like'%?%')";
        }
        hql += " order by t.id";
        return (BaseQueryRecords<Object[]>) super
                .find(new HQL(hql, Constants.USTATUS_ENABLE,
                        Constants.USTATUS_DISABLE,
                        department, content, content), page, rows);
    }

    @Override
    public BaseQueryRecords<CXtUser> queryUsers() {
        return (BaseQueryRecords<CXtUser>) super.find(new HQL("from CXtUser where ustatus != ?", Constants.USTATUS_DELETE));
    }

    @Override
    public BaseQueryRecords<CXtUser> queryUsers(List<Integer> userids, int page, int rows) {
        String hql = "from CXtUser u,CXtDpt d,CZdAppattribute t,CZdUserjstatus j where u.title=t.id and u.jstatus=j.id and u.department=d.id and u.ustatus !=? and u.id in (";
        for (int i = 0; i < userids.size(); i++) {
            hql += userids.get(i);
            if (i != userids.size() - 1)
                hql += ",";
        }
        hql += ")";
        return (BaseQueryRecords<CXtUser>) super.find(new HQL(hql, Constants.USTATUS_DELETE), page, rows);
    }
}
