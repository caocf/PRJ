package com.zjport.backlog.service;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.zjport.backlog.dao.BacklogDao;
import com.zjport.model.TBacklog;
import com.zjport.util.CommonConst;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWQ on 2016/10/9.
 * 待办事项Service
 */
@Service("backlogService")
public class BacklogService extends BaseService{

    @Resource(name = "backlogDao")
    private BacklogDao backlogDao;

    public List<TBacklog> selectBacklogListByUserId(int userId) {
        List<TBacklog> backList = new ArrayList<TBacklog>();
        BaseRecords records = this.backlogDao.selectBacklogListByUserId(userId);
        for(int i = 0; i<records.getData().size(); i++) {
            backList.add((TBacklog)records.getData().get(i));
        }
        return backList;
    }

    public void addBacklog(String backName,String type,int id,int userId) {
        TBacklog backlog = new TBacklog();
        backlog.setStBacklogName(backName);
        backlog.setStType(type);
        backlog.setDtCreate(new Timestamp(System.currentTimeMillis()));
        backlog.setStRelationId(id);
        backlog.setStUserId(userId);
        backlog.setStState(CommonConst.BACKLOG_STATE_WAIT_FINISH);

        this.backlogDao.save(backlog);
    }

    public void deleteBacklog(String type,int id,int userId) {
        TBacklog backlog = this.backlogDao.getBacklogByTypeId(type,id,userId);

        backlog.setStState(CommonConst.BACKLOG_STATE_FINISHED);

        this.backlogDao.saveOrUpdate(backlog);
    }

    public int selectCount(int userId) {
        return this.backlogDao.selectCount(userId);
    }

    public BaseRecords<TBacklog> selectBacklogList(int userId, int rows, int page) {
        BaseRecords records = this.backlogDao.selectAll(userId, rows, page);
        return records;
    }
}
