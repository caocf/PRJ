package com.zjport.smart.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.SQL;

/**
 * Created by Will on 2016/10/18 12:14.
 */
public interface IChannelDao extends BaseDao{

    BaseRecords<?> findByHQL(HQL hql);

    BaseRecords<?> queryBySQL(SQL sql);
}
