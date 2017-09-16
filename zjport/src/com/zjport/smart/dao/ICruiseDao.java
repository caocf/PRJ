package com.zjport.smart.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.model.TCurise;

/**
 * Created by Will on 2016/9/28 10:31.
 */
public interface ICruiseDao extends BaseDao {

    /**
     * 根据HQL查询巡航信息
     *
     * @param hql the hql
     * @return the base records
     * @Auth Will
     * @Date 2016 -10-11 09:59:53
     */
    BaseRecords<TCurise> findByHql(HQL hql);


    /**
     * 执行SQLupdate操作
     *
     * @param sql the sql
     * @return the int
     * @Auth Will
     * @Date 2016 -10-11 09:59:53
     */
    int updateSQL(SQL sql);
}
