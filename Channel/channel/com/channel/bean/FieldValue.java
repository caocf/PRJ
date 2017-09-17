package com.channel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class FieldValue {
    private String tname;//类型名 integer
    private String pname;//变量名 id
    private String vname;//显示名 序号
    private boolean flag;//是否字典类型
    private List dics;//字典

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List getDics() {
        return dics;
    }

    public void setDics(List dics) {
        this.dics = dics;
    }

    public FieldValue() {
    }

    public FieldValue(String tname, String pname, String vname) {
        this.tname = tname;
        this.pname = pname;
        this.vname = vname;
    }

    public FieldValue(String tname, String pname, String vname, boolean flag, List dics) {
        this.tname = tname;
        this.pname = pname;
        this.vname = vname;
        this.flag = flag;
        this.dics = dics;
    }
}
