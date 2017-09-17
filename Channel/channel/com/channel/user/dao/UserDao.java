package com.channel.user.dao;

import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.common.dao.BaseDao;
import com.common.dao.BaseQueryRecords;

import java.util.List;

public interface UserDao extends BaseDao {
    public CXtUser queryUserByUsername(String username);

    public CXtUser queryUserById(int id);

    public BaseQueryRecords<CXtUser> queryUserByDpt(int id, int page, int rows);

    public long countUserByDpt(int id);

    public BaseQueryRecords<CXtUser> queryUserByIds(String ids, int page,
                                                    int rows);

    public BaseQueryRecords<Object[]> queryUser(String department,
                                                String content, int page, int rows);

    /**
     * 查询所有用户未删除
     *
     * @return
     */
    public BaseQueryRecords<CXtUser> queryUsers();

    public BaseQueryRecords<CXtUser> queryUsers(List<Integer> userids, int page, int rows);
}
