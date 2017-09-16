package com.zjport.log;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.zjport.log.model.Log;
import com.zjport.log.model.LogModel;
import com.zjport.util.ExportExcel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

@Service("logService")
public class LogService extends BaseService
{

    @Resource(name="logDao")
    private LogDao logDao;

    /**
     * 查询日志
     *
     * @param optUser    用户名相关查询
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param page       页数
     * @return
     */
    public BaseResult queryLogs(String optUser, String startTime, String endTime, int page)
    {
        BaseRecords records = logDao.queryLogs(optUser, startTime, endTime, page);
        return BaseResult.newResultOK(records);
    }

    /**
     * 记录日志
     *
     * @param operator    操作人
     * @param logContent  日志内容
     * @return
     */
    public void saveLog(int operator, String logContent)
    {
        logDao.save(new Log(operator, logContent, Timestamp.valueOf(DateTimeUtil.formatCurrDate())));
    }

    /**
     * 删除日志（批量删除）
     * @param logIds   日志ID ,多个ID用“，”区分
     * @return
     */
    public BaseResult deleteLogs(String logIds)
    {
        String[] idlist = logIds.split(",");
        for (String e : idlist) {
            int id = -1;
            try {
                id = Integer.valueOf(e).intValue();
            } catch (Exception error) {
                continue;
            }
            this.logDao.deleteLog(id);
        }
        return BaseResult.newResultOK("删除成功");
    }

    public BaseResult excelCreat(String optUser, String startTime, String endTime)
    {
        List<LogModel> logList = logDao.queryLogs(optUser, startTime, endTime, -1).getData();
        ExportExcel<LogModel> ex = new ExportExcel<LogModel>();
        String[] headers = {"序号","操作人姓名","操作内容","操作时间"};
        InputStream in = ex.exportExcel("省综合管理与服务平台操作日志", headers, logList, "yyyy-MM-dd hh:mm:ss","省综合管理与服务平台");
        return BaseResult.newResultOK(in) ;
    }

    public void saveLog(Log log)
    {
        logDao.save(log);
    }
}
