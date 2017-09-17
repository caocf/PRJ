package com.channel.backrestore.schedule;

import org.quartz.Trigger;

/**
 * Created by 25019 on 2015/10/23.
 */
public enum JobStatus {
    DISABLED(0, "未启用"),
    NORMAL(1, "正常"),
    PAUSED(2, "已暂停"),
    COMPLETE(3, "已完成"),
    ERROR(4, "错误"),
    BLOCKED(5, "阻塞");

    private int jobstatuskey;
    private String jobstatusdesc;

    private JobStatus(int jobstatuskey, String jobstatusdesc) {
        this.jobstatuskey = jobstatuskey;
        this.jobstatusdesc = jobstatusdesc;
    }

    public int getJobstatuskey() {
        return jobstatuskey;
    }

    public void setJobstatuskey(int jobstatuskey) {
        this.jobstatuskey = jobstatuskey;
    }

    public String getJobstatusdesc() {
        return jobstatusdesc;
    }

    public void setJobstatusdesc(String jobstatusdesc) {
        this.jobstatusdesc = jobstatusdesc;
    }
}
