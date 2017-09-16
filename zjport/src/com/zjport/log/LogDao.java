package com.zjport.log;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.log.model.LogModel;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("logDao")
public class LogDao  extends BaseDaoDB
{
    /**
     * 查询日志
     * @param optUser    用户名相关查询
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param page       页数
     * @return
     */
    public BaseRecords queryLogs(String optUser, String startTime, String endTime, int page)
    {
        String sql = "select l.id, u.st_user_name, l.content, date_format(l.logtime, '%Y-%m-%d %H:%i:%S') from t_log l left join t_user u on l.operator = u.st_user_id where 1=1";
        Map<String,Object> paramMap = new HashMap<>();
        if(!StringUtils.isEmpty(optUser)){
            sql += " and u.st_user_name like :username";
            paramMap.put("username", "%"+optUser+"%");
        }
        if(!StringUtils.isEmpty(startTime)){
            sql += " and l.logtime >= :start";
            paramMap.put("start", startTime+" 00:00:00");
        }
        if(!StringUtils.isEmpty(endTime)){
            sql += " and l.logtime <= :end";
            paramMap.put("end", endTime+" 23:59:59");
        }
        sql +=" order by l.logtime desc";

        SQL sql1 = new SQL(sql, paramMap);
        sql1.setPaging(page, 10);

        BaseRecords records = super.find(sql1);
        if (records.getTotal() > 0)
        {
            List<LogModel> logs = new ArrayList<>();
            List data = records.getData();
            for (int i=0; i < data.size(); i++)
            {
                Object[] log = (Object[]) data.get(i);

                LogModel logModel = new LogModel();
                logModel.setId((int) log[0]);
                logModel.setName((String) log[1]);
                logModel.setContent((String) log[2]);
                logModel.setJlsj(String.valueOf(log[3]));

                logs.add(logModel);
            }
            records.setData(logs);
        }
        return records;
    }

    public void deleteLog(int id){
         super.delete(new SQL("delete from t_log where id =?",id));
    }
}
