package com.channel.log;

import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/21.
 */
public class LogAction extends BaseAction {
    @Resource(name = "logService")
    private LogService logService;

    private Date starttime;
    private Date endtime;
    private int page;
    private int rows;
    private int sEcho;
    private List<Integer> logids;
    private BaseResult result;

    public String queryLogs() {
        result = this.logService.queryLogs(starttime, endtime, page, rows);
        result.setObj(sEcho);
        return "json";
    }

    public String delLogs() {
        result = this.logService.delLogs(logids);
        return "json";
    }

    public List<Integer> getLogids() {
        return logids;
    }

    public void setLogids(List<Integer> logids) {
        this.logids = logids;
    }

    public int getSEcho() {
        return sEcho;
    }

    public void setSEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
