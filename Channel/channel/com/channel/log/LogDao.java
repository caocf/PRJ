package com.channel.log;

import com.channel.model.log.CXtSysLog;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/21.
 */
@Repository("logDao")
public class LogDao extends BaseDaoDB {
    public void addLog(CXtSysLog log) {
        super.save(log);
    }

    public BaseQueryRecords<CXtSysLog> queryLogs(Date starttime, Date endtime, int page, int rows) {
        HQL hql = null;
        if (starttime != null && endtime != null) {
            hql = new HQL("select a from CXtSysLog a where a.createtime >= '?' and a.createtime <= '?' order by a.createtime desc", DateTimeUtil.getTimeFmt(starttime), DateTimeUtil.getTimeFmt(endtime));
        }
        if (starttime == null && endtime != null) {
            hql = new HQL("select a from CXtSysLog a where a.createtime <= '?' order by a.createtime desc", DateTimeUtil.getTimeFmt(endtime));
        }
        if (starttime != null && endtime == null) {
            hql = new HQL("select a from CXtSysLog a where a.createtime >= '?' order by a.createtime desc", DateTimeUtil.getTimeFmt(starttime));
        }

        if (starttime == null && endtime == null) {
            hql = new HQL("select a from CXtSysLog a order by a.createtime desc");
        }
        return (BaseQueryRecords<CXtSysLog>) super.find(hql, page, rows);
    }

    public void delLogs(List<Integer> logids) {
        if (logids == null || logids.size() <= 0)
            return;
        String hql = "delete from CXtSysLog where id in(";

        for (int i = 0; i < logids.size(); i++) {
            hql += logids.get(i);
            if (i != logids.size() - 1)
                hql += ",";
        }

        hql += ")";
        super.delete(new HQL(hql));
    }
}
