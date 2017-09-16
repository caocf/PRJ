package com.zjport.electronicmap.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;
import com.common.base.dao.impl.querycondition.HQL;

/**
 * Created by Will on 2016/11/4 13:45.
 */
public interface ElectronicMapDao extends BaseDao {
    BaseRecords<?> findByHQL(HQL hql);

}