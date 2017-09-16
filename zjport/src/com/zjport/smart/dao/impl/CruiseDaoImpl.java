package com.zjport.smart.dao.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.model.TCurise;
import com.zjport.smart.dao.ICruiseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by Will on 2016/9/28 10:32.
 */
@Repository("cruiseDao")
public class CruiseDaoImpl extends BaseDaoDB implements ICruiseDao {
    public static final Logger log = LoggerFactory.getLogger(CruiseDaoImpl.class);

    @Override
    public BaseRecords<TCurise> findByHql(HQL hql) {
        return (BaseRecords<TCurise>) super.find(hql);
    }

    @Override
    public int updateSQL(SQL sql) {
        return super.update(sql);
    }
}
