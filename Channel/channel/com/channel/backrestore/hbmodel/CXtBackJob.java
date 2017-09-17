package com.channel.backrestore.hbmodel;

import java.util.Date;

/**
 * Created by 25019 on 2015/10/23.
 */
public class CXtBackJob {
    // 自增ID
    private int id;
    // 备份名
    private String backname;
    //备份内容
    private String backcontent;

    //备份时间类型
    private int type;
    private int interval;
    private Date starttime;

    //备份计划 cron表达式
    private String cronexpression;
    //备份目录
    private String backfolder;

    //备份数据表,以逗号分隔，如果为空表示备份所有
    private String backtables;

    // 是否启用
    private int ifenable;

    private String runtimestatus;

    //备份计划描述
    private String backschedule;

    //更新时间
    private Date updatetime;
    // 创建时间
    private Date createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackname() {
        return backname;
    }

    public void setBackname(String backname) {
        this.backname = backname;
    }

    public String getBackcontent() {
        return backcontent;
    }

    public void setBackcontent(String backcontent) {
        this.backcontent = backcontent;
    }

    public String getCronexpression() {
        return cronexpression;
    }

    public void setCronexpression(String cronexpression) {
        this.cronexpression = cronexpression;
    }

    public String getBackfolder() {
        return backfolder;
    }

    public void setBackfolder(String backfolder) {
        this.backfolder = backfolder;
    }

    public String getBacktables() {
        return backtables;
    }

    public void setBacktables(String backtables) {
        this.backtables = backtables;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getIfenable() {
        return ifenable;
    }

    public void setIfenable(int ifenable) {
        this.ifenable = ifenable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public String getRuntimestatus() {
        return runtimestatus;
    }

    public void setRuntimestatus(String runtimestatus) {
        this.runtimestatus = runtimestatus;
    }

    public String getBackschedule() {
        return backschedule;
    }

    public void setBackschedule(String backschedule) {
        this.backschedule = backschedule;
    }
}
