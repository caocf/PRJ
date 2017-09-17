package com.channel.outinterface;

import com.channel.model.user.CXtDpt;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

public class OutInterfaceAction extends BaseAction {
    private Integer xzqh = -1;
    private String username;// 用户名
    private BaseResult result;
    private static final long serialVersionUID = -4561016447982566253L;

    @Resource(name = "OutInterfaceService")
    private OutInterfaceService outInterfaceService;


    public int initXzqh(int xzqh) {
        int ret = 2;
        if (xzqh < 1) {
            CXtDpt dept = (CXtDpt) session.get("dpt");
            if (dept != null && dept.getXzqh() > 0) {
                ret = dept.getXzqh();
            }
        } else {
            ret = xzqh;
        }
        return ret;
    }

    /**
     * 设定报表-航道主要设施汇总表
     */
    public String outSsTable() {
        xzqh = initXzqh(xzqh);
        result = this.outInterfaceService.outSsTable(xzqh);
        session.put("ss", result.getObj());
        session.put("mapinfo", result.getMap());
        return "success";

    }

    /**
     * 登录
     *
     * @return
     */
    public String Login() {
        String ret = this.outInterfaceService.login(username);
        return ret;
    }

    public Integer getXzqh() {
        return xzqh;
    }

    public void setXzqh(Integer xzqh) {
        this.xzqh = xzqh;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
