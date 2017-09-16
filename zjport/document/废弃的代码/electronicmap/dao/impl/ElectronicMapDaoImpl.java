package com.zjport.electronicmap.dao.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.zjport.electronicmap.dao.ElectronicMapDao;
import com.zjport.model.ZAcRegionInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by Will on 2016/11/4 13:49.
 */
@Repository("electronicMapDao")
public class ElectronicMapDaoImpl extends BaseDaoDB implements ElectronicMapDao {
    @Override
    public BaseRecords<?> findByHQL(HQL hql) {
        return super.find(hql);
    }
}
