package com.channel.model.log;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * Created by 25019 on 2015/10/21.
 */
public class CXtSysLog {
    private int id; //自增ID
    private String opername; //操作人姓名
    private String ipaddr;//操作人IP
    private String imei; //手机端addr
    private String modulename; //操作模块
    private String opname; //操作名
    private String remarks; //操作备注信息
    private Date createtime; //操作时间

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpername() {
        return opername;
    }

    public void setOpername(String opername) {
        this.opername = opername;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
