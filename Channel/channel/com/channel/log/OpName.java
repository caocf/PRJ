package com.channel.log;

/**
 * Created by 25019 on 2015/10/22.
 */
public enum OpName {
    ADD("增加"), DEL("删除"), UPDATE("修改"), QUERY("查询"), LOGIN("登入"), LOGOUT("登出"),EXPORT("导出");

    private String describe;

    private OpName(String desc) {
        this.describe = desc;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
