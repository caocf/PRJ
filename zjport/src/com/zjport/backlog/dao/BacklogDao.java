package com.zjport.backlog.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.zjport.model.TBacklog;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TWQ on 2016/10/9.
 */
@Repository("backlogDao")
public class BacklogDao extends BaseDaoDB{

    public BaseRecords selectBacklogListByUserId(int userId) {

        String hql = "from TBacklog where stState = 1";
        Map<String,Object> paramMap = new HashMap<>();

        if(!StringUtils.isEmpty(userId)) {
            hql += " and stUserId =:userId";
            paramMap.put("userId", userId);
        }
        hql +=" order by dtCreate desc";
        HQL Hql = new HQL(hql, paramMap);
        return super.find(Hql);
    }

    public TBacklog getBacklogByTypeId(String type, int id, int userId) {
        String hql = "from TBacklog";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(type)) {
            hql += " where stType =:type";
            paramMap.put("type", type);
        }
        if(!StringUtils.isEmpty(id)) {
            hql += " and stRelationId =:id";
            paramMap.put("id", id);
        }
        if(!StringUtils.isEmpty(userId)) {
            hql += " and stUserId =:userId";
            paramMap.put("userId", userId);
        }
        HQL Hql = new HQL(hql, paramMap);
        BaseRecords record = super.find(Hql);
        return (TBacklog)record.getData().get(0);
    }

    public int selectCount(int userId) {
        return (int)super.count(new ObjectQuery(TBacklog.class,"stUserId",userId));
    }

    public BaseRecords selectAll(int userId, int rows, int page) {
        String hql = "from TBacklog where stState=1";
        //state优先级最高，判断state不为空先查，如果state为空则判断search是否为空，若为空则正常查询，不为空则模糊搜索
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(userId)) {
            hql += " and stUserId =:userId";
            paramMap.put("userId", userId);
        }
        hql +=" order by dtCreate desc";
        HQL Hql = new HQL(hql, paramMap);
        Hql.setPage(page);
        Hql.setRows(rows);
        return super.find(Hql);
    }
}
