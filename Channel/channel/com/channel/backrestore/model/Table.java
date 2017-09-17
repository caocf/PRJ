package com.channel.backrestore.model;

/**
 * Created by 25019 on 2015/10/22.
 */
public class Table {
    /**
     * 表名
     */
    private String tablename;
    /**
     * 注释
     */
    private String comment;
    /**
     * 数据条数
     */
    private int datacnt;

    public Table(String tablename, String comment, int datacnt) {
        this.tablename = tablename;
        this.comment = comment;
        this.datacnt = datacnt;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDatacnt() {
        return datacnt;
    }

    public void setDatacnt(int datacnt) {
        this.datacnt = datacnt;
    }
}
