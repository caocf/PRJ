package com.channel.log;

import com.channel.model.log.CXtSysLog;
import com.channel.model.user.CXtUser;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/21.
 */
@Service("logService")
public class LogService extends BaseService {
    @Resource
    private LogDao logDao;

    public void addLog(CXtSysLog log) {
        this.logDao.addLog(log);
    }

    public void addLog(
            ModuleName modulename, //操作模块
            OpName opname, //操作名
            String remarks //操作备注信息
    ) {
        HttpServletRequest request = ServletActionContext.getRequest();
        String remoteip = request.getRemoteAddr();
        CXtUser user = (CXtUser) getSession().getAttribute("user");

        CXtSysLog log = new CXtSysLog();
        log.setCreatetime(new Date());
        log.setOpername(user.getName());
        log.setIpaddr(remoteip);
        log.setImei("");
        log.setModulename(modulename.getDescribe());
        log.setOpname(opname.getDescribe());
        log.setRemarks(remarks);
        addLog(log);
    }

    public BaseResult queryLogs(Date starttime, Date endtime, int page, int rows) {
        BaseResult result = new BaseResultOK(this.logDao.queryLogs(starttime, endtime, page, rows));
        return result;
    }

    public BaseResult delLogs(List<Integer> logids) {
        this.logDao.delLogs(logids);
        return new BaseResultOK();
    }
}
