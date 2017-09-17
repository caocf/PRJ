package com.channel.backrestore;

import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/22.
 */
public class BackRestoreAction extends BaseAction {
    @Resource
    private BackRestoreService backRestoreService;
    private BaseResult result;

    private String sEcho;
    private int id;
    private int page;
    private int rows;
    private String backname;
    private String backcontent;
    private boolean ifenable = false;
    private boolean backall = false;
    private List<String> tablenames;
    private int type;
    private Date starttime;
    private int secondsinterval;
    private String cronexpression;

    /**
     * 查询数据库可备份的所有表列表
     *
     * @return
     */
    public String queryTables() {
        result = this.backRestoreService.queryTables();
        return "json";
    }

    /**
     * 查询备份计划列表
     *
     * @return
     */
    public String queryBackSchedules() {
        result = this.backRestoreService.queryBackSchedules(page, rows);
        result.addToMap("sEcho", sEcho);
        return "json";
    }

    public String queryBackHistories() {
        result = this.backRestoreService.queryBackHistories(id, page, rows);
        result.addToMap("sEcho", sEcho);
        return "json";
    }

    public String deleteBackHistory() {
        result = this.backRestoreService.deleteBackHistory(id);
        return "json";
    }

    public String enableBackSchedule() {
        result = this.backRestoreService.enableBackSchedule(id);
        return "json";
    }

    public String disableBackSchedule() {
        result = this.backRestoreService.disableBackSchedule(id);
        return "json";
    }

    public String deleteBackSchedule() {
        result = this.backRestoreService.deleteBackSchedule(id);
        return "json";
    }

    public String viewBackSchedule() {
        result = this.backRestoreService.viewBackSchedule(id);
        return "json";
    }

    public String addBackSchedule() {
        result = this.backRestoreService.addBackSchedule(backname, backcontent, backall, tablenames, cronexpression, ifenable, type, secondsinterval, starttime);
        return "json";
    }

    public String updateBackSchedule() {
        result = this.backRestoreService.updateBackSchedule(id, backname, backcontent, backall, tablenames, cronexpression, type, secondsinterval, starttime);
        return "json";
    }

    public String restore() {
        result = this.backRestoreService.restore(id);
        return "json";
    }

    //立即备份
    public String backup() {
        boolean ret = this.backRestoreService.backup(id);
        if (ret)
            result = new BaseResultOK();
        else
            result = new BaseResultFailed("备份失败");
        return "json";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSEcho() {
        return sEcho;
    }

    public void setSEcho(String sEcho) {
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

    public boolean isBackall() {
        return backall;
    }

    public void setBackall(boolean backall) {
        this.backall = backall;
    }

    public List<String> getTablenames() {
        return tablenames;
    }

    public void setTablenames(List<String> tablenames) {
        this.tablenames = tablenames;
    }

    public String getCronexpression() {
        return cronexpression;
    }

    public void setCronexpression(String cronexpression) {
        this.cronexpression = cronexpression;
    }

    public String getBackcontent() {
        return backcontent;
    }

    public void setBackcontent(String backcontent) {
        this.backcontent = backcontent;
    }

    public String getBackname() {
        return backname;
    }

    public void setBackname(String backname) {
        this.backname = backname;
    }

    public boolean isIfenable() {
        return ifenable;
    }

    public void setIfenable(boolean ifenable) {
        this.ifenable = ifenable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public int getSecondsinterval() {
        return secondsinterval;
    }

    public void setSecondsinterval(int secondsinterval) {
        this.secondsinterval = secondsinterval;
    }
}
