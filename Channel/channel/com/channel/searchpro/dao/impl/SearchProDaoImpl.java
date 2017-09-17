package com.channel.searchpro.dao.impl;

import com.channel.searchpro.dao.SearchProDao;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/3/7.
 */
@Repository("searchprodao")
public class SearchProDaoImpl extends BaseDaoDB implements SearchProDao {
    @Override
    public BaseQueryRecords searchPro(String tname, String cdt, int page, int rows) {
        String hql = "";
        if ("CHdHdaojcxx".equals(tname)) {
            hql = "select a from CHdHdaojcxx a where 1=1 ?";
            return super.find(new HQL(hql, cdt), page, rows);
        } else if ("CHdHduanjcxx".equals(tname)) {
            hql = "select a from CHdHduanjcxx a,CHdHdaojcxx b where a.sshdid=b.id ?";
            return super.find(new HQL(hql, cdt), page, rows);
        } else {
            hql = "select a from "+tname+" a,CHdHdaojcxx b,CHdHduanjcxx c where a.sshdaoid=b.id and a.sshduanid=c.id ?";
            return super.find(new HQL(hql, cdt), page, rows);
        }
    }
}
