package com.channel.channelmanage;

import com.channel.model.hz.*;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.utils.DateTimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
@Repository("sjzxdao")
public class SjzxDao extends SjzxDB {
    public BaseQueryRecords queryAdminpenaltyById(Integer id) {
        String sql = "select * from yw_cb_wzxx where ajlb='航政' and id=?";
        return super.find(new SQL(sql, id));
    }

    public BaseQueryRecords queryAdminpenalty(Integer dept, Integer contenttype, String content, int page, int rows) {
        String sql = "select * from yw_cb_wzxx where ajlb='航政'";
        /*if (dept != 0) {
            hql += " and dept =" + dept;
        }*/
        if (content != null && !"".equals(content)) {
            if (contenttype == 1) {
                sql += " and slh like '%" + content + "%'";
            } else if (contenttype == 2) {
                sql += " and zwcm like '%" + content + "%'";
            } else {
                sql += " and (slh like '%" + content + "%' or zwmc like '%" + content + "%')";
            }
        }
        sql += " order by jarq desc";
        return find(new SQL(sql), page, rows);
    }
}
