package com.zjport.smart.dao.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.smart.dao.IChannelDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Will on 2016/10/18 12:15.
 */

@Repository("channelDao")
public class ChannelDaoImpl extends BaseDaoDB implements IChannelDao {
    @Override
    public BaseRecords<?> queryBySQL(SQL sql) {
        return find(sql);
    }

    @Override
    public BaseRecords<?> findByHQL(HQL hql) {
        return  find(hql);
    }
}
